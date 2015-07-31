package core.phase

import core.Entity
import core.Game
import core.Link
import core.card.creature.Creature

public class DamagePhase extends TargetablePhase {
    final int damage

    DamagePhase(int damage) {
        this.damage = damage
    }

    DamagePhase(int damage, Link<Entity> target, Link<Entity> owner) {
        this.target = target
        this.damage = damage
        this.owner = owner
    }

    void occur(Game game) {
        super.occur(game)
        if (target[game] instanceof Creature) {
            target[game].takeDamage(damage)
            return
        }
        throw new Exception("Only Creature can take damage")
    }

    DamagePhase copy() {
        def copy = new DamagePhase(damage)
        copy.owner = owner == null ? null : owner.copy()
        copy.target = target == null ? null : target.copy()
        copy
    }

    String toString() {
        "DamageEvent{target=$target, damage=$damage}"
    }
}
