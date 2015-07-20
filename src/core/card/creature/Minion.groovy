package core.card.creature

import core.Link
import core.Player
import core.buff.Buff
import core.card.Card
import core.phase.BattlecryPhase

class Minion implements Card, Creature {
    Link link
    Link<Player> player
    boolean pendingDestroy
    List<Buff> buffs
    protected BattlecryPhase battlecry
    /**
     * Triggers which are added to main trigger list whenever minion is played
     */

    Minion() {}

    Minion(int attack, int health, int mana, String name) {
        this['name'] = name
        this['mana'] = this['nativeMana'] = mana
        this['attack'] = this['nativeAttack'] = attack
        this['health'] = this['maxHealth'] = this['nativeHealth'] = health
        this['pendingDestroy'] = false;
        this['canBeTargeted'] = true;
        buffs = new ArrayList<Buff>()
    }

    BattlecryPhase getBattlecry() {
        return battlecry;
    }

    void setBattlecry(BattlecryPhase battlecry) {
        if (battlecry != null) battlecry.minion = link;
        this.battlecry = battlecry;
    }

    Minion copy() {
        def m = new Minion();
        m.link = link.copy()
        m.player = player.copy()
        m.tags.putAll(tags)
        m.buffs = buffs*.copy()
        m.playTriggers = playTriggers*.copy()
        m.deckTriggers = deckTriggers*.copy()
        m.handTriggers = handTriggers*.copy()
        m.graveyardTriggers = graveyardTriggers*.copy()
        m.battlecry = battlecry == null ? null : battlecry.copy()
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
            battlecry.minion = link;
    }
}
