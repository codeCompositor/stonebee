package core.card.creature

import core.Copyable
import core.Entity
import core.Game
import core.Link
import core.buff.Buff
import core.phase.DamagePhase

trait Creature extends Entity implements Copyable {
    abstract List<Buff> getBuffs();

    abstract void setBuffs(List<Buff> buffs);

    boolean isDead() {
        return mortallyWounded || this['pendingDestroy'];
    }

    boolean isMortallyWounded() { this['health'] <= 0 }

    /**
     * Whenever creature attacks this minion it deals damage to him
     * @param attacker a creature that attacks this minion
     */
    def defend(Link<? extends Creature> attacker, Game game) {
        game.addPhase(new DamagePhase(this['attack'], attacker, link))
    }

    /**
     * Whenever this minion attacks creature he deals damage to it
     * @param defender a creature that is attacked by this minion
     */
    def attack(Link<? extends Creature> defender, Game game) {
        game.addPhase(new DamagePhase(this['attack'], defender, link))
    }

    def takeDamage(int damage) { this['health'] -= damage }

    void updateStats() {
        def oldHealth = this['maxHealth']
        this['maxHealth'] = this['nativeHealth']
        this['attack'] = this['nativeAttack']
        this['mana'] = this['nativeMana']
        buffs.each { it.apply(this) }
        if (this['maxHealth'] > oldHealth)
            this['health'] += this['maxHealth'] - oldHealth
        else if (this['maxHealth'] < this['health'])
            this['health'] = this['maxHealth']
    }

    static enum Race {
        BEAST, MURLOC, MECH, TOTEM, DEMON, PIRATE, DRAGON
    }
}
