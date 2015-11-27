package core.cardbase.spells

import core.buff.HealthBuff
import core.card.Spell
import core.phase.AOEPhase
import core.phase.BuffPhase

import static core.Selector.MINIONS

class Equality extends Spell {
    Equality() {
        super(2, "Equality", new AOEPhase(new BuffPhase(new HealthBuff({ 1 })), MINIONS))
    }
}
