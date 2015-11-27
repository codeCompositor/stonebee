package core.phase

import core.Game
import core.Selector


class AOEPhase extends Phase {
    TargetablePhase phase
    Selector selector

    AOEPhase(TargetablePhase phase, Selector selector) {
        this.phase = phase
        this.selector = selector
    }

    void occur(Game game) {
        super.occur(game)
        game.addPhase(new Phase())
        selector.eval(game, owner).each {
            def p = phase.copy()
            p.owner = owner.owner
            p.target = it
            game.addPhase(p, false)
        }
    }
}
