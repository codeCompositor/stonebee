package core;

import core.phase.TriggeredPhase;

import java.util.List;

public interface TriggerOwner {
    List<Link<TriggeredPhase>> getTriggers();

    void setTriggers(List<Link<TriggeredPhase>> triggers);
}
