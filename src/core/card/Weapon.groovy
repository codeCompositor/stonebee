package core.card

import core.Link
import core.Player

public class Weapon implements Card {//extends Card {
    int attack;
    int durability;
    int mana;
    String name
    Link link
    Link<Player> player
    boolean pendingDestroy

    Weapon() {}

    public Weapon(int manaCost, String name) {
        // super(mana, name);
    }

    void reduceDurability() {
        --durability;
    }

    Weapon copy() {
        def copy = new Weapon()
        copy.attack = attack
        copy.durability = durability
        copy.mana = mana
        copy.name = name
        copy.link = link.copy()
        copy.player = player.copy()
        copy.pendingDestroy = pendingDestroy
        return copy
    }
}
