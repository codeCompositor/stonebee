package core

import core.buff.Buff
import core.card.ZoneType
import core.phase.TriggeredPhase

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    List<Buff> buffs
    Link link
    Link<Player> player
    List<Link<TriggeredPhase>> triggers = []
    Map<ZoneType, List<TriggeredPhase>> zoneTriggers = [:]
    Map<TagType, Object> tags = [:]

    def setLink(Link link) {
        zoneTriggers.values().each { it.each { it.setOwner(link) } }
        this.@link = link
    }

    def getAt(TagType tag) {
        tags[tag]
    }

    def putAt(TagType tag, value) {
        tags[tag] = value
    }

    abstract void updateStats()
}
