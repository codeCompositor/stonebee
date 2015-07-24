package core.buff

import core.Entity
import core.card.Card

import static core.TagType.MANA

class ManaBuff extends Buff {
    final Closure<Integer> f

    ManaBuff(Closure<Integer> f) {
        this.f = f;
    }

    ManaBuff(int delta) {
        this.f = { it + delta }
    }

    void apply(Entity entity) {
        if (entity instanceof Card) {
            entity[MANA] = f(entity[MANA])
            return
        }
        throw new Exception("Mana Buff can be applied only to Card")
    }

    ManaBuff copy() {
        new ManaBuff(f)
    }
}
