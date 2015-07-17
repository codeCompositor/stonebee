package core;

import core.card.creature.Creature;

import java.util.List;

public interface TargetChooser {
//    private final Game game;
//
//    public TargetChooser(Game game) {
//        this.game = game;
//    }
//
//    public void chooseTarget(int n) {
//        for (int i = 0; i < n; ++i) {
//            //TODO: Clone game and set different targets
//            Game game = this.game;//TODO:Change to ".copy()";
//            game.currentTarget = i;
//            game.run();
//        }
//    }
    public void chooseTarget(int n, Game game);
}
