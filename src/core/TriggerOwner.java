package core;

import core.phase.PhaseTrigger;

import java.util.List;

public interface TriggerOwner {
    List<Integer> getTriggers();
    void addTrigger(PhaseTrigger trigger, Game game);
    void removeTrigger(PhaseTrigger trigger, Game game);
}
