package core.cardbase.minions

import core.Game
import core.buff.AttackBuff
import core.card.creature.Creature
import core.card.creature.Minion
import core.phase.EarlyOnSummonPhase
import core.phase.Phase
import core.phase.TriggeredPhase

import static core.TagType.RACE
import static core.card.ZoneType.PLAY

public class MurlocTidecaller extends Minion {
    MurlocTidecaller() {
        super(1, 2, 1, "Murloc Tidecaller")
        zoneTriggers[PLAY].add(new MTTrigger())
    }

    class MTTrigger extends TriggeredPhase {
        MTTrigger() {
            super()
            trigger = { Phase phase, Game game ->
                phase instanceof EarlyOnSummonPhase && phase.minion[game][RACE] == Creature.Race.MURLOC
            }
        }

        void occur(Game game) {
            super.occur(game)
            entity[game].buffs.add(new AttackBuff(1))
        }
    }
}
