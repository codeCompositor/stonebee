package core;

import core.card.Card;
import core.card.Spell;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.cardbase.minions.BloodfenRaptor;
import core.cardbase.minions.ChillwindYeti;
import core.cardbase.spells.Flamestrike;
import core.phase.DamagePhase;
import core.phase.SpellTextPhase;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpellSystemTests extends Assert {
    private Player player;
    private Player opponent;
    private Game game;

    @Before
    public void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    @Test
    public void flamestrike() {
        Minion yeti = new ChillwindYeti();
        Minion raptor = new BloodfenRaptor();
        Spell flamestrike = new Flamestrike();
        Link<Creature> yetiLink = new Link<>(yeti, game);
        Link<Creature> raptorLink = new Link<>(raptor, game);
        Link<Card> flamestrikeLink = new Link<>(flamestrike, game);

        player.play.add(yetiLink, game);
        player.play.add(raptorLink, game);
        opponent.hand.add(flamestrikeLink, game);

        // 4/5 -> 4/1
        // 3/2 -> 3/-2
        game.playSpell(flamestrike);
        game.run();

        assertEquals("Verify Yeti has 1 health", 1, yeti.getHealth());
        assertEquals("Verify Raptor has -2 health", -2, raptor.getHealth());
        assertTrue("Verify Raptor is in graveyard zone", game.graveyard.contains(raptorLink));
        assertTrue("Verify opponent's graveyard contains flamestrike", game.graveyard.contains(flamestrikeLink));

        Game gameCopy = game.copy();
        Player playerCopy = gameCopy.getPlayer();
        Player opponentCopy = gameCopy.getOpponent();
        Creature yetiCopy = yetiLink.getFrom(gameCopy);
        Creature raptorCopy = raptorLink.getFrom(gameCopy);
        Spell flamestrike2 = new Flamestrike();
        Link<Card> flamestrike2Link = new Link<>(flamestrike2, gameCopy);
        opponentCopy.hand.add(flamestrike2Link, gameCopy);

        gameCopy.playSpell(flamestrike2);
        gameCopy.run();

        assertEquals("Verify Yeti has -3 health", -3, yetiCopy.getHealth());
        assertEquals("Verify Raptor has -2 health", -2, raptorCopy.getHealth());
        assertTrue("Verify Yeti is in graveyard zone", gameCopy.graveyard.contains(yetiLink));
        assertTrue("Verify Raptor is in graveyard zone", gameCopy.graveyard.contains(raptorLink));
        assertTrue("Verify graveyard contains flamestrike", gameCopy.graveyard.contains(flamestrikeLink));
        assertTrue("Verify graveyard contains flamestrike2", gameCopy.graveyard.contains(flamestrike2Link));
    }
}
