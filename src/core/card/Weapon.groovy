package core.card

import core.Link
import core.Player

import static core.Tags.DURABILITY

public class Weapon implements Card {//extends Card {
    Link link
    Link<Player> player

    Weapon() {}

    public Weapon(int manaCost, String name) {
        // super(mana, NAME);
    }

    void reduceDurability() {
        --tags[DURABILITY]
    }

    Weapon copy() {
        def copy = new Weapon()
        copy.tags.putAll(tags)
        copy.link = link.copy()
        copy.player = player.copy()
        copy
    }
}
