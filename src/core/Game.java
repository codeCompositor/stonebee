package core;

import core.card.Card;
import core.card.Spell;
import core.card.Zone;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.cardbase.heroes.JainaProudmoore;
import core.phase.*;
import groovy.lang.Closure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is main class of Core module
 */
public class Game implements Copyable<Game> {
    public Player player;
    public Player opponent;
    /**
     * Contains all triggers
     */
    public List<PhaseTrigger> allTriggers; //TODO: Make this a Map object
    /**
     * Contains indexes of triggers from allTriggers list in order from never to older
     */
    public IndexesList<PhaseTrigger> triggers;
    public Closure targetChooser;
    public Stack<Phase> phaseStack;
    public int currentTarget;
    public List<Card> allCards;
    public LinkedList<Link<Creature>> play;
    public LinkedList<Link<? extends Card>> hand;
    public LinkedList<Link<? extends Card>> deck;
    public LinkedList<Link<? extends Card>> graveyard;
    public List<Copyable> allObjects;
    private GameResult result;

    public Game(Player player, Player opponent) {
        this();
        initPlayers(player, opponent);
    }

    public Game() {
        allObjects = new ArrayList<>();
        allTriggers = new ArrayList<>();
        allCards = new ArrayList<>();
        triggers = new IndexesList<>(allObjects);
        play = new LinkedList<>();
        hand = new LinkedList<>();
        deck = new LinkedList<>();
        graveyard = new LinkedList<>();
        phaseStack = new Stack<>();
        resetCurrentTarget();
    }

    private void initPlayers(Player player, Player opponent) {
        this.player = player;
        this.opponent = opponent;
        player.setLink(new Link<>(player, this));
        opponent.setLink(new Link<>(opponent, this));
        player.initHero(new JainaProudmoore(), this); //TODO: Add other heroes
        opponent.initHero(new JainaProudmoore(), this);
    }

    public void addPhase(Phase phase) {
        phaseStack.push(phase);
    }

    public void run() {
        while (!phaseStack.isEmpty()) {
            Phase phase = phaseStack.peek();
            if (phase.isPendingResolution()) {
                phaseStack.pop();
                if (phase.isOutermost()) {
                    updateStats();
                    handleDeaths();
                    checkForGameEnd();
                }
            } else {
                phase.occur(this);
                phase.setPendingResolution(true);
            }
        }
    }

    /**
     * Process death, activate death-related triggers.
     * This method is used only in outermost Phase.
     */
    private void handleDeaths() {
        List<Link<Creature>> deadCreatures = play.stream()
                .filter(c -> c.getFrom(this).isDead())
                .collect(Collectors.toList());
        if (!deadCreatures.isEmpty())
            addPhase(new DeathPhase(deadCreatures));
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void playSpell(Spell spell) {
        Link<Spell> l = new Link<>(spell, this);
        addPhase(new AfterSpellPhase(l, true));
        addPhase(spell.getText());
        addPhase(new OnPlayPhase(l, true));
        addPhase(new PlayingPhase(l, true));
    }

    /**
     * Play minion from player's hand
     *
     * @param link link on minion to play
     */
    public void playMinion(Link<Minion> link) {
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

    public void playMinion(Minion minion) {
        playMinion(new Link<>(minion, this));
    }

    /**
     * Checks if the game has ended and if it is so then assigns result.
     *
     * @return <tt>true</tt> if the game has ended
     */
    private boolean checkForGameEnd() {
        if (player.getHero(this).isDead() || opponent.getHero(this).isDead()) {
            result = GameResult.valueOf((player.getHero(this).isDead() ? 2 : 0) + (opponent.getHero(this).isDead() ? 1 : 0));
            return true;
        }
        return false;
    }

    /**
     * Performs combat between attacker and defender.
     *
     * @param attacker creature that attacks
     * @param defender creature that defends
     */
    public void combat(Creature attacker, Creature defender) {
        combat(new Link<>(attacker, this), new Link<>(defender, this));
    }

    public void combat(Link<Creature> attacker, Link<Creature> defender) {
        addPhase(new CombatPhase(attacker, defender, true));
        addPhase(new PreparationPhase(attacker, defender, true));
    }

    public void endTurn() {
        phaseStack.addAll(Arrays.asList(new CardDrawPhase(true), new StartOfTurnPhase(true), new EndOfTurnPhase(true)));
        //TODO: Replace with normal commands
    }

    public void updateStats() {
        //TODO: Add auras
        play.forEach(c -> {
            try {
                c.getFrom(this).updateStats();
            } catch (NullPointerException e) {
                System.out.println(allObjects);
                System.out.println(c);
                e.printStackTrace();
            }
        });
    }

    public Player oppositePlayer(Player player) {
        return player.equals(this.player) ? this.opponent : this.player;
    }

    public GameResult getResult() {
        return result;
    }

    @Override
    public Game copy() {
        Game copy = new Game();
        allObjects.forEach(o -> copy.allObjects.add(o.copy()));
        copy.setPlayer((Player) copy.allObjects.get(0));
        copy.setOpponent((Player) copy.allObjects.get(1));
        copy.player.setLink(new Link<>(copy.player, copy));
        copy.opponent.setLink(new Link<>(copy.opponent, copy));
        phaseStack.forEach(p -> copy.phaseStack.add(p.copy()));
        play.forEach(c -> copy.play.add(c.copy()));
        hand.forEach(c -> copy.hand.add(c.copy()));
        deck.forEach(c -> copy.deck.add(c.copy()));
        graveyard.forEach(c -> copy.graveyard.add(c.copy()));
        copy.currentTarget = currentTarget;
        copy.result = result;
        return copy;
    }

    public void resetCurrentTarget() {
        currentTarget = -1;
    }

    public enum GameResult {
        WIN, LOSS, DRAW;

        /**
         * Returns the enum constant of GameResult with the specified id.
         *
         * @param id the identificator of the enum constant. Here is the list of ids: 1 - WIN, 2 - LOSS, 3 - DRAW
         * @return the enum constant with the specified id
         * @throws IllegalArgumentException if this enum has no constant with the specified id
         */
        public static GameResult valueOf(int id) throws IllegalArgumentException {
            switch (id) {
                case 1:
                    return WIN;
                case 2:
                    return LOSS;
                case 3:
                    return DRAW;
            }
            throw new IllegalArgumentException();
        }

        /**
         * Returns the enum constant of GameResult based on information about the life state of player and opponent.
         *
         * @param isPlayerDead   the player's life state (true if dead, and false if alive)
         * @param isOpponentDead the opponent's life state (true if dead, and false if alive)
         * @return the enum constant which calculated using the life states of the player and the opponent
         * @throws IllegalArgumentException if this enum has no constant with the specified id
         */
        public static GameResult valueOf(boolean isPlayerDead, boolean isOpponentDead) throws IllegalArgumentException {
            return valueOf((isPlayerDead ? 2 : 0) + (isOpponentDead ? 1 : 0));
        }
    }
}
