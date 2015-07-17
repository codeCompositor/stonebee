package core.phase;

import core.Game;
import core.Link;
import core.card.creature.Minion;

public class SecretActivationPhase extends Phase {
    private final Link<? extends Minion> minion;

    public SecretActivationPhase(Link<? extends Minion> minion, boolean outermost) {
        super(outermost);
        this.minion = minion;
    }

    @Override
    public void occur(Game game) {
        if (!game.play.contains(minion)) {
            System.out.printf("Secret Activation Phase of %s aborted because he is not in play zone\n", minion);
            return;
        }
        super.occur(game);
    }

    public Link<? extends Minion> getMinion() {
        return minion;
    }
}
