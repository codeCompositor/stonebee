package core.cardbase.minions;

import core.Game;
import core.Link;
import core.card.creature.Creature;
import core.card.creature.Minion;
import core.buff.AttackBuff;
import core.phase.Phase;
import core.phase.PhaseTrigger;
import core.phase.EarlyOnSummonPhase;

public class MurlocTidecaller extends Minion {
    private MTPhaseTrigger trigger;

    public MurlocTidecaller() {
        super(1, 2, 1, "Murloc Tidecaller");
        trigger = new MTPhaseTrigger();
        //getPlayTriggers().add(trigger);
    }

    @Override
    public void setLink(Link link) {
        super.setLink(link);
        trigger.setCreature(link);
    }

    public class MTPhaseTrigger extends PhaseTrigger {
        private Link<Creature> creature;

        @Override
        boolean trigger(Phase phase, Game game) {
            phase instanceof EarlyOnSummonPhase && phase.minion.getFrom(game).race == Creature.Race.MURLOC;
        }

        @Override
        public void occur(Game game) {
            super.occur(game);
            creature.getFrom(game).getBuffs().add(new AttackBuff({x -> x + 1})); //TODO: Add buff here
        }

        public Link<Creature> getCreature() {
            return creature;
        }

        public void setCreature(Link<Creature> creature) {
            this.creature = creature;
        }
    }
}
