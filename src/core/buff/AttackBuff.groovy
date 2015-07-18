package core.buff

import core.Entity
import core.card.creature.Creature

public class AttackBuff extends Buff {
    final Closure<Integer> f;

    AttackBuff(Closure<Integer> f) {
        this.f = f;
    }

    boolean apply(Entity entity) {
        if (entity instanceof Creature) {
            entity['attack'] = f(entity['attack'])
            true
        }
        false
    }

    AttackBuff copy() {
        new AttackBuff(f)
    }
}
