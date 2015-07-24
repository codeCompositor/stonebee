package core.cardbase.spells

import core.Game
import core.Link
import core.card.Spell
import core.phase.SpellTextPhase
import core.selector.Selector

import static core.EntityType.MINION
import static core.Side.ENEMY
import static core.TagType.*
import static core.card.ZoneType.PLAY

class Flamestrike extends Spell {
    Selector selector

    Flamestrike() {
        super(7, "Flamestrike")
        text = new FlamestrikeTextPhase()
        selector = new Selector(TYPE - MINION, SIDE - ENEMY, ZONE - PLAY)
    }

    void setLink(Link link) {
        super.setLink(link);
        text.setSpell(link);
    }

    private class FlamestrikeTextPhase extends SpellTextPhase {
        void occur(Game game) {
            game.damage(4, selector, spell)
            super.occur(game)
        }
    }
}
