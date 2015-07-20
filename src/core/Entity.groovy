package core

import core.trigger.Trigger

trait Entity implements PlayerOwnable, Copyable, Linkable, TriggerOwner {
    Link link
    Link<Player> player
    List<Link<Trigger>> triggers = []
    List<Trigger> playTriggers = []
    List<Trigger> handTriggers = []
    List<Trigger> deckTriggers = []
    List<Trigger> graveyardTriggers = []
    Map tags = [:]

    def getAt(String property) {
        tags[property]
    }

    def putAt(String property, value) {
        tags[property] = value
    }
}
