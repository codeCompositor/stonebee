package core

import core.card.ZoneType
import core.cardbase.minions.ChillwindYeti

public class UtilsTests extends GroovyTestCase {
    private Player player;
    private Player opponent;
    private Game game;

    void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    void testBasicMinionsAttacking() {
        Link yeti = new Link(new ChillwindYeti(), game) // 4/5
        player.play.add(yeti, game);
        Utils.moveEntity(game, ZoneType.PLAY, ZoneType.DECK, yeti);
        assertTrue("Verify yeti is in deck zone", yeti in game.deck);
    }
}