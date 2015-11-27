package core.card

import core.Copyable
import core.Entity

import static core.TagType.MANA
import static core.TagType.NATIVE_MANA

trait Card extends Entity implements Copyable {
    static enum Class {
        NEUTRAL(""), MAGE("core.cardbase.heroes.JainaProudmoore"), WARRIOR("core.cardbase.heroes.GarroshHellscream");

        /**
         * Contains NAME of class witch implements given hero class
         */
        final String className;

        Class(String className) { this.className = className }
    }

    void updateStats() {
        tags[MANA] = tags[NATIVE_MANA]

        buffs.each { it.apply(this) }
    }
}
