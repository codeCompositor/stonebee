package core;

import core.card.creature.Creature;

import java.util.List;

public interface Targetable {
    Link<Creature> getTarget();

    void setTarget(Link<Creature> target);

    List<Link<Creature>> getValidTargets(Game game);
}
