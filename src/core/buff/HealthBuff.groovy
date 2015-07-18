package core.buff

import core.Entity
import core.card.creature.Creature

public class HealthBuff extends Buff {
    final Closure<Integer> f;

    HealthBuff(Closure<Integer> f) {
        this.f = f;
    }

    boolean apply(Entity entity) {
        if (entity instanceof Creature) {
            entity['maxHealth'] = f(entity['maxHealth'])
            true
        }
        false
    }

    HealthBuff copy() {
        new HealthBuff(f)
    }
}
