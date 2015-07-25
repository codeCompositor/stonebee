package core;

/**
 * Represents entity that can be owned by player
 */
public interface PlayerOwnable {
    Link<Player> getPlayer();

    void setPlayer(Link<Player> player);
}
