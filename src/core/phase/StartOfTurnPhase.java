package core.phase;

import core.Game;

public class StartOfTurnPhase extends Phase {

    @Override
    public void occur(Game game) {
        //TODO:Hearthstone resets all counters related to 'this turn' (such as Deaths and cards played this turn), fills
        //TODO:your opponent's mana, flips which player's weapons are sheathed/unsheathed, flips which player's Secrets
        //TODO:are active[38], unflips your opponent's Hero Power and removes exhaustion from all characters.
        super.occur(game);
    }
}
