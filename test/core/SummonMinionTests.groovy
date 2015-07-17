package core;

import core.card.creature.Creature;
import core.card.creature.Minion
import core.cardbase.minions.AbusiveSergeant;
import core.cardbase.minions.ChillwindYeti
import core.cardbase.minions.ElvenArcher;
import core.cardbase.minions.MurlocTidecaller
import core.phase.TargetableBattlecryPhase;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

class SummonMinionTests extends Assert {
    Player player;
    Player opponent;
    Game game;

    @Before
    void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    @Test
    void manaChange() {
        def yeti = new ChillwindYeti();
        player.hand.add(new Link<>(yeti, game), game);
        player.mana = 4;
        game.playMinion(yeti);
        game.run();
        assertEquals("Verify Player has 0 mana", 0, player.mana);
    }

    @Test
    void zoneChange() {
        def yeti = new Link<Minion>(new ChillwindYeti(), game);
        player.hand.add(yeti, game);
        yeti.getFrom(game).setMana(0);
        game.playMinion(yeti);
        game.run();
        assertTrue("Verify Yeti is not in hand zone", !player.hand.contains(yeti));
        assertTrue("Verify Yeti is in play zone", player.play.contains(yeti));
    }

    @Test
    void earlyOnSummonTrigger() {
        def murloc = new Minion(1, 1, 0, "Murloc"), tidecaller = new MurlocTidecaller();
        murloc.setRace(Creature.Race.MURLOC);
        player.hand.add(new Link<>(tidecaller, game), game);
        player.hand.add(new Link<>(murloc, game), game);
        game.playMinion(tidecaller);
        game.run();
        game.playMinion(murloc);
        game.run();
        assertEquals("Verify Tidecaller has 2 attack", 2, tidecaller.getAttack());
    }

    @Test
    void battlecry() {
        game.targetChooser = {n, game -> game.currentTarget = 2};
        def archer = new Link<Minion>(new ElvenArcher(), game);
        def yetiLink = new Link<Minion>(new ChillwindYeti(), game);
        player.play.add(yetiLink, game);
        player.hand.add(archer, game);
        assertTrue("Verify Archer can target Yeti", ((TargetableBattlecryPhase) archer.getFrom(game).battlecry).getValidTargets(game).contains(yetiLink));
        game.playMinion(archer);
        game.run();
        assertEquals("Verify Yeti has 4 health", 4, yetiLink.getFrom(game).health);
    }

    @Test
    void abusiveSergeant() {
        def sergeantLink = new Link<Minion>(new AbusiveSergeant(), game);
        def yetiLink = new Link<Minion>(new ChillwindYeti(), game);

        player.play.add(yetiLink, game);
        player.hand.add(sergeantLink, game);
        game.targetChooser = {n, game -> game.currentTarget = 0};

        assertTrue("Verify Sergeant can target Yeti", sergeantLink.getFrom(game).getBattlecry().getValidTargets(game).contains(yetiLink));

        game.playMinion(sergeantLink);
        game.run();
        assertEquals("Verify Yeti has 6 attack", 6, yetiLink.getFrom(game).attack);

        game.endTurn();
        game.run();
        assertEquals("Verify Yeti has 4 attack", 4, yetiLink.getFrom(game).attack);
    }
}
