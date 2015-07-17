package core.cardbase.spells;

import core.Game;
import core.Link;
import core.card.Spell;
import core.card.creature.Minion;
import core.phase.DamagePhase;
import core.phase.SpellTextPhase;

class Flamestrike extends Spell {
    Flamestrike() {
        super(7, "Flamestrike");
        text = new FlamestrikeTextPhase();
    }

    void setLink(Link link) {
        super.setLink(link);
        text.setSpell(link);
    }

    private class FlamestrikeTextPhase extends SpellTextPhase {
        void occur(Game game) {
            System.out.println(getPlayer());
            for (link in game.oppositePlayer(getPlayer().getFrom(game)).play)
                if (link.getFrom(game) instanceof Minion)
                    game.addPhase(new DamagePhase(4, link, spell))
        }
    }
}
