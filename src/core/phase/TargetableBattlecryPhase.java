package core.phase;

import core.Game;
import core.Link;
import core.Targetable;
import core.card.creature.Creature;

import java.util.List;
import java.util.stream.Collectors;

public class TargetableBattlecryPhase extends BattlecryPhase implements Targetable {
    private Link<Creature> target;

    public TargetableBattlecryPhase() {
        this(null, null);
    }

    public TargetableBattlecryPhase(Link minion) {
        this(minion, null);
    }

    public TargetableBattlecryPhase(Link minion, Link target) {
        super(minion);
        this.target = target;
    }

    @Override
    public void occur(Game game) {
        if (game.currentTarget < 0)
            game.targetChooser.call(getValidTargets(game).size(), game);
        target = getValidTargets(game).get(game.currentTarget);

        game.resetCurrentTarget();
    }

    public Link<Creature> getTarget() {
        return target;
    }

    public void setTarget(Link<Creature> target) {
        this.target = target;
    }

    public List<Link<Creature>> getValidTargets(Game game) {
        return game.play;
        //return game.play.stream().filter(l -> l.getFrom(game).isCanBeTargeted()).collect(Collectors.toList());
    }

    @Override
    public void goOn() {
    }
}
