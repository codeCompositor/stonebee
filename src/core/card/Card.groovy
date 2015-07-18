package core.card

import core.Copyable
import core.Entity

trait Card extends Entity implements Copyable {
    static enum Class {
        NEUTRAL(""), MAGE("core.cardbase.heroes.JainaProudmoore"), WARRIOR("core.cardbase.heroes.GarroshHellscream");

        /**
         * Contains name of class witch implements given hero class
         */
        final String className;

        Class(String className) { this.className = className }
    }
}
