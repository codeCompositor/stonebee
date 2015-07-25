package core.phase

import core.Game
import core.Link
import core.Utils
import core.card.Card
import core.card.Spell
import core.card.creature.Minion

import static core.TagType.MANA
import static core.card.ZoneType.*

/**
 * This is first phase of playing card.
 * The card is removed from your hand and enters the board as a minion,
 * taking up physical space and becoming interactable.
 * In this phase card moves from hand to play ZONE and player's amount of mana decremented by mana cost of card
 */
public class PlayingPhase extends Phase {
    final Link<Card> link;

    PlayingPhase(Link<Card> card, boolean outermost) {
        super(outermost);
        link = card;
    }

    void occur(Game game) {
        def card = link[game]
        if (!(link in game.zones[HAND])) {
            System.out.printf("Playing Phase of %s aborted because it is not in hand zone\n", card)
            pendingResolution = true
            return
        }
        super.occur(game)
        card.player[game].mana -= card[MANA]
        if (card instanceof Minion) {
            Utils.moveEntity(game, HAND, PLAY, link)
            //game.triggers.addAll(((Minion) card).getPlayTriggers());//TODO: Change triggers (add different types of triggers)
        } else if (card instanceof Spell)
            Utils.moveEntity(game, HAND, GRAVEYARD, link)
    }
}
