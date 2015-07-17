package core;

import core.card.Card;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.cardbase.minions.ChillwindYeti
import core.cardbase.spells.Equality;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

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
        assertEquals("Verify Yeti has 1 hp", 1, yeti.getHealth());
        assertEquals("Verify player's hero has 30 hp", 30, player.getHero(game).getHealth());
        //TODO: Add silence
        yeti.getBuffs().clear();
        yeti.updateStats();
        assertEquals("Verify Yeti has 5 hp", 5, yeti.getHealth());
    }
}
