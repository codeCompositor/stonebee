package core.cardbase.spells

import core.card.AOESpell
import core.phase.DamagePhase

import static core.Selector.ENEMY_MINIONS

class Flamestrike extends AOESpell {
    Flamestrike() {
        super(7, "Flamestrike", new DamagePhase(4), ENEMY_MINIONS)
    }
}
