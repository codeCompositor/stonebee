package core.buff;

import core.Copyable;
import core.Entity;
import core.card.Card;
import core.card.creature.Creature;
import groovy.lang.Closure;

import java.io.Closeable;
import java.util.function.Function;

public class HealthBuff implements Buff {
    final Closure<Integer> f;

    public HealthBuff(Closure<Integer> f) {
        this.f = f;
    }

    /**
     * Apply this buff to <tt>x</tt> health
     * @param x initial health
     * @return resulting health
     */
    int apply(Integer x) {
        return x;
    }

    @Override
    public boolean apply(Entity entity) {
        if (!(entity instanceof Creature))
            return false
        Creature creature = entity as Creature
        creature.maxHealth = f(creature.maxHealth)
        return true
    }

    @Override
    public HealthBuff copy() {
        return new HealthBuff(f);
    }
}
