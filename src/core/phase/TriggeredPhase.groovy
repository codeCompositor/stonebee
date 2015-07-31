package core.phase

import core.Link

class TriggeredPhase extends Phase implements Comparable<TriggeredPhase> {
    Link entity
    int priority
    Closure<Boolean> trigger

    TriggeredPhase(int priority = 0) {
        this.priority = priority
    }

    int compareTo(TriggeredPhase trigger) {
        Integer.compare(priority, trigger.priority)
    }

    TriggeredPhase copy() {
        def clone = (TriggeredPhase) super.copy()
        clone.priority = priority
        clone
    }
}
