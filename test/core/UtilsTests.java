package core;

import core.card.ZoneType;
import core.card.creature.Creature;
import core.cardbase.minions.ChillwindYeti;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilsTests extends Assert {
    private Player player;
    private Player opponent;
    private Game game;

    @Before
    public void setUp() {
        game = new Game(player = new Player(), opponent = new Player());
    }

    @Test
    public void basicMinionsAttacking() {
        Link<Creature> yeti = new Link<>(new ChillwindYeti(), game); // 4/5
        player.play.add(yeti, game);
        Utils.moveCard(game, ZoneType.PLAY, ZoneType.DECK, yeti);
        assertTrue("Verify yeti is in deck zone", game.deck.contains(yeti));
    }
}