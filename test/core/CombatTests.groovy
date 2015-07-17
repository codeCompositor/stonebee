package core;

import core.card.Card;
import core.card.Spell;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.cardbase.heroes.JainaProudmoore;
import core.cardbase.minions.BloodfenRaptor;
import core.cardbase.minions.ChillwindYeti;
import core.phase.DamagePhase;
import core.phase.Phase;
import core.phase.PhaseTrigger;
import core.phase.PreparationPhase;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class CombatTests extends Assert {
    private Player player;
    private Player opponent;
    private Game game;

    @Before
    public void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    @Test
    public void basicMinionsAttacking() {
        def yeti1 = new ChillwindYeti(); // 4/5
        def yeti2 = new ChillwindYeti(); // 4/5
        def raptor = new BloodfenRaptor(); // 3/2
        player.play.add(yeti1, game);
        player.play.add(raptor, game);
        opponent.play.add(yeti2, game);

        // 4/5 vs 4/5 -> 4/1 and 4/1
        game.combat(yeti1, yeti2);
        game.run();
        assertEquals("Verify Yeti_1 is at 1 health", 1, yeti1.getHealth());
        assertEquals("Verify Yeti_2 is at 1 health", 1, yeti2.getHealth());

        // 3/2 vs 4/1 -> 3/-2 and 4/-2
        game.combat(raptor, yeti2);
        game.run();
        assertEquals("Verify Yeti_2 is at -2 health", -2, yeti2.getHealth());
        assertEquals("Verify Raptor is at -2 health", -2, raptor.getHealth());
        assertTrue("Verify Yeti_1 is in play zone", game.play.contains(new Link(yeti1, game)));
        assertTrue("Verify Yeti_2 is in graveyard zone", game.graveyard.contains(new Link(yeti2, game)));
    }

//    @Test
//    public void misdirectionAndExplosive() {
//        Minion yeti = new ChillwindYeti();
//        player.play.add(yeti);
//        final Spell misdirectionSpell = new Spell(2, "Misdirection");
//        PhaseTrigger misdirection = new PhaseTrigger(1) {
//            boolean triggered = false;
//
//            @Override
//            public boolean trigger(Phase phase) {
//                return !triggered && phase instanceof PreparationPhase && ((PreparationPhase) phase).getDefender().equals(opponent.hero);
//            }
//
//            @Override
//            public void occur() {
//                triggered = true;
//                game.player.play.forEach(c -> getGame().add(new DamagePhase(2, c, misdirectionSpell)));
//                //Analogue
//                /*for (Creature c : game.player.play) {
//                    getGame().addPhase(new DamagePhase(2, c, misdirectionSpell));
//                }*/
//            }
//        };
//        game.allTriggers.add(misdirection);
//        game.triggers.add(game.allTriggers.size() - 1);
//
//        // 4/5 vs 4/5 -> 4/1 and 4/1
//        game.combat(yeti, opponent.hero);
//        game.run();
//        assertEquals("Verify Yeti is at 3 health", 3, yeti.getHealth());
//        assertEquals("Verify opponent's hero is at 26 health", 26, opponent.hero.getHealth());
//        assertEquals("Verify player's hero is at 28 health", 28, player.hero.getHealth());
//    }

    public void testGameResult() {
        Minion destroyer = new Minion(30, 1, 0, "DESTROYER");
        player.play.add(destroyer, game);
        game.combat(new Link(destroyer, game), opponent.hero);
        game.run();
        assertEquals("Verify player won", Game.GameResult.WIN, game.getResult());
    }
}
