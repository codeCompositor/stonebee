package core.buff

import core.Entity
import core.card.creature.Creature

class HealthBuff extends Buff {
    final Closure<Integer> f;

    HealthBuff(Closure<Integer> f) {
        this.f = f;
    }

    HealthBuff(int delta) {
        this.f = { it + delta };
    }

    void apply(Entity entity) {
        if (entity instanceof Creature) {
            entity['maxHealth'] = f(entity['maxHealth'])
            return
        }
        throw new Exception("Health Buff can be applied only to Creature")
    }

    HealthBuff copy() {
        new HealthBuff(f)
    }
}
