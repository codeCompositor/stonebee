package core.phase

import core.Copyable
import core.Game
import core.Link

class Phase implements Copyable {
    boolean pendingResolution
    Link owner

    Phase() {
        this.pendingResolution = false
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
        Phase copy = new Phase()
        copy.pendingResolution = pendingResolution
        copy
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

    Phase multiply(int number) {
        def phase = this
        new Phase() {
            void occur(Game game) {
                super.occur(game)
                number.times { phase.occur(game) }
            }
        }
    }
}
