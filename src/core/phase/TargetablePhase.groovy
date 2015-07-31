package core.phase

import core.Targetable


class TargetablePhase extends Phase implements Targetable {
    TargetablePhase copy() {
        def copy = new TargetablePhase()
        copy.pendingResolution = pendingResolution
        copy.owner = owner == null ? null : owner.copy()
        copy.target = target == null ? null : target.copy()
        copy
    }
}