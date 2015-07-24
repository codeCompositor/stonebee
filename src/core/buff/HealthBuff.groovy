package core.buff

import core.Entity
import core.card.creature.Creature

import static core.TagType.MAX_HEALTH

class HealthBuff extends Buff {
    final Closure<Integer> f;

    HealthBuff(Closure<Integer> f) {
        this.f = f;
    }

    HealthBuff(int delta) {
        this.f = { it + delta }
    }

    void apply(Entity entity) {
        if (entity instanceof Creature) {
            entity[MAX_HEALTH] = f(entity[MAX_HEALTH])
            return
        }
        throw new Exception("Health Buff can be applied only to Creature")
    }

    HealthBuff copy() {
        new HealthBuff(f)
    }
}
