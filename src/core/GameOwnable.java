package core;

/**
 * Represents entity that can be owned by game
 */
public interface GameOwnable {
    Game getGame();

    void setGame(Game game);
}