package core.phase;

import core.Game;
import core.Link;

/**
 * Wild Pyromancer and Flamewaker Queue and resolve here.
 */
public class AfterSpellPhase extends Phase {
    private Link spell;

    public AfterSpellPhase(Link spell, boolean outermost) {
        super(outermost);
        this.spell = spell;
    }

    @Override
    public void occur(Game game) {
        super.occur(game);
    }

    public Link getSpell() {
        return spell;
    }

    @Override
    public AfterSpellPhase copy() {
        AfterSpellPhase o = (AfterSpellPhase) super.copy();
        o.spell = spell.copy();
        return o;
    }
}
