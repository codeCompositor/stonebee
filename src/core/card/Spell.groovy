package core.card

import core.Link
import core.Player
import core.phase.SpellTextPhase

import static core.EntityType.SPELL
import static core.TagType.*

class Spell implements Card {
    SpellTextPhase text

    Link link
    Link<Player> player

    Spell(int mana, String name) {
        this[TYPE] = SPELL
        this[MANA] = this[NATIVE_MANA] = mana
        this[NAME] = name
        this[PENDING_DESTROY] = false
    }

    Spell copy() { // TODO: Write this method
        return null
    }
}
