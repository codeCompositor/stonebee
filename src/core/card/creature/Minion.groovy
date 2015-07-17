package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Card
import core.phase.BattlecryPhase
import core.phase.PhaseTrigger

class Minion implements Card, Creature {
    String name
    Link link
    Link<Player> player
    boolean pendingDestroy
    int mana
    int attack
    int health
    int nativeAttack
    int nativeHealth
    int maxHealth
    Creature.Race race
    boolean canBeTargeted
    List<Buff> buffs
    Map tags
    protected BattlecryPhase battlecry
    private List<Integer> triggers
    /**
     * Triggers which are added to main trigger list whenever minion is played
     */
    private List<PhaseTrigger> playTriggers

    public Minion() {
    }

    public Minion(int attack, int health, int mana, String name) {
        this.mana = mana
        this.name = name
        this.attack = nativeAttack = attack
        this.health = maxHealth = nativeHealth = health
        triggers = new ArrayList<Integer>()
        playTriggers = new ArrayList<PhaseTrigger>()
        buffs = new ArrayList<Buff>()
    }

    public BattlecryPhase getBattlecry() {
        return battlecry;
    }

    public void setBattlecry(BattlecryPhase battlecry) {
        if (battlecry != null) battlecry.minion = link;
        this.battlecry = battlecry;
    }

    Minion copy() { // TODO: Complete this
//        def m = new Minion()
//        m.copy(this)
//        m.battlecry = battlecry.copy()
        //TODO: copy triggers
        def m = new Minion();
        m.name = name
        m.link = link.copy()
        m.player = player.copy()
        m.pendingDestroy = pendingDestroy
        m.mana = mana
        m.attack = attack
        m.health = health
        m.nativeAttack = nativeAttack
        m.nativeHealth = nativeHealth
        m.maxHealth = maxHealth
        m.race = race
        m.canBeTargeted = canBeTargeted
        m.buffs = buffs*.copy()
        m.battlecry = battlecry == null ? null : battlecry.copy()
        //TODO: copy triggers
        return m
    }

    @Override
    public String toString() {
        "Minion{\"$name\",$attack/$health}"
    }

    public boolean hasBattlecry() {
        return battlecry != null;
    }

    public void setLink(Link link) {
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
