package core.phase;

import core.Game;
import core.Link;
import core.card.creature.Minion;
import core.phase.Phase;

/**
 * Cobalt Guardian and Murloc Tidecaller Queue and resolve here.
 * This phase is only used to activate triggers
 */
public class EarlyOnSummonPhase extends Phase {
    private final Link<? extends Minion> minion;

    public EarlyOnSummonPhase(Link<? extends Minion> minion, boolean outermost) {
        super(outermost);
        this.minion = minion;
    }

    @Override
    public void occur(Game game) {
        if (!game.play.contains(minion)) {
            System.out.printf("Early On Summon Phase of %s aborted because he is not in play zone", minion);
            return;
        }
        super.occur(game);
    }

    public Link<? extends Minion> getMinion() {
        return minion;
    }
}
