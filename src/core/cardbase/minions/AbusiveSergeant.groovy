package core.cardbase.minions

import core.Game
import core.Link
import core.buff.AttackBuff
import core.buff.Buff
import core.card.creature.Creature
import core.card.creature.Minion
import core.phase.EndOfTurnPhase
import core.phase.TargetableBattlecryPhase
import core.phase.TriggeredPhase

class AbusiveSergeant extends Minion {
    AbusiveSergeant() {
        super(2, 1, 1, "Abusive Sergeant")
        battlecry = new ASBattlecry()
    }

    private class ASBattlecry extends TargetableBattlecryPhase {
        public ASBattlecry() {
            super()
        }

        void occur(Game game) {
            super.occur(game)

            Buff buff = new AttackBuff(2)
            Link<Buff> link = new Link<>(buff, getTarget()[game].getBuffs())
            game.triggers.add(new Link(new ASTrigger(getTarget(), link), game))
        }

        List<Link<Creature>> getValidTargets(Game game) {
            super.getValidTargets(game).findAll({ it[game] instanceof Minion && it != target })
        }
    }

    private class ASTrigger extends TriggeredPhase {
        final Link<Creature> target
        final Link<Buff> buff

        ASTrigger(Link<Creature> target, Link<Buff> buff) {
            this.target = target
            this.buff = buff
            this.trigger = { phase, game ->
                phase instanceof EndOfTurnPhase
            }
        }

        void occur(Game game) {
            super.occur(game)

            Creature c = target[game]
            c.getBuffs().remove(buff[c.buffs])
            game.triggers.remove(new Link(this, game))
        }
    }
}
