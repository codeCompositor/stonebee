package core.buff

import core.Entity

class BuffUnion extends Buff {
    Buff[] buffs

    BuffUnion(Buff... buffs) {
        this.buffs = buffs
    }

    void apply(Entity entity) {
        buffs.each { it.apply(entity) }
    }

    BuffUnion copy() {
        new BuffUnion(buffs*.copy() as Buff[])
    }
}
