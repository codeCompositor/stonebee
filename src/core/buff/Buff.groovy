package core.buff

import core.Copyable
import core.Entity

abstract class Buff implements Copyable {
    abstract boolean apply(Entity entity);

    Buff plus(Buff buff) {
        new BuffsSum(this, buff)
    }

    abstract Buff copy();
}
