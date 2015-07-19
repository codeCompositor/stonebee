package core.phase

import core.Game
import core.Link
import core.card.creature.Creature

public class PreparationPhase extends Phase {
    private Link<Creature> attacker;
    private Link<Creature> defender;

    public PreparationPhase(Link<Creature> attacker, Link<Creature> defender, boolean game) {
        super(game);
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void occur(Game game) {
        if (!checkHighPriorityTriggers(game) && !checkLowPriorityTriggers(game))
            setPendingResolution(true);
    }

    private boolean checkHighPriorityTriggers(Game game) {
        List<Phase> greedyQueue = new ArrayList<>();
        boolean result = false;
        for (PhaseTrigger trigger : game.triggers*.getFrom(game)) {
            if (trigger.getPriority() > 0 && trigger.trigger(this, game)) {
                greedyQueue.add(trigger);
                result = true;
            }
        }
        for (Phase phase : greedyQueue) {
            game.addPhase(phase);
        }
        return result;
    }

    private boolean checkLowPriorityTriggers(Game game) {
        List<Phase> greedyQueue = new ArrayList<>();
        boolean result = false;
        for (PhaseTrigger trigger : game.triggers*.getFrom(game)) {
            if (trigger.getPriority() == 0 && trigger.trigger(this, game)) {
                greedyQueue.add(trigger);
                result = true;
            }
        }
        for (Phase phase : greedyQueue) {
            game.addPhase(phase);
        }
        return result;
    }
}
