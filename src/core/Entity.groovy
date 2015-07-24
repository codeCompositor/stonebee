package core

import core.card.ZoneType
import core.phase.TriggeredPhase

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    Link link
    Link<Player> player
    List<Link<TriggeredPhase>> triggers = []
    Map<ZoneType, List<TriggeredPhase>> zoneTriggers = [:]
    Map<Tags, Object> tags = [:]

    def getAt(Tags tag) {
        tags[tag]
    }

    def putAt(Tags tag, value) {
        tags[tag] = value
    }
}
