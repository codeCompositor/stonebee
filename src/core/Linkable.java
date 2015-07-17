package core;

public interface Linkable<E extends Copyable> {
    void setLink(Link<E> link);
}
