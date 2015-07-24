package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Weapon

import static core.Tags.*

class Hero implements Creature {
    Weapon weapon
    Link link
    Link<Player> player
    List<Buff> buffs

    Hero() {}

    Hero(int attack, int health, int armor, String name) {
        this[ARMOR] = armor
        this[NAME] = name
        this[ATTACK] = this[NATIVE_ATTACK] = attack
        this[HEALTH] = this[MAX_HEALTH] = this[NATIVE_HEALTH] = health
        this[PENDING_DESTROY] = false
    }

    Creature copy() {
        def h = new Hero()
        h.weapon = weapon == null ? null : weapon.copy()
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
