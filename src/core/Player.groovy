package core

import core.card.DeckZone
import core.card.Zone
import core.card.ZoneType
import core.card.creature.Hero

import static core.card.ZoneType.*

public class Player implements Copyable<Player>, Linkable<Player> {
    HashMap<ZoneType, Zone<Entity>> zones
    Link<Hero> hero
    int mana
    Link<Player> link

    Player() {
        zones[PLAY] = new Zone(7, PLAY)
        zones[HAND] = new Zone(10, PLAY)
        zones[DECK] = new DeckZone()
        zones[GRAVEYARD] = new Zone(GRAVEYARD)
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
        this.link = link
        zones.each { it.value.setPlayer(link) }
    }

    Player copy() {
        Player p = new Player();
        zones.each { p.zones.put(it.key, it.value.copy()) }
        p.hero = hero;
        p.mana = mana;
        return p;
    }
}
