package core.card;

import core.*;

public class Zone<E extends Copyable> extends LinkedList<Link<E>> implements PlayerOwnable, Copyable<Zone> {
    /**
     * Maximal numbers of cards in zone
     */
    private int maxSize;
    /**
     * Owner of this zone
     */
    Link<Player> player;
    ZoneType zoneType;

    Zone(int maxSize = -1, Link<Player> player = null, ZoneType zoneType) {
        this.player = player;
        this.maxSize = maxSize;
        this.zoneType = zoneType;
    }

    /**
     * Appends the specified element to the end of this zone.
     * @param o element to be appended to this zone
     * @return <tt>true</tt> if zone isn't full
     */
    boolean add(Link<E> o) {
        return (maxSize < 0 || size() < maxSize) && super.add(o);
    }

    boolean add(Link<E> o, Game game) {
        if (add(o)) {
            if (o.getFrom(game) instanceof PlayerOwnable)
                o.getFrom(game).setPlayer(player);
            return Utils.getZone(zoneType, game).add(o);
        }
        return false;
    }

    boolean add(E e, Game game) {
        if (maxSize < 0 || size() < maxSize) {
            return add(new Link<E>(e, game), game);
        }
        return false;
    }

    boolean remove(Link<E> o) {
        return super.remove(o);
    }

    boolean remove(Link<E> o, Game game) {
        return remove(o) && Utils.getZone(zoneType, game).remove(o);
    }

    Link<Player> getPlayer() {
        return player;
    }

    void setPlayer(Link<Player> player) {
        this.player = player;
    }

    Zone<E> copy() {
        Zone<E> z = new Zone<>(maxSize, player.copy(), zoneType);
        z.addAll(this);
        return z;
    }
}
