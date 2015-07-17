package core;

/**
 * Represents entity that can be owned by player
 */
public interface PlayerOwnable {
    default Player getPlayer(Game game) {
        return getPlayer().getFrom(game);
    }

    Link<Player> getPlayer();

    void setPlayer(Link<Player> player);
}
