package core;

import core.trigger.Trigger;

import java.util.List;

public interface TriggerOwner {
    List<Link<Trigger>> getTriggers();

    void setTriggers(List<Link<Trigger>> triggers);
}
