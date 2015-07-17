package core.phase;

import core.Game;
import core.Link;

public class CreatureDeathPhase extends Phase {
    private final Link creature;

    public CreatureDeathPhase(Link creature) {
        super(false);
        this.creature = creature;
    }

    @Override
    public void occur(Game game) {
        super.occur(game);
    }

    public Link getCreature() {
        return creature;
    }
}
