package core.cardbase.minions

import core.Game
import core.Link
import core.buff.AttackBuff
import core.buff.HealthBuff
import core.card.creature.Creature
import core.card.creature.Minion
import core.phase.TargetableBattlecryPhase

class ShatteredSunCleric extends Minion {
    ShatteredSunCleric() {
        super(3, 2, 3, "Shattered Sun Cleric")
        battlecry = new SSCBattlecry();
        //battlecry = new SelectOnePhase(new BuffPhase(new AttackBuff(1) + new HealthBuff(1)), MINIONS)
    }

    class SSCBattlecry extends TargetableBattlecryPhase {
        void occur(Game game) {
            super.occur(game);
            def buff = new AttackBuff(1) + new HealthBuff(1)
            target[game].buffs.add(buff)
        }

        List<Link<Creature>> getValidTargets(Game game) {
            super.getValidTargets(game).findAll({ it[game] instanceof Minion && it != target })
        }
    }
}
