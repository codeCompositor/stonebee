package core.buff

import core.Copyable
import core.Entity

abstract class Buff implements Copyable {
    abstract void apply(Entity entity)

    Buff plus(Buff buff) {
        new BuffUnion(this, buff)
    }

    abstract Buff copy()
}
