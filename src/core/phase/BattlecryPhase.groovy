package core.phase

import core.Game
import core.Link
import core.card.creature.Minion

/**
 * The Battlecry is fully resolved here.
 */
public class BattlecryPhase extends Phase {
    Link<? extends Minion> minion;

    public BattlecryPhase(Link<? extends Minion> minion) {
        super(true);
        this.minion = minion;
    }

    @Override
    public void occur(Game game) {
        super.occur(game)
        if (!minion.getFrom(game).getPlayer(game).play.contains(minion)) {
            System.out.printf("Battlecry Phase of %s aborted because he is not in play zone\n", minion);
        }
    }

    @Override
    public BattlecryPhase copy() {
        BattlecryPhase o = (BattlecryPhase) super.copy();
        o.minion = minion.copy();
        return o;
    }
}
