package core

import core.card.ZoneType

import static core.card.ZoneType.*

final class Utils {
    /**
     * Moves specified card from the initial to the resulting zone.
     * If the resulting zone is full, the card will get marked 'pending destroy'.
     *
     * @param initial   zone from which the card will be moved
     * @param resulting zone to which the card will be moved
     * @param entity entity which will be moved
     * @return <tt>true</tt> if initial zone contains specified card
     */
    static moveEntity(Game game, ZoneType initial, ZoneType resulting, Link entity) {
        getZone(initial, entity.getFrom(game).getPlayer(game)).remove(entity, game) &&
                getZone(resulting, entity.getFrom(game).getPlayer(game)).add(entity, game);
    }

    static getZone(ZoneType zone, owner) {
        owner.(zone.toString().toLowerCase())
        owner.("$zone".toLowerCase())
        switch (zone) {
            case PLAY:
                return owner.play
            case DECK:
                return owner.deck
            case HAND:
                return owner.hand
            case GRAVEYARD:
                return owner.graveyard
            default:
                System.out.println("Error: wrong ZoneType")
        }
    }

    static getPlayersZone(zone, player) {
        switch (zone) {
            case PLAY:
                return player.play;
            case DECK:
                return player.deck;
            case HAND:
                return player.hand;
            case GRAVEYARD:
                return player.graveyard;
            default:
                System.out.println("Error: wrong ZoneType");
        }
    }
}
