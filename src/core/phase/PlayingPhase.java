package core.phase;

import core.Game;
import core.Link;
import core.Player;
import core.Utils;
import core.card.Card;
import core.card.Spell;
import core.card.ZoneType;
import core.card.creature.Minion;

/**
 * This is first phase of playing card.
 * The card is removed from your hand and enters the board as a minion,
 * taking up physical space and becoming interactable.
 * In this phase card moves from hand to play zone and player's amount of mana decremented by mana cost of card
 */
public class PlayingPhase extends Phase {
    private final Link<? extends Card> link;

    public PlayingPhase(Link<? extends Card> card, boolean outermost) {
        super(outermost);
        link = card;
    }

    @Override
    public void occur(Game game) {
        if (!game.hand.contains(link)) {
            System.out.printf("Playing Phase of %s aborted because it is not in hand zone\n", link.getFrom(game));
            return;
        }
        Card card = link.getFrom(game);
        Player player = card.getPlayer(game);
        player.mana -= card.getMana();
        if (card instanceof Minion) {
            Utils.moveCard(game, ZoneType.HAND, ZoneType.PLAY, link);
            //game.triggers.addAll(((Minion) card).getPlayTriggers());//TODO: Change triggers (add different types of triggers)
        } else if (card instanceof Spell)
            Utils.moveCard(game, ZoneType.HAND, ZoneType.GRAVEYARD, link);
        super.occur(game);
    }

    public Link<? extends Card> getCard() {
        return link;
    }
}
