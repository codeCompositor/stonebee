package core.card

import core.Link
import core.Player
import core.phase.SpellTextPhase

import static core.Tags.*

class Spell implements Card {
    SpellTextPhase text

    Link link
    Link<Player> player

    Spell(int mana, String name) {
        this[MANA] = this[NATIVE_MANA] = mana
        this[NAME] = name
        this[PENDING_DESTROY] = false
    }

    Spell copy() { // TODO: Write this method
        return null
    }
}
