package core

trait Entity implements PlayerOwnable, Copyable, Linkable {
    Link link
    Link<Player> player
    Map tags = [:]

    def getAt(String property) {
        tags[property]
    }

    def putAt(String property, value) {
        tags[property] = value
    }
}
