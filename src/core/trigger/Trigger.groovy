package core.trigger

import core.Game
import core.Link
import core.phase.Phase

class Trigger extends Phase implements Comparable<Trigger> {
    Link entity
    int priority

    Trigger() {
        this(0)
    }

    Trigger(int priority) {
        super(false)
        this.priority = priority
    }

    boolean trigger(Phase phase, Game game) {
        false
    }

    int compareTo(Trigger trigger) {
        Integer.compare(priority, trigger.priority)
    }

    Trigger copy() {
        def clone = (Trigger) super.copy()
        clone.priority = priority
        clone
    }
}
