package core.phase

import core.Game
import core.Utils
import core.card.ZoneType

/**
 * Process death, activate death-related triggers.
 */
class DeathPhase extends Phase {
    DeathPhase() {
        super(true)
    }

    void occur(Game game) {//TODO: Add deathrattle
        super.occur(game)
        def deadCreatures = game.play.findAll { it.getFrom(game).dead }
        deadCreatures.each {
            Utils.moveEntity(game, ZoneType.PLAY, ZoneType.GRAVEYARD, it)
            game.addPhase(new CreatureDeathPhase(it))
        }
    }

    void checkTriggers(Game game) {
        super.checkTriggers(game)
    }
}
