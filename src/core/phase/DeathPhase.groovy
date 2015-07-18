package core.phase

import core.Game
import core.Link
import core.Utils
import core.card.ZoneType
import core.card.creature.Creature

public class DeathPhase extends Phase {
    private final List<Link<Creature>> targets;

    public DeathPhase(List<Link<Creature>> targets) {
        super(true);
        this.targets = targets;
    }

    @Override
    public void occur(Game game) {
        super.occur(game);
        targets.each {
            Utils.moveEntity(game, ZoneType.PLAY, ZoneType.GRAVEYARD, it)
            game.addPhase(new CreatureDeathPhase(it)) //TODO: Add deathrattle
        }
    }

    public List<Link<Creature>> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return String.format("DeathEvent{targets=%s}", targets);
    }
}
