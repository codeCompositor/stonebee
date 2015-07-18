package core.cardbase.minions

import core.Game
import core.card.creature.Minion
import core.phase.DamagePhase
import core.phase.TargetableBattlecryPhase

class IronforgeRifleman extends Minion {
    IronforgeRifleman() {
        super(2, 2, 3, "Ironforge Rifleman");
        battlecry = new IRBattlecry();
    }

    class IRBattlecry extends TargetableBattlecryPhase {
        void occur(Game game) {
            super.occur(game);

            game.addPhase(new DamagePhase(1, target, minion));
        }
    }
}
