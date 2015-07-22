package core.phase

import core.Game
import core.Link
import core.card.creature.Creature

public class PreparationPhase extends Phase {
    private Link<Creature> attacker
    private Link<Creature> defender

    public PreparationPhase(Link<Creature> attacker, Link<Creature> defender) {
        this.attacker = attacker
        this.defender = defender
    }

    void occur(Game game) {
        if (!checkHighPriorityTriggers(game) && !checkLowPriorityTriggers(game))
            setPendingResolution(true)
    }

    private boolean checkHighPriorityTriggers(Game game) {
        def greedyQueue = new ArrayList<Phase>()
        def result = false
        game.triggers*.getFrom(game).each {
            if (it.getPriority() > 0 && it.trigger(this, game)) {
                greedyQueue.add(it)
                result = true
            }
        }
        greedyQueue.each { game.addPhase(it) }
        result
    }

    private boolean checkLowPriorityTriggers(Game game) {
        def greedyQueue = new ArrayList<Phase>()
        def result = false
        game.triggers*.getFrom(game).each {
            if (it.getPriority() > 0 && it.trigger(this, game)) {
                greedyQueue.add(it)
                result = true
            }
        }
        greedyQueue.each { game.addPhase(it) }
        result
    }
}
