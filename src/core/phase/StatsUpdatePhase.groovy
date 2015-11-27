package core.phase

import core.Game

import static core.card.ZoneType.HAND
import static core.card.ZoneType.PLAY


class StatsUpdatePhase extends Phase {
    void occur(Game game) {
        super.occur(game)
        game.zones[PLAY].each { it[game].updateStats() }
        game.zones[HAND].each { it[game].updateStats() }
    }
}
