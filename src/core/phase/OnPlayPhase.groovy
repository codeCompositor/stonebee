package core.phase

import core.Game
import core.Link
import core.card.Card

import static core.card.ZoneType.PLAY

/**
 * Minions:<br>
 * Hobgoblin, Questing Adventurer, Fel Reaver, Unbound Elemental and Illidan Stormrage Queue and resolve here.
 * <br>Spells:<br>
 * All triggers on playing a card/casting a spell/playing a Secret Queue and resolve here, such as Questing Adventurer,
 * Mana Wyrm, Burly Rockjaw Trogg, Secretkeeper and Violet Teacher. Counterspell also triggers here.
 * <br>This phase is only used to activate triggers.
 */
class OnPlayPhase extends Phase {
    final Link<? extends Card> card

    OnPlayPhase(Link<? extends Card> card) {
        this.card = card;
    }

    void occur(Game game) {
        if (!game.zones[PLAY].contains(card)) {
            System.out.printf("On Play Phase of %s aborted because he is not in play zone\n", card[game])
            pendingResolution = true
            return
        }
        super.occur(game)
    }
}
