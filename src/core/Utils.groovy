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
        Player player = entity.getFrom(game).getPlayer(game)
        player.zones[initial].remove(entity, game)
        player.zones[resulting].add(entity, game)
    }
}
