package core.card.creature

import core.Link
import core.buff.Buff
import core.card.Card
import core.card.ZoneType
import core.phase.BattlecryPhase
import core.phase.Phase

import static core.EntityType.MINION
import static core.TagType.*

class Minion implements Card, Creature {
    protected Phase battlecry
    /**
     * Triggers which are added to main trigger list whenever minion is played
     */

    Minion() {}

    Minion(int attack, int health, int mana, String name) {
        this[TYPE] = MINION
        this[NAME] = name
        this[MANA] = this[NATIVE_MANA] = mana
        this[ATTACK] = this[NATIVE_ATTACK] = attack
        this[HEALTH] = this[MAX_HEALTH] = this[NATIVE_HEALTH] = health
        this[PENDING_DESTROY] = false
        ZoneType.values().each { zoneTriggers[it] = new LinkedList() }
        buffs = new ArrayList<Buff>()
    }

    Phase getBattlecry() {
        return battlecry
    }

    void setBattlecry(BattlecryPhase battlecry) {
        if (battlecry != null) battlecry.minion = link
        this.battlecry = battlecry
    }

    Minion copy() {
        def m = new Minion()
        m.link = link.copy()
        m.player = player.copy()
        m.tags.putAll(tags)
        m.buffs = buffs*.copy()
        zoneTriggers.each { m.zoneTriggers.put(it.key, it.value*.copy()) }
        m.battlecry = battlecry == null ? null : battlecry.copy()
        m
    }

    String toString() {
        "Minion{'${this[NAME]}',${this[ATTACK]}/${this[HEALTH]}}"
    }

    boolean hasBattlecry() {
        battlecry != null
    }

    def setLink(Link link) {
        core_Entity__link = link
        if (battlecry != null)
            battlecry.setOwner(link)
    }

    void updateStats() {
        def oldHealth = tags[MAX_HEALTH]

        tags[MAX_HEALTH] = tags[NATIVE_HEALTH]
        tags[ATTACK] = tags[NATIVE_ATTACK]
        tags[MANA] = tags[NATIVE_MANA]

        buffs.each { it.apply(this) }

        if (tags[MAX_HEALTH] > oldHealth)
            tags[HEALTH] += tags[MAX_HEALTH] - oldHealth
        else if (tags[MAX_HEALTH] < tags[HEALTH])
            tags[HEALTH] = tags[MAX_HEALTH]
    }
}
