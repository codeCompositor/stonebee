package core.cardbase.minions

import core.Game
import core.Link
import core.card.creature.Creature
import core.card.creature.Minion
import core.phase.DamagePhase
import core.phase.TargetableBattlecryPhase

class ElvenArcher extends Minion {
    ElvenArcher() {
        super(1, 1, 1, "Elven Archer");
        battlecry = new EABattlecry();
    }

    class EABattlecry extends TargetableBattlecryPhase {
        void occur(Game game) {
            super.occur(game);

            game.addPhase(new DamagePhase(1, target, minion));
        }

        List<Link<Creature>> getValidTargets(Game game) {
            super.getValidTargets(game).findAll({ it != target })
        }
    }
}
