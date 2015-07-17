package core.buff;

import core.Copyable;
import core.Entity;

public interface Buff extends Copyable {
    boolean apply(Entity entity);

    Buff copy();
}
