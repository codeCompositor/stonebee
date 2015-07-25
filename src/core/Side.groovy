package core


enum Side {
    FRIENDLY, ENEMY, ALL_SIDES

    def getHolder(Game game, Player friendlyPlayer) {
        switch (this) {
            case FRIENDLY:
                return friendlyPlayer
            case ENEMY:
                return game.oppositePlayer(friendlyPlayer)
            case ALL_SIDES:
                return game
        }
        null
    }
}
