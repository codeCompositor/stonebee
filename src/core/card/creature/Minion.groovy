package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Card
import core.phase.BattlecryPhase
import core.phase.PhaseTrigger

class Minion implements Card, Creature {
    Link link
    Link<Player> player
    boolean pendingDestroy
    List<Buff> buffs
    protected BattlecryPhase battlecry
    private List<Integer> triggers
    /**
     * Triggers which are added to main trigger list whenever minion is played
     */
    private List<PhaseTrigger> playTriggers

    Minion() {}

    Minion(int attack, int health, int mana, String name) {
        this['mana'] = mana
        this['name'] = name
        this['attack'] = this['nativeAttack'] = attack
        this['health'] = this['maxHealth'] = this['nativeHealth'] = health
        this['pendingDestroy'] = false;
        this['canBeTargeted'] = true;
        triggers = new ArrayList<Integer>()
        playTriggers = new ArrayList<PhaseTrigger>()
        buffs = new ArrayList<Buff>()
    }

    BattlecryPhase getBattlecry() {
        return battlecry;
    }

    void setBattlecry(BattlecryPhase battlecry) {
        if (battlecry != null) battlecry.minion = link;
        this.battlecry = battlecry;
    }

    Minion copy() { // TODO: Complete this
//        def m = new Minion()
//        m.copy(this)
//        m.battlecry = battlecry.copy()
        //TODO: copy triggers
        def m = new Minion();
        m.link = link.copy()
        m.player = player.copy()
        m.tags.putAll(tags)
        m.buffs = buffs*.copy()
        m.battlecry = battlecry == null ? null : battlecry.copy()
        //TODO: copy triggers
        return m
    }

    String toString() {
        "Minion{'${this['name']}',${this['attack']}/${this['health']}}"
    }

    boolean hasBattlecry() {
        return battlecry != null;
    }

    void setLink(Link link) {
        this.link = link;
        if (battlecry != null)
            battlecry.setMinion(link);
    }

//    @Override
//    public List<Integer> getTriggers() {
//        return triggers;
//    }
//
//    @Override
//    public void addTrigger(PhaseTrigger trigger, Game game) {
//        game.triggers.add(trigger);
//        triggers.add(game.allTriggers.size() - 1);
//    }
//
//    @Override
//    public void removeTrigger(PhaseTrigger trigger, Game game) {
//        triggers.remove(game.allTriggers.indexOf(trigger));
//        game.triggers.remove(trigger);
//    }
//
//    public List<PhaseTrigger> getPlayTriggers() {
//        return playTriggers;
//    }

}
