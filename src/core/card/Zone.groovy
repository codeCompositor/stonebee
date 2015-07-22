package core.card

import core.*

class Zone<E extends Entity> extends LinkedList<Link<E>> implements PlayerOwnable, Copyable<Zone> {
    /**
     * Maximal numbers of cards in zone
     */
    private int maxSize
    /**
     * Owner of this zone
     */
    Link<Player> player
    ZoneType zoneType

    Zone(int maxSize = -1, Link<Player> player = null, ZoneType zoneType) {
        this.player = player
        this.maxSize = maxSize
        this.zoneType = zoneType
    }

    /**
     * Appends the specified element to the end of this zone.
     * @param o element to be appended to this zone
     * @return <tt>true</tt> if zone isn't full
     */
    boolean add(Link<E> o, int index = size()) {
        if (maxSize < 0 || size() < maxSize) {
            super.add(index, o)
            return true
        }
        false
    }

    boolean add(Link<E> o, Game game, int index = size()) {
        if (add(o, index)) {
            def e = o.getFrom(game)
            if (e instanceof Entity) {
                e.player = player
                e.zoneTriggers[zoneType].each {
                    it.entity = o
                    Link link = new Link(it, game)
                    e.triggers.add(link)
                    game.triggers.add(link)
                }
            }
            return game.zones[zoneType].add(o)
        }
        false
    }

    boolean add(E e, Game game, int index = size()) {
        add(new Link(e, game), game, index)
    }

    boolean remove(Link<E> o) {
        super.remove(o)
    }

    boolean remove(Link<E> o, Game game) {
        if (remove(o)) {
            def e = o.getFrom(game)
            if (e instanceof Entity) {
                game.triggers.removeAll(e.triggers)
                e.triggers.clear()
            }
            return Utils.getZone(zoneType, game).remove(o)
        }
        false
    }

    boolean remove(E e, Game game) {
        remove(new Link(e, game), game)
    }

    Zone<E> copy() {
        Zone<E> z = new Zone(maxSize, player.copy(), zoneType)
        z.addAll(this)
        z
    }
}
