package core

public final class Link<E extends Copyable> implements Copyable { //TODO: Think about this
    private Integer index;

    Link(E e, List<E> list) {
        if ((index = list.indexOf(e)) < 0) {
            index = list.size();
            list.add(e);
        }
    }

    Link(E o, Game game) {
        if ((index = game.allObjects.indexOf(o)) < 0) {
            index = game.allObjects.size()
            game.allObjects.add(o)
            if (o instanceof Entity)
                o.setLink(this)
        }
    }

    Link(Integer index) {
        this.index = index;
    }

    Integer getIndex() {
        return index;
    }

    /**
     * Returns object contained in allObjects list that has given index
     *
     * @return object with index contained in this Link
     */
    E getFrom(Game game) {
        (E) game.allObjects.get(index);
    }

    /**
     * Returns object contained in list that has given index
     *
     * @return object with index contained in this Link
     */
    E getFrom(List<E> list) {
        list.get(index)
    }

    E getAt(holder) {
        getFrom(holder)
    }

    Link<E> copy() {
        new Link(index)
    }

    boolean equals(Object o) {
        o instanceof Link && Objects.equals(this.index, ((Link) o).index)
    }

    String toString() {
        "Link{index=$index}"
    }
}
