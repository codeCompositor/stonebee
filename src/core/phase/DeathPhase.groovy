package core.phase

import core.Game
import core.Utils

import static core.card.ZoneType.GRAVEYARD
import static core.card.ZoneType.PLAY

/**
 * Process death, activate death-related triggers.
 */
class DeathPhase extends Phase {
    void occur(Game game) {//TODO: Add deathrattle
        super.occur(game)
        def deadCreatures = game.zones[PLAY].findAll { it[game].dead }
        deadCreatures.each {
            Utils.moveEntity(game, PLAY, GRAVEYARD, it)
            game.addPhase(new CreatureDeathPhase(it))
        }
    }

    void checkTriggers(Game game) {
        super.checkTriggers(game)
    }
}
