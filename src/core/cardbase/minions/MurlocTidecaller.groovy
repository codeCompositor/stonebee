package core.cardbase.minions

import core.Game
import core.buff.AttackBuff
import core.card.creature.Creature
import core.card.creature.Minion
import core.phase.EarlyOnSummonPhase
import core.phase.Phase
import core.phase.PhaseTrigger

public class MurlocTidecaller extends Minion {
    MurlocTidecaller() {
        super(1, 2, 1, "Murloc Tidecaller")
        playTriggers.add(new MTPhaseTrigger())
    }

    class MTPhaseTrigger extends PhaseTrigger {
        boolean trigger(Phase phase, Game game) {
            phase instanceof EarlyOnSummonPhase && phase.minion.getFrom(game)['race'] == Creature.Race.MURLOC;
        }

        void occur(Game game) {
            super.occur(game);
            entity.getFrom(game).buffs.add(new AttackBuff(1));
        }
    }
}
