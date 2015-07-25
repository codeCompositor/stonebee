package core.phase

import core.Copyable
import core.Game

class Phase implements Copyable {
    final boolean outermost
    boolean pendingResolution

    Phase() {
        this(false)
    }

    Phase(boolean outermost) {
        this.pendingResolution = false
        this.outermost = outermost
    }

    /**
     * Called when phase occurs.
     * This method is used in sequence of events, cause only main phase can have death phase after it.
     */
    void occur(Game game) {
        checkTriggers(game)
        pendingResolution = true
    }

    void checkTriggers(Game game) {
        def queue = new PriorityQueue<Phase>()
        game.triggers.each { if (it[game].trigger(this, game)) queue.add(it[game]) }
        queue.each { game.addPhase(it) }
    }

    Phase copy() {
        Phase clone = new Phase(outermost)
        clone.pendingResolution = pendingResolution
        clone
    }

    Phase plus(Phase phase2) {
        def phase1 = this
        new Phase() {
            void occur(Game game) {
                super.occur(game)
                phase1.occur(game)
                phase2.occur(game)
            }
        }
    }
}
