package core

import core.cardbase.minions.ChillwindYeti
import core.cardbase.spells.Equality

public class EnchantmentTests extends GroovyTestCase {
    private Player player;
    private Player opponent;
    private Game game;

    public void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    public void testEquality() {
        def yeti = new ChillwindYeti();
        def equality = new Equality();
        def yetiLink = new Link(yeti, game);
        def equalityLink = new Link(equality, game);
        player.play.add(yetiLink, game);
        player.hand.add(equalityLink, game);
        game.playSpell(equality);
        game.run();
        assertEquals("Verify Yeti has 1 hp", 1, yeti['health']);
        assertEquals("Verify player's hero has 30 hp", 30, player.getHero(game)['health']);
        //TODO: Add silence
        yeti.getBuffs().clear();
        yeti.updateStats();
        assertEquals("Verify Yeti has 5 hp", 5, yeti['health']);
    }
}
