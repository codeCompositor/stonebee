package core.buff

import core.Copyable
import core.Entity

class Buff implements Copyable {
    void apply(Entity entity) {}

    Buff plus(Buff buff2) {
        def buff1 = this
        new Buff() {
            void apply(Entity entity) {
                buff1.apply(entity)
                buff2.apply(entity)
            }
        }
    }

    Buff multiply(int number) {
        def buff = this
        new Buff() {
            void apply(Entity entity) {
                number.times { buff.apply(entity) }
            }
        }
    }

    Buff copy() { new Buff() }
}
