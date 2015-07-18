package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Weapon

class Hero implements Creature {
    Weapon weapon
    Link link
    Link<Player> player
    List<Buff> buffs

    Hero(){}

    Hero(int attack, int health, int armor, String name) {
        this['armor'] = armor
        this['name'] = name
        this['attack'] = this['nativeAttack'] = attack
        this['health'] = this['maxHealth'] = this['nativeHealth'] = health
        this['pendingDestroy'] = false;
        this['canBeTargeted'] = true;
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
        "Hero{'${this['name']}',${this['attack']}/${this['health']}}"
    }

}
