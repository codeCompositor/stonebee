package core.phase;

import core.Game;

public class PhaseTrigger extends Phase implements Comparable<PhaseTrigger> {
    private int priority;

    public PhaseTrigger() {
        this(0);
    }

    public PhaseTrigger(int priority) {
        super(false);
        this.priority = priority;
    }

    public boolean trigger(Phase phase, Game game) {
        return false;
    }

    @Override
    public int compareTo(PhaseTrigger trigger) {
        return Integer.compare(priority, trigger.priority);
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public PhaseTrigger copy() {
        PhaseTrigger clone = (PhaseTrigger) super.copy();
        clone.priority = priority;
        return clone;
    }
}
