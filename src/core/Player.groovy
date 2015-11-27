package core

import core.card.DeckZone
import core.card.Weapon
import core.card.Zone
import core.card.ZoneType
import core.card.creature.Hero

import static core.card.ZoneType.*

public class Player implements Copyable<Player>, Linkable<Player> {
    Map<ZoneType, Zone<Entity>> zones = [:]
    Link<Hero> hero
    Link<Weapon> weapon
    int mana
    Link<Player> link

    Player() {
        zones[PLAY] = new Zone(7, PLAY)
        zones[HAND] = new Zone(10, HAND)
        zones[DECK] = new DeckZone()
        zones[GRAVEYARD] = new Zone(GRAVEYARD)
        hero = null
        mana = 0
    }

    void initHero(Hero hero, Game game) {
        this.hero = new Link(hero, game)
        zones[PLAY].add(this.hero, game)
    }

    Hero getHero(Game game) {
        hero[game]
    }

    void setLink(Link<Player> link) {
        this.link = link
        zones.each { it.value.setPlayer(link) }
    }

    Player copy() {
        Player p = new Player()
        zones.each { p.zones.put(it.key, it.value.copy()) }
        p.hero = hero
        p.weapon = weapon
        p.mana = mana
        return p
    }
}
