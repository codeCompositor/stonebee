package core

import core.card.Card
import core.card.Spell
import core.card.ZoneType
import core.card.creature.Creature
import core.card.creature.Minion
import core.cardbase.heroes.JainaProudmoore
import core.phase.*

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
    Map<ZoneType, LinkedList<Link<Entity>>> zones
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

    void addPhase(Phase phase) {
        phaseStack.push(phase)
    }

    void run() {
        while (!phaseStack.empty) {
            Phase phase = phaseStack.peek()
            if (phase.pendingResolution)
                phaseStack.pop()
            else
                phase.occur(this)
        }
    }

    void playSpell(Spell spell) {
        Link<Spell> l = new Link<>(spell, this);
        addPhase(new AfterSpellPhase(l, true));
        addPhase(spell.text);
        addPhase(new OnPlayPhase(l, true));
        addPhase(new PlayingPhase(l, true));
    }

    /**
     * Play minion from player's hand
     *
     * @param link link on minion to play
     */
    void playMinion(Link<Minion> link) {
        addPhase(new AfterSummonPhase(link, true));
        addPhase(new SecretActivationPhase(link, true));
        if (link.getFrom(this).hasBattlecry()) {
            addPhase(link.getFrom(this).getBattlecry());
        }
        addPhase(new LateOnSummonPhase(link, true));
        addPhase(new OnPlayPhase(link, true));
        addPhase(new EarlyOnSummonPhase(link, true));
        addPhase(new PlayingPhase(link, true));
    }

    void playMinion(Minion minion) {
        playMinion(new Link<>(minion, this));
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
        phaseStack.addAll([new CardDrawPhase(true), new StartOfTurnPhase(true), new EndOfTurnPhase(true)])
        //TODO: Replace with normal commands
    }

    void updateStats() {
        //TODO: Add auras
        play.each { it.getFrom(this).updateStats() }
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
        copy.play = play*.copy()
        copy.hand = hand*.copy()
        copy.deck = play*.copy()
        copy.graveyard = graveyard*.copy()
        copy.currentTarget = currentTarget
        copy.result = result
        copy
    }

    void resetCurrentTarget() {
        currentTarget = -1
    }

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
