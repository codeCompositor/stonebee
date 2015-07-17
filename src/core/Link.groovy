package core;

import core.card.creature.Creature;

import java.util.List;
import java.util.Objects;

public final class Link<E extends Copyable> implements Copyable {
//public final class Link<E extends Copyable> implements Copyable { //TODO: Think about this

    private Integer index;

    public Link(E e, List<E> list) {
        if ((index = list.indexOf(e)) < 0) {
            index = list.size();
            list.add(e);
        }
    }

    public Link(E o, Game game) {
        if ((index = game.allObjects.indexOf(o)) < 0) {
            index = game.allObjects.size();
            game.allObjects.add(o);
            if (o instanceof Creature)
                ((Creature) o).setLink(this);
        }
    }

    public Link(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    /**
     * Returns object contained in allObjects list that has given index
     *
     * @return object with index contained in this Link
     */
    public E getFrom(Game game) {
        return (E) game.allObjects.get(index);
    }

    /**
     * Returns object contained in list that has given index
     *
     * @return object with index contained in this Link
     */
    public E getFrom(List<E> list) {
        return list.get(index);
    }

    @Override
    public Link<E> copy() {
        return new Link<>(index);
    }

    public boolean equals(Object o) {
        return o instanceof Link && Objects.equals(this.index, ((Link) o).index);
    }

    @Override
    public String toString() {
        return "Link{" +
                "index=" + index +
                '}';
    }
}
