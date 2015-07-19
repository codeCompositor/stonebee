package core.phase

import core.Game
import core.Link

class PhaseTrigger extends Phase implements Comparable<PhaseTrigger> {
    Link entity
    int priority

    PhaseTrigger() {
        this(0)
    }

    PhaseTrigger(int priority) {
        super(false)
        this.priority = priority
    }

    boolean trigger(Phase phase, Game game) {
        false
    }

    int compareTo(PhaseTrigger trigger) {
        Integer.compare(priority, trigger.priority)
    }

    PhaseTrigger copy() {
        def clone = (PhaseTrigger) super.copy()
        clone.priority = priority
        clone
    }
}
