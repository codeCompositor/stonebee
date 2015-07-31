package core.phase

import core.Game
import core.Link
import core.Targetable

import static core.card.ZoneType.PLAY

class TargetableBattlecryPhase extends BattlecryPhase implements Targetable {
    TargetableBattlecryPhase(Link minion = null, Link target = null) {
        super(minion)
        this.target = target
    }

    void occur(Game game) {
        super.occur(game)
        if (game.currentTarget < 0)
            game.targetChooser.call(getValidTargets(game).size(), game)
        target = getValidTargets(game)[game.currentTarget]

        game.resetCurrentTarget()
    }

    List<Link> getValidTargets(Game game) {
        game.zones[PLAY]
    }
}
