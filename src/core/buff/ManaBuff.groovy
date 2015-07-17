package core.buff;

import core.Entity;
import core.card.Card;
import core.card.creature.Creature;

import java.util.function.Function;

public class ManaBuff implements Buff {
    final Closure<Integer> f;

    public ManaBuff(Closure<Integer> f) {
        this.f = f;
    }

    /**
     * Apply this buff to x mana
     * @param x initial mana
     * @return resulting mana
     */
    public int apply(int x) {
        return x;
    }

    @Override
    boolean apply(Entity entity) {
        if (!(entity instanceof Card))
            return false
        Card card = entity as Card
        card.mana = f(card.mana)
        return true;
    }

    @Override
    public ManaBuff copy() {
        return new ManaBuff(f);
    }
}
