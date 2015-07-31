package core.card

import core.Game
import core.Selector
import core.phase.Phase
import core.phase.SpellTextPhase
import core.phase.TargetablePhase


class AOESpell extends Spell {
    TargetablePhase phase
    Selector selector

    AOESpell(int mana, String name, TargetablePhase phase, Selector selector) {
        super(mana, name)
        this.phase = phase
        this.selector = selector
        text = new AOESpellText()
    }

    class AOESpellText extends SpellTextPhase {
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
