package core.phase;

import core.Game;
import core.Link;
import core.Player;
import core.PlayerOwnable;
import core.card.Card;
import core.card.creature.Creature;

public class DamagePhase extends Phase {
    private final Link<? extends Card> attacker;
    private final int damage;
    private final Link<? extends Creature> target;

    public DamagePhase(int damage, Link<? extends Creature> target, Link<? extends Card> attacker) {
        super(false);
        this.target = target;
        this.damage = damage;
        this.attacker = attacker;
    }

    @Override
    public void occur(Game game) {
        target.getFrom(game).takeDamage(damage);
        super.occur(game);
    }

    public Link getAttacker() {
        return attacker;
    }

    public int getDamage() {
        return damage;
    }

    public Link getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return String.format("DamageEvent{target=%s, damage=%s}", target, damage);
    }
}
