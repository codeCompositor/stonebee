package core.phase

import core.Game
import core.Link
import core.card.Card
import core.card.creature.Creature

public class DamagePhase extends Phase {
    final Link<? extends Card> attacker
    final int damage
    final Link<? extends Creature> target

    DamagePhase(int damage, Link<? extends Creature> target, Link<? extends Card> attacker) {
        super(false)
        this.target = target
        this.damage = damage
        this.attacker = attacker
    }

    void occur(Game game) {
        target.getFrom(game).takeDamage(damage)
        super.occur(game)
    }

    String toString() {
        "DamageEvent{target=$target, damage=$damage}"
    }
}
