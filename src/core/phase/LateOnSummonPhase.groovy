package core.phase

import core.Game
import core.Link
import core.card.creature.Minion

import static core.card.ZoneType.PLAY

/**
 * Starving Buzzard, One-eyed Cheat and Undertaker Queue and resolve here.
 * This phase is only used to activate triggers
 */
public class LateOnSummonPhase extends Phase {
    final Link<? extends Minion> minion

    LateOnSummonPhase(Link<? extends Minion> minion) {
        this.minion = minion
    }

    void occur(Game game) {
        if (!game.zones[PLAY].contains(minion)) {
            System.out.printf("Late On Summon Phase of %s aborted because he is not in play zone", minion)
            return
        }
        super.occur(game)
    }
}
