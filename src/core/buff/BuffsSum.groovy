package core.buff

import core.Entity

public class BuffsSum extends Buff {
    Buff buff1
    Buff buff2

    BuffsSum(Buff buff1, Buff buff2) {
        this.buff1 = buff1
        this.buff2 = buff2
    }

    boolean apply(Entity entity) {
        buff1.apply(entity) && buff2.apply(entity)
    }

    BuffsSum copy() {
        new BuffsSum(buff1, buff2)
    }
}
