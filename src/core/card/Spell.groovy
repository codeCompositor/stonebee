package core.card

import core.Link
import core.phase.SpellTextPhase

import static core.EntityType.SPELL
import static core.TagType.*

class Spell implements Card {
    SpellTextPhase text

    Spell(int mana, String name) {
        this[TYPE] = SPELL
        this[MANA] = this[NATIVE_MANA] = mana
        this[NAME] = name
        this[PENDING_DESTROY] = false
    }

    void setLink(Link link) {
        core_Entity__link = link
        if (text != null)
            text.setSpell(link)
    }

    Spell copy() { // TODO: Write this method
        return null
    }
}
