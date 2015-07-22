package core.phase

import core.Game
import core.Link
import core.card.Card
import core.card.creature.Creature

public class DamagePhase extends Phase {
    final int damage
    final Link<Card> attacker
    final Link<Creature> target

    DamagePhase(int damage, Link<Creature> target, Link<Card> attacker) {
        super(false)
        this.target = target
        this.damage = damage
        this.attacker = attacker
    }

    void occur(Game game) {
        super.occur(game)
        target.getFrom(game).takeDamage(damage)
    }

    String toString() {
        "DamageEvent{target=$target, damage=$damage}"
    }
}
