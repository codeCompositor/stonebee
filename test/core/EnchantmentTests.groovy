package core

import core.cardbase.minions.ChillwindYeti
import core.cardbase.spells.Equality

import static core.card.ZoneType.HAND
import static core.card.ZoneType.PLAY

class EnchantmentTests extends GroovyTestCase {
    Player player
    Player opponent
    Game game

    void setUp() {
        game = new Game(player = new Player(), opponent = new Player())
    }

    void testEquality() {
        def yeti = new ChillwindYeti()
        def equality = new Equality()
        def yetiLink = new Link(yeti, game)
        def equalityLink = new Link(equality, game)
        player.zones[PLAY].add(yetiLink, game)
        player.zones[HAND].add(equalityLink, game)
        game.playSpell(equality)
        game.run()
        assertEquals("Verify Yeti has 1 hp", 1, yeti['health'])
        assertEquals("Verify player's hero has 30 hp", 30, player.getHero(game)['health'])
        //TODO: Add silence
        yeti.getBuffs().clear()
        yeti.updateStats()
        assertEquals("Verify Yeti has 5 hp", 5, yeti['health'])
    }
}
