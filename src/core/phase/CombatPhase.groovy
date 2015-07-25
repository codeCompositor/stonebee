package core.phase

import core.Game
import core.Link
import core.card.creature.Creature

class CombatPhase extends Phase {
    private final Link<? extends Creature> attacker
    private final Link<? extends Creature> defender

    CombatPhase(Link<? extends Creature> attacker, Link<? extends Creature> defender) {
        this.attacker = attacker
        this.defender = defender
    }

    void occur(Game game) {
        super.occur(game)
        defender[game].defend(attacker, game)
        attacker[game].attack(defender, game)
    }

    String toString() {
        "CombatPhase{attacker=$attacker, defender=$defender}"
    }
}
