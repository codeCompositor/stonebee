package core.phase;

import core.Game;
import core.Link;
import core.card.Spell;

public class SpellTextPhase extends Phase {
    private Link<Spell> spell;

    public SpellTextPhase() {
        this(null);
    }

    public SpellTextPhase(Link<Spell> spell) {
        super(true);
        this.spell = spell;
    }

    @Override
    public void occur(Game game) {
        super.occur(game);
    }

    public Link<Spell> getSpell() {
        return spell;
    }

    public void setSpell(Link<Spell> spell) {
        this.spell = spell;
    }

    @Override
    public SpellTextPhase copy() {
        SpellTextPhase o = (SpellTextPhase) super.copy();
        o.spell = spell.copy();
        return o;
    }
}
