package core.cardbase.minions;

import core.Game;
import core.Link;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.buff.AttackBuff;
import core.buff.Buff;
import core.phase.EndOfTurnPhase;
import core.phase.Phase;
import core.phase.PhaseTrigger;
import core.phase.TargetableBattlecryPhase

class AbusiveSergeant extends Minion {
    AbusiveSergeant() {
        super(2, 1, 1, "Abusive Sergeant");
        battlecry = new ASBattlecry();
    }

    private class ASBattlecry extends TargetableBattlecryPhase {
        public ASBattlecry() {
            super();
        }

        void occur(Game game) {
            super.occur(game);

            Buff buff = new AttackBuff({x -> x + 2});
            Link<Buff> link = new Link<>(buff, getTarget().getFrom(game).getBuffs());
            game.triggers.add(new ASTrigger(getTarget(), link));//TODO: See what's up with triggers
        }

        List<Link<Creature>> getValidTargets(Game game) {
            def targets = super.getValidTargets(game);
            targets.remove(game.player.hero);
            targets.remove(game.opponent.hero);
            return targets;
        }

    }

    private class ASTrigger extends PhaseTrigger {
        final Link<Creature> target;
        final Link<Buff> buff;

        ASTrigger(Link<Creature> target, Link<Buff> buff) {
            this.target = target;
            this.buff = buff;
        }

        boolean trigger(Phase phase, Game game) {
            return phase instanceof EndOfTurnPhase;
        }

        void occur(Game game) {
            super.occur(game);

            Creature c = target.getFrom(game);
            c.getBuffs().remove(buff.getFrom(c.buffs));
            game.triggers.remove(this);
        }
    }
}
