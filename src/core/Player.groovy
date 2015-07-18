package core

import core.card.Card
import core.card.DeckZone
import core.card.Zone
import core.card.ZoneType
import core.card.creature.Creature
import core.card.creature.Hero

public class Player implements Copyable<Player>, Linkable<Player> {
    Zone<Creature> play;
    DeckZone deck;
    Zone<Card> hand, graveyard;
    Link<Hero> hero;
    int mana;
    Link<Player> link;

    Player() {
        play = new Zone<>(7, ZoneType.PLAY);
        hand = new Zone<>(10, ZoneType.HAND);
        deck = new DeckZone();
        graveyard = new Zone<>(ZoneType.GRAVEYARD);
        hero = null;
        mana = 0;
    }

    void initHero(Hero hero, Game game) {
        this.hero = new Link<>(hero, game);
        //hero.setPlayer(link);
        play.add(this.hero, game);
    }

    Hero getHero(Game game) {
        return (Hero) hero.getFrom(game);
    }

    void setLink(Link<Player> link) {
        this.link = link;
        play.setPlayer(link);
        hand.setPlayer(link);
        deck.setPlayer(link);
        graveyard.setPlayer(link);
    }

    Player copy() {
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
