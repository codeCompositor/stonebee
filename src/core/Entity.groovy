package core

import core.card.ZoneType
import core.phase.TriggeredPhase

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    Link link
    Link<Player> player
    List<Link<TriggeredPhase>> triggers = []
    Map<ZoneType, List<TriggeredPhase>> zoneTriggers = [:]
    Map<TagType, Object> tags = [:]

    def getAt(TagType tag) {
        tags[tag]
    }

    def putAt(TagType tag, value) {
        tags[tag] = value
    }
}
