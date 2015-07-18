package core;

public interface Linkable<E extends Copyable> {
    Link<E> getLink();

    void setLink(Link<E> link);
}
