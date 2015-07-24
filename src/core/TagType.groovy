package core

import org.testng.internal.collections.Pair

enum TagType {
    ZONE, SIDE, TYPE,
    NAME,
    MANA, NATIVE_MANA,
    ATTACK, NATIVE_ATTACK,
    HEALTH, MAX_HEALTH, NATIVE_HEALTH,
    PENDING_DESTROY,
    ARMOR,
    RACE,
    DURABILITY

    Pair<TagType, Object> minus(object) { new Pair(this, object) }
}