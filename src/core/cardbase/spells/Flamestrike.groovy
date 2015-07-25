package core.cardbase.spells

import core.Game
import core.Link
import core.Selector
import core.card.Spell
import core.phase.SpellTextPhase

import static core.EntityType.MINION
import static core.Side.ENEMY

class Flamestrike extends Spell {
    Selector selector

    Flamestrike() {
        super(7, "Flamestrike")
        text = new FlamestrikeTextPhase()
        selector = new Selector(ENEMY, MINION)
    }

    void setLink(Link link) {
        super.setLink(link);
        text.setSpell(link);
    }

    private class FlamestrikeTextPhase extends SpellTextPhase {
        void occur(Game game) {
            game.dealDamage(4, selector, spell)
            super.occur(game)
        }
    }
}
