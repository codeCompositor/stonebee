package core.phase

import core.Game
import core.Link
import core.card.creature.Minion

import static core.card.ZoneType.PLAY

/**
 * The Battlecry is fully resolved here.
 */
class BattlecryPhase extends Phase {
    Link<Minion> minion

    BattlecryPhase(Link<Minion> minion) {
        super(true)
        this.minion = minion
    }

    void occur(Game game) {
        super.occur(game)
        if (!minion[game].player[game].zones[PLAY].contains(minion)) {
            System.out.printf("Battlecry Phase of %s aborted because he is not in play zone\n", minion)
        }
    }

    BattlecryPhase copy() {
        BattlecryPhase o = (BattlecryPhase) super.copy()
        o.minion = minion.copy()
        o
    }
}
