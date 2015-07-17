package core.phase;

import core.Copyable;
import core.Game;

import java.util.PriorityQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Phase implements Copyable {
    private final boolean outermost;
    private boolean pendingResolution;

    public Phase() {
        this(false);
    }

    public Phase(boolean outermost) {
        this.pendingResolution = false;
        this.outermost = outermost;
    }

    /**
     * Called when phase occurs.
     * This method is used in sequence of events, cause only main phase can have death phase after it.
     */
    public void occur(Game game) {
        checkTriggers(game);
        pendingResolution = true;
    }

    private void checkTriggers(Game game) {
        PriorityQueue<Phase> queue = new PriorityQueue<>();
        game.triggers.forEach(t -> {
            if (t.trigger(this, game)) queue.add(t);
        });
        queue.forEach(game::addPhase);
    }

    public boolean isOutermost() {
        return outermost;
    }

    public boolean isPendingResolution() {
        return pendingResolution;
    }

    public void setPendingResolution(boolean pendingResolution) {
        this.pendingResolution = pendingResolution;
    }

    public Phase copy() {
        Phase clone = new Phase(outermost);
        clone.pendingResolution = pendingResolution;
        return clone;
    }
}
