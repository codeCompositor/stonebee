package core.phase

import core.Entity
import core.Game
import core.Link
import core.card.creature.Creature

public class DamagePhase extends Phase {
    final int damage
    final Link<Entity> attacker
    final Link<Entity> target

    DamagePhase(int damage, Link<Entity> target, Link<Entity> attacker) {
        super(false)
        this.target = target
        this.damage = damage
        this.attacker = attacker
    }

    void occur(Game game) {
        super.occur(game)
        if (target.getFrom(game) instanceof Creature) {
            target.getFrom(game).takeDamage(damage)
            return
        }
        throw new Exception("Only Creature can take damage")
    }

    String toString() {
        "DamageEvent{target=$target, damage=$damage}"
    }
}
