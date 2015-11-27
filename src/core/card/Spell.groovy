package core.card

import core.Link
import core.phase.Phase

import static core.EntityType.SPELL
import static core.TagType.*

class Spell implements Card {
    Phase text

    Spell(int mana, String name, text) {
        this[TYPE] = SPELL
        this[MANA] = this[NATIVE_MANA] = mana
        this[NAME] = name
        this[PENDING_DESTROY] = false
        //text.type = SPELL_TEXT//TODO: Add PhaseType
        this.text = text
    }

    def setLink(Link link) {
        core_Entity__link = link
        println this.link
        if (text != null)
            text.setOwner(link)
    }

    Spell copy() { // TODO: Write this method
        return null
    }
}
