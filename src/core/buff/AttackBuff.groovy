package core.buff

import core.Entity
import core.card.creature.Creature

public class AttackBuff implements Buff {
    final Closure<Integer> f;

    AttackBuff(Closure<Integer> f) {
        this.f = f;
    }

    /**
     * Apply this buff to x attack
     * @param x initial attack
     * @return resulting attack
     */
    int apply(int x) {
        return x;
    }

    @Override
    boolean apply(Entity entity) {
        if (!(entity instanceof Creature))
            return false;
        Creature creature = entity as Creature;
        creature.attack = f(creature.attack);
        return true;
    }

    @Override
    AttackBuff copy() {
        return new AttackBuff(f);
    }
}
