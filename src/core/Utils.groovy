package core

import core.card.ZoneType

final class Utils {
    /**
     * Moves specified card from the initial to the resulting zone.
     * If the resulting zone is full, the card will get marked 'pending destroy'.
     *
     * @param initial zone from which the card will be moved
     * @param resulting zone to which the card will be moved
     * @param entity entity which will be moved
     * @return <tt>true</tt> if initial zone contains specified card
     */
    static moveEntity(Game game, ZoneType initial, ZoneType resulting, Link<Entity> entity) {
        getZone(initial, entity.getFrom(game).getPlayer(game)).remove(entity, game)
        getZone(resulting, entity.getFrom(game).getPlayer(game)).add(entity, game)
    }

    static getZone(ZoneType zone, owner) {
        owner[zone.toString().toLowerCase()]
    }
}
