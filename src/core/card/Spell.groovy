package core.card

import core.Link
import core.Player;
import core.phase.SpellTextPhase;

class Spell implements Card {
    SpellTextPhase text

    String name
    Link link
    Link<Player> player
    boolean pendingDestroy
    int mana

    Spell(int mana, String name) {
        this.mana = mana
        this.name = name
    }

    Spell copy() {
        return null
    }
}
