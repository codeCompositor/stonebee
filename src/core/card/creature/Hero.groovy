package core.card.creature

import static core.EntityType.HERO
import static core.TagType.*

class Hero implements Creature {
    Hero() {}

    Hero(int attack, int health, int armor, String name) {
        this[TYPE] = HERO
        this[ARMOR] = armor
        this[NAME] = name
        this[ATTACK] = this[NATIVE_ATTACK] = attack
        this[HEALTH] = this[MAX_HEALTH] = this[NATIVE_HEALTH] = health
        this[PENDING_DESTROY] = false
    }

    Creature copy() {
        def h = new Hero()
        h.link = link.copy()
        h.player = player.copy()
        h.tags.putAll(tags)
        h.buffs = buffs*.copy()
        h
    }

    String toString() {
        "Hero{'${this[NAME]}',${this[ATTACK]}/${this[HEALTH]}}"
    }

}
