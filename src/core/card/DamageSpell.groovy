package core.card

import core.Selector


class DamageSpell extends Spell {
    DamageSpell(int mana, String name, Selector selector, int damage) {
        super(mana, name)
    }
}
