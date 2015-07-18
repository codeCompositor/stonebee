package core.buff

import core.Entity

public class BuffsSum extends Buff {
    Buff[] buffs

    BuffsSum(Buff... buffs) {
        this.buffs = buffs
    }

    void apply(Entity entity) {
        buffs.each { it.apply(entity) }
    }

    BuffsSum copy() {
        new BuffsSum(buffs)
    }
}
