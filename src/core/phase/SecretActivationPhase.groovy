package core.phase

import core.Game
import core.Link
import core.card.creature.Minion

import static core.card.ZoneType.PLAY

class SecretActivationPhase extends Phase {
    final Link<? extends Minion> minion

    public SecretActivationPhase(Link<? extends Minion> minion, boolean outermost) {
        super(outermost)
        this.minion = minion
    }

    void occur(Game game) {
        if (!game.zones[PLAY].contains(minion)) {
            System.out.printf("Secret Activation Phase of %s aborted because he is not in play zone\n", minion)
            return
        }
        super.occur(game)
    }
}
