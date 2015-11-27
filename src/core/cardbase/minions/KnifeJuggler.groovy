package core.cardbase.minions

import core.card.creature.Minion
import core.phase.DamagePhase
import core.phase.EarlyOnSummonPhase
import core.phase.SelectOnePhase
import core.phase.TriggeredPhase

import static core.Selector.ENEMY_CREATURES
import static core.card.ZoneType.PLAY

class KnifeJuggler extends Minion {
    KnifeJuggler() {
        super(3, 2, 2, "Knife Juggler")
        zoneTriggers[PLAY].add(new TriggeredPhase({
            it instanceof EarlyOnSummonPhase
        }, new SelectOnePhase(new DamagePhase(1), ENEMY_CREATURES)))
    }
}
