package core.buff

import core.Entity
import core.card.Card

public class ManaBuff implements Buff {
    final Closure<Integer> f;

    ManaBuff(Closure<Integer> f) {
        this.f = f;
    }

    boolean apply(Entity entity) {
        if (entity instanceof Card) {
            entity['mana'] = f(entity['mana'])
            true
        }
        false
    }

    ManaBuff copy() {
        new ManaBuff(f)
    }
}
