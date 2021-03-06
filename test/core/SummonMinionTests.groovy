package core

import core.card.creature.Creature
import core.card.creature.Minion
import core.cardbase.minions.*
import core.phase.TargetableBattlecryPhase

import static core.TagType.*
import static core.card.ZoneType.HAND
import static core.card.ZoneType.PLAY

class SummonMinionTests extends GroovyTestCase {
    Player player
    Player opponent
    Game game

    void setUp() {
        game = new Game(player = new Player(), opponent = new Player())
    }

    void testManaChange() {
        def yeti = new ChillwindYeti()
        player.zones[HAND].add(new Link<>(yeti, game), game)
        player.mana = 4
        game.playMinion(yeti)
        game.run()
        assertEquals("Verify Player has 0 mana", 0, player.mana)
    }

    void testZoneChange() {
        def yeti = new Link<Minion>(new ChillwindYeti(), game)
        player.zones[HAND].add(yeti, game)
        yeti[game][MANA] = 0
        game.playMinion(yeti)
        game.run()
        assertTrue("Verify Yeti is not in hand zone", !player.zones[HAND].contains(yeti))
        assertTrue("Verify Yeti is in play zone", player.zones[PLAY].contains(yeti))
    }

    void testEarlyOnSummonTrigger() {
        def murloc = new Minion(1, 1, 0, "Murloc"), tidecaller = new MurlocTidecaller()
        murloc[RACE] = Creature.Race.MURLOC
        player.zones[HAND].add(new Link<>(tidecaller, game), game)
        player.zones[HAND].add(new Link<>(murloc, game), game)
        game.playMinion(tidecaller)
        game.run()
        game.playMinion(murloc)
        game.run()
        assertEquals("Verify Tidecaller has 2 attack", 2, tidecaller[ATTACK])
    }

    void testElvenArcher() {
        game.targetChooser = { n, game -> game.currentTarget = 2 }
        def archer = new Link<Minion>(new ElvenArcher(), game)
        def yetiLink = new Link<Minion>(new ChillwindYeti(), game)
        player.zones[PLAY].add(yetiLink, game)
        player.zones[HAND].add(archer, game)
        assertTrue("Verify Archer can target Yeti", ((TargetableBattlecryPhase) archer[game].battlecry).getValidTargets(game).contains(yetiLink))
        game.playMinion(archer)
        game.run()
        assertEquals("Verify Yeti has 4 health", 4, yetiLink[game][HEALTH])
    }

    void testShatteredSunCleric() {
        game.targetChooser = { n, game -> game.currentTarget = 0 }
        def cleric = new Link<Minion>(new ShatteredSunCleric(), game)
        def yetiLink = new Link<Minion>(new ChillwindYeti(), game)
        player.zones[PLAY].add(yetiLink, game)
        player.zones[HAND].add(cleric, game)
        assertTrue("Verify Sun Cleric can target Yeti", yetiLink in cleric[game].battlecry.getValidTargets(game))
        game.playMinion(cleric)
        game.run()
        yetiLink[game].updateStats()
        assertEquals("Verify Yeti has 5 attack", 5, yetiLink[game][ATTACK])
        assertEquals("Verify Yeti has 6 health", 6, yetiLink[game][HEALTH])
        yetiLink[game].with {
            buffs.clear()
            updateStats()
        }
        assertEquals("Verify Yeti has 4 attack", 4, yetiLink[game][ATTACK])
        assertEquals("Verify Yeti has 5 health", 5, yetiLink[game][HEALTH])
    }

    void testAbusiveSergeant() {
        def sergeantLink = new Link<Minion>(new AbusiveSergeant(), game)
        def yetiLink = new Link<Minion>(new ChillwindYeti(), game)

        player.zones[PLAY].add(yetiLink, game)
        player.zones[HAND].add(sergeantLink, game)
        game.targetChooser = { n, game -> game.currentTarget = 0 }

        assertTrue("Verify Sergeant can target Yeti", yetiLink in sergeantLink[game].battlecry.getValidTargets(game))

        game.playMinion(sergeantLink)
        game.run()
        assertEquals("Verify Yeti has 6 attack", 6, yetiLink[game][ATTACK])

        game.endTurn()
        game.run()
        assertEquals("Verify Yeti has 4 attack", 4, yetiLink[game][ATTACK])
    }
}
