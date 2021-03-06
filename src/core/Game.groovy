package core

import core.card.Card
import core.card.Spell
import core.card.ZoneType
import core.card.creature.Creature
import core.card.creature.Minion
import core.cardbase.heroes.JainaProudmoore
import core.phase.*

import static core.card.ZoneType.PLAY

/**
 * This is main class of Core module
 */
public class Game implements Copyable<Game> {
    Player player
    Player opponent
    /**
     * Contains all triggers
     */
    public List<TriggeredPhase> allTriggers //TODO: Make this a Map object
    /**
     * Contains indexes of triggers from allTriggers list in order from never to older
     */
    public LinkedList<Link<TriggeredPhase>> triggers
    public Closure targetChooser
    public Stack<Phase> phaseStack
    public int currentTarget
    public List<Card> allCards
    Map<ZoneType, LinkedList<Link<Entity>>> zones = [:]
    public List<Copyable> allObjects
    private GameResult result

    Game(Player player, Player opponent) {
        this()
        initPlayers(player, opponent)
    }

    Game() {
        allObjects = new ArrayList<>()
        allTriggers = new ArrayList<>()
        allCards = new ArrayList<>()
        triggers = new LinkedList<>()
        ZoneType.values().each { zones[it] = new LinkedList() }
        phaseStack = new Stack<>()
        resetCurrentTarget()
    }

    private void initPlayers(Player player, Player opponent) {
        this.player = player
        this.opponent = opponent
        player.setLink(new Link(player, this))
        opponent.setLink(new Link(opponent, this))
        player.initHero(new JainaProudmoore(), this) //TODO: Add other heroes
        opponent.initHero(new JainaProudmoore(), this)
    }

    void addPhase(Phase phase, boolean newPhase = true) {
        if (newPhase)
            phaseStack.push(phase)
        else
            phaseStack.push(phaseStack.pop() + phase)
    }

    void run() {
        while (!phaseStack.empty) {
            def phase = phaseStack.peek()
            if (phase.pendingResolution)
                phaseStack.pop()
            else
                phase.occur(this)
        }
    }

    void playSpell(Spell spell) {
        playSpell(new Link(spell, this))
    }

    void playSpell(Link<Spell> link) {
        addPhase(new DeathPhase())
        addPhase(new StatsUpdatePhase())
        addPhase(new AfterSpellPhase(link))
        addPhase(new DeathPhase())
        addPhase(new StatsUpdatePhase())
        addPhase(link[this].text)
        addPhase(new DeathPhase())
        addPhase(new StatsUpdatePhase())
        addPhase(new OnPlayPhase(link))
        addPhase(new PlayingPhase(link))
    }

    /**
     * Play minion from player's hand
     *
     * @param link link on minion to play
     */
    void playMinion(Link<Minion> link) {
        addPhase(new DeathPhase())
        addPhase(new StatsUpdatePhase())
        addPhase(new AfterSummonPhase(link))
        addPhase(new StatsUpdatePhase())
        addPhase(new SecretActivationPhase(link))
        if (link[this].hasBattlecry()) {
            addPhase(new DeathPhase())
            addPhase(new StatsUpdatePhase())
            addPhase(link[this].getBattlecry())
        }
        addPhase(new DeathPhase())
        addPhase(new StatsUpdatePhase())
        addPhase(new LateOnSummonPhase(link))
        addPhase(new OnPlayPhase(link))
        addPhase(new EarlyOnSummonPhase(link))
        addPhase(new PlayingPhase(link))
    }

    void playMinion(Minion minion) {
        playMinion(new Link<>(minion, this))
    }

    /**
     * Checks if the game has ended and assigns result.
     *
     * @return <tt>true</tt> if the game has ended
     */
    private boolean checkForGameEnd() {
        (result = GameResult.valueOf(player.getHero(this).dead, opponent.getHero(this).dead)) != GameResult.IN_PROGRESS
    }

    /**
     * Performs combat between attacker and defender.
     *
     * @param attacker creature that attacks
     * @param defender creature that defends
     */
    void combat(Creature attacker, Creature defender) {
        combat(new Link(attacker, this), new Link(defender, this))
    }

    void combat(Link<Creature> attacker, Link<Creature> defender) {
        addPhase(new DeathPhase())
        addPhase(new CombatPhase(attacker, defender))
        addPhase(new DeathPhase())
        addPhase(new PreparationPhase(attacker, defender))
    }

    void endTurn() {
        phaseStack.addAll([new CardDrawPhase(), new StartOfTurnPhase(), new EndOfTurnPhase()])
        //TODO: Replace with normal commands
    }

    void updateStats() {
        //TODO: Add auras
        zones[PLAY].each { it[this].updateStats() }
        zones[PLAY].each { it[this].updateStats() }
    }

    Player oppositePlayer(Player player) {
        player.is(this.player) ? this.opponent : this.player
    }

    GameResult getResult() {
        result
    }

    Game copy() {
        Game copy = new Game()
        copy.allObjects = allObjects*.copy()
        copy.setPlayer((Player) copy.allObjects.get(0))
        copy.setOpponent((Player) copy.allObjects.get(1))
        copy.player.setLink(new Link(copy.player, copy))
        copy.opponent.setLink(new Link(copy.opponent, copy))
        copy.phaseStack = phaseStack*.copy()
        zones.each { copy.zones.put(it.key, it.value*.copy()) }
        copy.currentTarget = currentTarget
        copy.result = result
        copy
    }

    void resetCurrentTarget() {
        currentTarget = -1
    }

    void dealDamage(int damage, Selector selector, Link<Entity> link) {
        def phase = new Phase()
        selector.eval(this, link).each {
            phase += new DamagePhase(damage, it, link)
        }
        addPhase(phase)
    }

//    void dealDamage(int damage, Selector selector, Link<Entity> link) {
//        addPhase(new Phase())
//        selector.eval(this, link).each {
//            addPhase(new DamagePhase(damage, it, link), false)
//        }
//    }

    enum GameResult {
        IN_PROGRESS, WIN, LOSS, DRAW

        /**
         * Returns the enum constant of GameResult with the specified id.
         *
         * @param id the identificator of the enum constant. Here is the list of ids: 1 - WIN, 2 - LOSS, 3 - DRAW
         * @return the enum constant with the specified id
         * @throws IllegalArgumentException if this enum has no constant with the specified id
         */
        static GameResult valueOf(int id) throws IllegalArgumentException {
            switch (id) {
                case 0:
                    return IN_PROGRESS
                case 1:
                    return WIN
                case 2:
                    return LOSS
                case 3:
                    return DRAW
            }
            throw new IllegalArgumentException()
        }

        /**
         * Returns the enum constant of GameResult based on information about the life state of player and opponent.
         *
         * @param isPlayerDead the player's life state (true if dead, and false if alive)
         * @param isOpponentDead the opponent's life state (true if dead, and false if alive)
         * @return the enum constant which calculated using the life states of the player and the opponent
         * @throws IllegalArgumentException if this enum has no constant with the specified id
         */
        static GameResult valueOf(boolean isPlayerDead, boolean isOpponentDead) throws IllegalArgumentException {
            valueOf((isPlayerDead ? 2 : 0) + (isOpponentDead ? 1 : 0))
        }
    }
}
