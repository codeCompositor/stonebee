package core;

import core.phase.PhaseTrigger;

import java.util.List;

public interface TriggerOwner {
    List<Link<PhaseTrigger>> getTriggers();

    void setTriggers(List<Link<PhaseTrigger>> triggers);
}
