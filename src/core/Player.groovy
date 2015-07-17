package core;

import core.card.Card;
import core.card.DeckZone;
import core.card.Zone;
import core.card.ZoneType;
import core.card.creature.Creature;
import core.card.creature.Hero;

public class Player implements Copyable<Player>, Linkable<Player> {
    public Zone<Creature> play;
    public DeckZone deck;
    public Zone<Card> hand, graveyard;
    Link<Hero> hero;
    public int mana;
    private Link<Player> link;

    public Player() {
        play = new Zone<>(7, ZoneType.PLAY);
        hand = new Zone<>(10, ZoneType.HAND);
        deck = new DeckZone();
        graveyard = new Zone<>(ZoneType.GRAVEYARD);
        hero = null;
        mana = 0;
    }

    public void initHero(Hero hero, Game game) {
        this.hero = new Link<>(hero, game);
        //hero.setPlayer(link);
        play.add(this.hero, game);
    }

    public Hero getHero(Game game) {
        return (Hero) hero.getFrom(game);
    }

    @Override
    public void setLink(Link<Player> link) {
        this.link = link;
        play.setPlayer(link);
        hand.setPlayer(link);
        deck.setPlayer(link);
        graveyard.setPlayer(link);
    }

    @Override
    public Player copy() {
        Player p = new Player();
        p.play = play.copy();
        p.hand = hand.copy();
        p.deck = deck.copy();
        p.graveyard = graveyard.copy();
        p.hero = hero;
        p.mana = mana;
        return p;
    }
}
