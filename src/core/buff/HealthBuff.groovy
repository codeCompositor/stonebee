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
        this.f = { health -> health + delta }
    }

    void apply(Entity entity) {
        if (entity instanceof Creature) {
            switch (f.maximumNumberOfParameters) {
                case 0:
                    entity[MAX_HEALTH] = f()
                    break
                case 1:
                    entity[MAX_HEALTH] = f(entity[MAX_HEALTH])
                    break
                case 2:
                    entity[MAX_HEALTH] = f(entity[MAX_HEALTH], entity)
                    break
                default:
                    throw new Exception("Health Buff function can be applied to 0, 1 or 2 arguments")
            }
            return
        }
        throw new Exception("Health Buff can be applied only to Creature")
    }

    HealthBuff copy() {
        new HealthBuff(f)
    }
}
