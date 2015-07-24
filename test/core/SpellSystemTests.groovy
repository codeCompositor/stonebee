package core

import core.cardbase.minions.BloodfenRaptor
import core.cardbase.minions.ChillwindYeti
import core.cardbase.spells.Flamestrike

import static TagType.HEALTH
import static core.card.ZoneType.*

public class SpellSystemTests extends GroovyTestCase {
    private Player player
    private Player opponent
    private Game game

    public void setUp() {
        game = new Game(player = new Player(), opponent = new Player())
    }

    public void testFlamestrike() {
        def yeti = new ChillwindYeti()
        def raptor = new BloodfenRaptor()
        def flamestrike = new Flamestrike()
        def yetiLink = new Link(yeti, game)
        def raptorLink = new Link(raptor, game)
        def flamestrikeLink = new Link(flamestrike, game)

        player.zones[PLAY].add(yetiLink, game)
        player.zones[PLAY].add(raptorLink, game)
        opponent.zones[HAND].add(flamestrikeLink, game)

        // 4/5 -> 4/1
        // 3/2 -> 3/-2
        game.playSpell(flamestrike)
        game.run()

        assertEquals("Verify Yeti has 1 health", 1, yeti[HEALTH])
        assertEquals("Verify Raptor has -2 health", -2, raptor[HEALTH])
        assertTrue("Verify Raptor is in graveyard zone", raptorLink in game.zones[GRAVEYARD])
        assertTrue("Verify opponent's graveyard contains flamestrike", flamestrikeLink in game.zones[GRAVEYARD])

        def gameCopy = game.copy()
        def playerCopy = gameCopy.getPlayer()
        def opponentCopy = gameCopy.getOpponent()
        def yetiCopy = yetiLink.getFrom(gameCopy)
        def raptorCopy = raptorLink.getFrom(gameCopy)
        def flamestrike2 = new Flamestrike()
        def flamestrike2Link = new Link(flamestrike2, gameCopy)
        opponentCopy.zones[HAND].add(flamestrike2Link, gameCopy)

        gameCopy.playSpell(flamestrike2)
        gameCopy.run()

        assertEquals("Verify Yeti has -3 health", -3, yetiCopy[HEALTH])
        assertEquals("Verify Raptor has -2 health", -2, raptorCopy[HEALTH])
        assertTrue("Verify Yeti is in graveyard zone", yetiLink in playerCopy.zones[GRAVEYARD])
        assertTrue("Verify Raptor is in graveyard zone", raptorLink in playerCopy.zones[GRAVEYARD])
        assertTrue("Verify graveyard contains flamestrike", flamestrikeLink in opponentCopy.zones[GRAVEYARD])
        assertTrue("Verify graveyard contains flamestrike2", flamestrike2Link in opponentCopy.zones[GRAVEYARD])
    }
}
