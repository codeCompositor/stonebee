package core.phase;

import core.Game;
import core.Link;
import core.Utils;
import core.card.ZoneType;
import core.card.creature.Creature;

import java.util.List;

public class DeathPhase extends Phase {
    private final List<Link<Creature>> targets;

    public DeathPhase(List<Link<Creature>> targets) {
        super(true);
        this.targets = targets;
    }

    @Override
    public void occur(Game game) {
        super.occur(game);
        for (Link<Creature> c : targets) {
            Utils.moveCard(game, ZoneType.PLAY, ZoneType.GRAVEYARD, c);
            //game.moveCard(Zone.Type.PLAY, Zone.Type.GRAVEYARD, c);
            //c.getFrom(game).getTriggers().forEach(game.triggers::remove);
        }
        targets.forEach(c->game.addPhase(new CreatureDeathPhase(c)));//TODO: Add deathrattle
    }

    public List<Link<Creature>> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return String.format("DeathEvent{targets=%s}", targets);
    }
}
