package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Card
import core.card.Weapon

class Hero implements Creature {
    int armor
    Weapon weapon

    String name
    Link link
    Link<Player> player
    boolean pendingDestroy
    int attack
    int health
    int nativeAttack
    int nativeHealth
    int maxHealth
    Creature.Race race
    boolean canBeTargeted
    List<Buff> buffs

    Hero(){}

    Hero(int attack, int health, int armor, String name) {
        this.armor = armor
        this.name = name
        this.attack = nativeAttack = attack
        this.health = maxHealth = nativeHealth = health
    }

    public Card.Class getHeroClass() {
        return heroClass;
    }

    @Override
    public Creature copy() {// TODO: Complete this
        def h = new Hero()
        h.armor = armor
        h.weapon = weapon == null ? null : weapon.copy()
        h.name = name
        h.link = link.copy()
        h.player = player.copy()
        h.pendingDestroy = pendingDestroy
        h.attack = attack
        h.health = health
        h.nativeAttack = nativeAttack
        h.nativeHealth = nativeHealth
        h.maxHealth = maxHealth
        h.race = race
        h.canBeTargeted = canBeTargeted
        h.buffs = buffs*.copy()
        return h
    }

    @Override
    public String toString() {
        return String.format("Hero{\"%s\",%d/%d}", name, attack, health + armor);
    }

}
