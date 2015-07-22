package core.cardbase.spells

import core.Game
import core.Link
import core.buff.HealthBuff
import core.card.Spell
import core.card.creature.Minion
import core.phase.SpellTextPhase

import static core.card.ZoneType.PLAY

class Equality extends Spell {
    Equality() {
        super(2, "Equality")
        text = new EqualityTextPhase()
    }

    void setLink(Link link) {
        super.setLink(link)
        text.setSpell(link)
    }

    private class EqualityTextPhase extends SpellTextPhase {
        void occur(Game game) {
            super.occur(game)
            def buff = new HealthBuff({ x -> 1 })
            for (link in game.zones[PLAY]) {
                def c = link.getFrom(game)
                if (c instanceof Minion)
                    c.buffs.add(buff)
            }
        }
    }
}
