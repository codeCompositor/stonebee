package core.buff

import core.Entity
import core.card.creature.Creature

import static core.TagType.ATTACK

class AttackBuff extends Buff {
    final Closure<Integer> f;

    AttackBuff(Closure<Integer> f) {
        this.f = f;
    }

    AttackBuff(int delta) {
        this.f = { it + delta }
    }

    void apply(Entity entity) {
        if (entity instanceof Creature) {
            entity[ATTACK] = f(entity[ATTACK])
            return
        }
        throw new Exception("Attack Buff can be applied only to Creature")
    }

    AttackBuff copy() {
        new AttackBuff(f)
    }
}
