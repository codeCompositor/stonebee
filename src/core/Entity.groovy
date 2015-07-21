package core

import core.phase.TriggeredPhase

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    Link link
    Link<Player> player
    List<Link<TriggeredPhase>> triggers = []
    List<TriggeredPhase> playTriggers = []
    List<TriggeredPhase> handTriggers = []
    List<TriggeredPhase> deckTriggers = []
    List<TriggeredPhase> graveyardTriggers = []
    Map tags = [:]

    def getAt(String property) {
        tags[property]
    }

    def putAt(String property, value) {
        tags[property] = value
    }
}
