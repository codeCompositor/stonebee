package core

import core.cardbase.minions.BloodfenRaptor
import core.cardbase.minions.ChillwindYeti

import static core.ComparisonType.L
import static core.TagType.ATTACK
import static core.card.ZoneType.PLAY

public class SelectorTests extends GroovyTestCase {
    private Player player
    private Player opponent
    private Game game

    void setUp() {
        game = new Game(player = new Player(), opponent = new Player())
    }

    void testSelector() {
        def yeti = new Link(new ChillwindYeti(), game)
        def raptor = new Link(new BloodfenRaptor(), game)
        player.zones[PLAY].add(yeti, game)
        player.zones[PLAY].add(raptor, game)
        def selector = ~new Selector([ATTACK, L, 4])// & new Selector(ENEMY, PLAY, MINION)
        assert [yeti] as Set == selector.eval(game, yeti)
    }
}