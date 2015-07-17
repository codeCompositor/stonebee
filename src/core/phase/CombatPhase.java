package core.phase;

import core.Game;
import core.Link;
import core.Player;
import core.card.creature.Creature;

public class CombatPhase extends Phase {
    private final Link<? extends Creature> attacker;
    private final Link<? extends Creature> defender;

    public CombatPhase(Link<? extends Creature> attacker, Link<? extends Creature> defender, boolean game) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void occur(Game game) {
        defender.getFrom(game).defend(attacker, game);
        attacker.getFrom(game).attack(defender, game);
    }

    @Override
    public String toString() {
        return String.format("AttackEvent{attacker=%s, defender=%s}", attacker, defender);
    }
}
