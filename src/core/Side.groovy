package core


enum Side {
    FRIENDLY, ENEMY

    Player getPlayer(Game game, Player friendlyPlayer) {
        switch (this) {
            case FRIENDLY:
                return friendlyPlayer
            case ENEMY:
                return game.oppositePlayer(friendlyPlayer)
        }
        null
    }
}
