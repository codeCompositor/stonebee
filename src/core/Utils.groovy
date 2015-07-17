package core

import core.card.ZoneType

final class Utils {
    /**
     * Moves specified card from the initial to the resulting zone.
     * If the resulting zone is full, the card will get marked 'pending destroy'.
     *
     * @param initial   zone from which the card will be moved
     * @param resulting zone to which the card will be moved
     * @param card      card which will be moved
     * @return <tt>true</tt> if initial zone contains specified card
     */
    static moveCard(game, initial, resulting, cardLink) {
        //return getZone(initial, game).remove(cardLink) && getZone(resulting, game).add(cardLink);
        getZone(initial, cardLink.getFrom(game).getPlayer(game)).remove(cardLink, game) &&
                getZone(resulting, cardLink.getFrom(game).getPlayer(game)).add(cardLink, game);
    }

    static getZone(zone, owner) {
        switch (zone) {
            case ZoneType.PLAY:
                return owner.play
            case ZoneType.DECK:
                return owner.deck
            case ZoneType.HAND:
                return owner.hand
            case ZoneType.GRAVEYARD:
                return owner.graveyard
            default:
                System.out.println("Error: wrong ZoneType")
        }
    }

    static getPlayersZone(zone, player) {
        switch (zone) {
            case ZoneType.PLAY:
                return player.play;
            case ZoneType.DECK:
                return player.deck;
            case ZoneType.HAND:
                return player.hand;
            case ZoneType.GRAVEYARD:
                return player.graveyard;
            default:
                System.out.println("Error: wrong ZoneType");
        }
    }
}
