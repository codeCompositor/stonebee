package core.phase

import core.Link

public class CreatureDeathPhase extends Phase {
    final Link creature;

    CreatureDeathPhase(Link creature) {
        super(false)
        this.creature = creature
    }
}
