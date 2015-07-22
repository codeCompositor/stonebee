package core.buff

import core.Entity
import core.card.creature.Creature

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
            entity['attack'] = f(entity['attack'])
            return
        }
        throw new Exception("Attack Buff can be applied only to Creature")
    }

    AttackBuff copy() {
        new AttackBuff(f)
    }
}
