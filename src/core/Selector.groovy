package core

import core.card.ZoneType

import static core.EntityType.*
import static core.Side.*
import static core.TagType.TAUNT
import static core.card.ZoneType.HAND
import static core.card.ZoneType.PLAY

class Selector {
    final static def CREATURES = new Selector(CREATURE)
    final static def MINIONS = new Selector(MINION)
    final static def HEROES = new Selector(HERO)
    final static def HANDS = new Selector(HAND)
    final static def FRIENDLY_CREATURES = new Selector(FRIENDLY, CREATURE)
    final static def FRIENDLY_MINIONS = new Selector(FRIENDLY, MINION)
    final static def FRIENDLY_HERO = new Selector(FRIENDLY, HERO)
    final static def FRIENDLY_HAND = new Selector(FRIENDLY, HAND)
    final static def ENEMY_CREATURES = new Selector(ENEMY, CREATURE)
    final static def ENEMY_MINIONS = new Selector(ENEMY, MINION)
    final static def ENEMY_HERO = new Selector(ENEMY, HERO)
    final static def ENEMY_HAND = new Selector(ENEMY, HAND)
    final static def ATTACKABLE = new Selector() {
        Set<Link<Entity>> eval(Game game, Link<Entity> entity) {
            def res = ENEMY_CREATURES.eval(game, entity)
            def taunts = res.findAll { it[game][TAUNT] }
            return taunts ?: res
        }
    }
    final List program
    ZoneType zone
    Side side

//    Selector(Pair<TagType, Object>... program) {
//        this.program = program
//    }
    /*Selector(Object... program) {

    }*/

    Selector(Object... program) {
        this.program = program as List
        def zone = program.find { it instanceof ZoneType }
        def side = program.find { it instanceof Side }
        if (zone) {
            this.zone = zone
            this.program.remove(zone)
        } else this.zone = PLAY

        if (side) {
            this.side = side
            this.program.remove(side)
        } else this.side = ALL_SIDES
    }

    Set<Link<Entity>> eval(Game game, Link<Entity> entity) {
        def result = [] as Set
        def holder = side.getHolder(game, entity[game].player[game])
        result += holder.zones[zone].findAll {
            for (command in program) {
                if (command instanceof List) {
                    switch (command.size()) {
                        case 1:
                            if (!it[game].tags[command[0]])
                                return false
                            break
                        case 2:
                            if (!(it[game].tags[command[0]] == command[1]))
                                return false
                            break
                        case 3:
                            if (!command[1].compare(it[game][command[0]], command[2]))
                                return false
                    }
                } else if (!it[game].tags.containsValue(command))
                    return false
            }
            true
        }
        result
    }

    Selector or(Selector selector2) {
        def selector1 = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Link<Entity> entity) {
                selector1.eval(game, entity) + selector2.eval(game, entity)
            }
        }
    }

    Selector and(Selector selector2) {
        def selector1 = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Link<Entity> entity) {
                selector1.eval(game, entity).intersect(selector2.eval(game, entity))
            }
        }
    }

    Selector bitwiseNegate() {
        def selector = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Link<Entity> entity) {
                def result = [] as Set
                ZoneType.values().each { result += game.zones[it] }
                result - selector.eval(game, entity)
            }
        }
    }
}