package core.phase;

import core.Game;
import core.Link;
import core.card.Card;

/**
 * Minions:<br>
 * Hobgoblin, Questing Adventurer, Fel Reaver, Unbound Elemental and Illidan Stormrage Queue and resolve here.
 * <br>Spells:<br>
 * All triggers on playing a card/casting a spell/playing a Secret Queue and resolve here, such as Questing Adventurer,
 * Mana Wyrm, Burly Rockjaw Trogg, Secretkeeper and Violet Teacher. Counterspell also triggers here.
 * <br>This phase is only used to activate triggers.
 */
public class OnPlayPhase extends Phase {
    private final Link<? extends Card> card;

    public OnPlayPhase(Link<? extends Card> card, boolean outermost) {
        super(outermost);
        this.card = card;
    }

    @Override
    public void occur(Game game) {
        if (!game.play.contains(card)) {
            System.out.printf("On Play Phase of %s aborted because he is not in play zone\n", card.getFrom(game));
            return;
        }
        super.occur(game);
    }

    public Link<? extends Card> getCard() {
        return card;
    }
}
