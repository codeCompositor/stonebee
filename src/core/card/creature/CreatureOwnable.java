package core.card.creature;

/**
 * Represents entity that can be owned by creature
 */
public interface CreatureOwnable {
    Creature getCreature();

    void setCreature(Creature creature);
}