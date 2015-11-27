package core.card.creature

import core.Copyable
import core.Entity
import core.Game
import core.Link
import core.phase.DamagePhase

import static core.TagType.*

trait Creature extends Entity implements Copyable {
    boolean isDead() {
        mortallyWounded || tags[PENDING_DESTROY]
    }

    boolean isMortallyWounded() { tags[HEALTH] <= 0 }

    /**
     * Whenever creature attacks this minion it deals damage to him
     * @param attacker a creature that attacks this minion
     */
    void defend(Link<? extends Creature> attacker, Game game) {
        game.addPhase(new DamagePhase(tags[ATTACK], attacker, link))
    }

    /**
     * Whenever this minion attacks creature he deals damage to it
     * @param defender a creature that is attacked by this minion
     */
    void attack(Link<? extends Creature> defender, Game game) {
        game.addPhase(new DamagePhase(tags[ATTACK], defender, link))
    }

    void takeDamage(int damage) { this[HEALTH] -= damage }

    void silence() {
        buffs.clear()
    }

    void updateStats() {
        def oldHealth = tags[MAX_HEALTH]

        tags[MAX_HEALTH] = tags[NATIVE_HEALTH]
        tags[ATTACK] = tags[NATIVE_ATTACK]

        buffs.each { it.apply(this) }

        if (tags[MAX_HEALTH] > oldHealth)
            tags[HEALTH] += tags[MAX_HEALTH] - oldHealth
        else if (tags[MAX_HEALTH] < tags[HEALTH])
            tags[HEALTH] = tags[MAX_HEALTH]
    }

    static enum Race {
        BEAST, MURLOC, MECH, TOTEM, DEMON, PIRATE, DRAGON
    }
}
