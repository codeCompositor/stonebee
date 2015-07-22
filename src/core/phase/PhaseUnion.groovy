package core.phase

import core.Game

class PhaseUnion extends Phase {
    Phase[] phases

    PhaseUnion(Phase... phases) {
        this.phases = phases
    }

    void occur(Game game) {
        super.occur(game)
        phases.each { it.occur(game) }
    }

    PhaseUnion copy() {
        def copy = (PhaseUnion) super.copy()
        copy.phases = phases*.copy()
        return copy
    }
}
