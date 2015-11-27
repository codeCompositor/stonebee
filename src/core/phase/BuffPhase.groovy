package core.phase

import core.Entity
import core.Game
import core.Link
import core.buff.Buff

class BuffPhase extends TargetablePhase {
    final Buff buff

    BuffPhase(Buff buff) {
        this.buff = buff
    }

    BuffPhase(Buff buff, Link<Entity> target, Link<Entity> owner) {
        this.target = target
        this.buff = buff
        this.owner = owner
    }

    void occur(Game game) {
        super.occur(game)
        target[game].buffs.add(buff)
    }

    BuffPhase copy() {
        def copy = new BuffPhase(buff.copy())
        copy.owner = owner == null ? null : owner.copy()
        copy.target = target == null ? null : target.copy()
        copy
    }
}
