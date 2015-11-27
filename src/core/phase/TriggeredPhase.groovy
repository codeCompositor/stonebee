package core.phase

import core.Game
import core.Link

class TriggeredPhase extends Phase implements Comparable<TriggeredPhase> {
    Link entity
    int priority
    Closure<Boolean> trigger

    TriggeredPhase(int priority = 0) {
        this.priority = priority
    }

    void occur(Game game) {
        super.occur(game)

    }

    int compareTo(TriggeredPhase trigger) {
        Integer.compare(priority, trigger.priority)
    }

    TriggeredPhase copy() {
        def copy = (TriggeredPhase) super.copy()
        copy.priority = priority
        copy
    }
}
