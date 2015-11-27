package core.phase

import core.Game
import core.Selector


class SelectOnePhase extends Phase {
    TargetablePhase phase
    Selector selector

    SelectOnePhase(TargetablePhase phase, Selector selector) {
        this.phase = phase
        this.selector = selector
    }

    void occur(Game game) {
        super.occur(game)
        phase.owner = owner
        phase.target = game.currentTarget //TODO
        game.addPhase(phase)
    }
}
