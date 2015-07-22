package core.phase

import core.Game
import core.Link
import core.card.creature.Minion

import static core.card.ZoneType.PLAY

/**
 * Cobalt Guardian and Murloc Tidecaller Queue and resolve here.
 * This phase is only used to activate triggers
 */
public class EarlyOnSummonPhase extends Phase {
    final Link<Minion> minion

    EarlyOnSummonPhase(Link<Minion> minion, boolean outermost) {
        super(outermost)
        this.minion = minion
    }

    void occur(Game game) {
        if (!game.zones[PLAY].contains(minion)) {
            System.out.printf("Early On Summon Phase of %s aborted because he is not in play zone", minion)
            return
        }
        super.occur(game)
    }
}
