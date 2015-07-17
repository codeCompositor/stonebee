package core;

public interface Copyable<E extends Copyable> {
    E copy();
}
