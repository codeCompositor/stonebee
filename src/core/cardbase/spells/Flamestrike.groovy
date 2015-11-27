package core.cardbase.spells

import core.card.Spell
import core.phase.AOEPhase
import core.phase.DamagePhase

import static core.Selector.ENEMY_MINIONS

class Flamestrike extends Spell {
    Flamestrike() {
        super(7, "Flamestrike", new AOEPhase(new DamagePhase(4), ENEMY_MINIONS))
    }
}
