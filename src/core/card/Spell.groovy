package core.card

import core.Link
import core.Player
import core.phase.SpellTextPhase

class Spell implements Card {
    SpellTextPhase text

    Link link
    Link<Player> player

    Spell(int mana, String name) {
        this['mana'] = mana
        this['name'] = name
        this['pendingDestroy'] = false
    }

    Spell copy() { // TODO: Write this method
        return null
    }
}
