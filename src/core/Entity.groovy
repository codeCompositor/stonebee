package core

import core.phase.PhaseTrigger

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    Link link
    Link<Player> player
    List<Link<PhaseTrigger>> triggers = []
    List<PhaseTrigger> playTriggers = []
    List<PhaseTrigger> handTriggers = []
    List<PhaseTrigger> deckTriggers = []
    List<PhaseTrigger> graveyardTriggers = []
    Map tags = [:]

    def getAt(String property) {
        tags[property]
    }

    def putAt(String property, value) {
        tags[property] = value
    }
}
