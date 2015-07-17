package core.card.creature

import core.Copyable
import core.Entity
import core.Game
import core.Link
import core.buff.Buff
import core.phase.DamagePhase

trait Creature extends Entity implements Copyable {
    abstract int getAttack();

    abstract void setAttack(int attack);

    abstract int getHealth();

    abstract void setHealth(int health);

    abstract int getNativeAttack();

    abstract void setNativeAttack(int nativeAttack);

    abstract int getNativeHealth();

    abstract void setNativeHealth(int nativeHealth);

    abstract int getMaxHealth();

    abstract void setMaxHealth(int maxHealth);

    abstract Creature.Race getRace();

    abstract void setRace(Creature.Race race);

    boolean getCanBeTargeted() {
        isCanBeTargeted()
    }

    abstract boolean isCanBeTargeted();

    abstract void setCanBeTargeted(boolean canBeTargeted);

    abstract List<Buff> getBuffs();

    abstract void setBuffs(List<Buff> buffs);

    boolean isDead() {
        return isMortallyWounded() || isPendingDestroy();
    }

    boolean isMortallyWounded() { health <= 0 }

    /**
     * Whenever creature attacks this minion it deals damage to him
     * @param attacker a creature that attacks this minion
     */
    def defend(Link<? extends Creature> attacker, Game game) {
        game.addPhase(new DamagePhase(attack, attacker, link))
    }

    /**
     * Whenever this minion attacks creature he deals damage to it
     * @param defender a creature that is attacked by this minion
     */
    def attack(Link<? extends Creature> defender, Game game) {
        game.addPhase(new DamagePhase(attack, defender, link))
    }

    def takeDamage(int damage) { health -= damage }

    void updateStats() {
        int oldHealth = maxHealth;
        maxHealth = nativeHealth;
        attack = nativeAttack;
        for (buff in buffs)
            buff.apply(this);
        if (maxHealth > oldHealth) {
            health += maxHealth - oldHealth;
        } else if (maxHealth < health) {
            health = maxHealth;
        }
    }

    Creature copy(Creature c) {
        c.attack = attack;
        c.health = health;
        c.nativeAttack = nativeAttack;
        c.nativeHealth = nativeHealth;
        c.name = name;
        c.race = race;
        c.player = player;
        c.pendingDestroy = pendingDestroy;
        c.canBeTargeted = canBeTargeted;
        c.buffs = buffs*.copy();
        //TODO: copy triggers
        return c;
    }

    static enum Race {
        BEAST, MURLOC, MECH, TOTEM, DEMON, PIRATE, DRAGON
    }
}
