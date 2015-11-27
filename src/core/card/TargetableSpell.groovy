package core.card

import core.Game
import core.Selector
import core.phase.Phase
import core.phase.SpellTextPhase
import core.phase.TargetablePhase


class TargetableSpell extends Spell {
    TargetablePhase phase
    Selector selector

    TargetableSpell(int mana, String name, TargetablePhase phase, Selector selector) {
        super(mana, name)
        this.phase = phase
        this.selector = selector
        text = new TargetableSpellText()
    }

    class TargetableSpellText extends SpellTextPhase {
        void occur(Game game) {
            super.occur(game)
            game.addPhase(new Phase())
            selector.eval(game, spell).each {
                def p = phase.copy()
                p.owner = spell
                p.target = it
                game.addPhase(p, false)
            }
        }
    }
}
