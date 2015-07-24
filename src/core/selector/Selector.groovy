package core.selector

import core.*
import core.card.ZoneType
import org.testng.internal.collections.Pair

import static TagType.SIDE
import static TagType.ZONE
import static core.Side.ENEMY
import static core.Side.FRIENDLY

class Selector {
    Pair<TagType, Object>[] program

    Selector(Pair<TagType, Object>... program) {
        this.program = program
    }

    Set<Link<Entity>> eval(Game game, Link<Player> player) {
        eval(game, player.getFrom(game))
    }

    Set<Link<Entity>> eval(Game game, Player player) {
        def result = []
        def sides = program.findAll { it.first() == SIDE }*.second()
        def holder = sides || FRIENDLY in sides && ENEMY in sides ? game : sides[0].getPlayer(game, player)
        def zones = program.findAll { it.first() == ZONE }*.second() ?: ZoneType.values()
        zones.each {
            result += holder.zones[it].findAll { entity ->
                for (tag in program)
                    if (entity.getFrom(game)[tag.first()] == tag.second())
                        return true
                false
            }
        }
        result as Set
    }

    Selector or(Selector s) {
        new Selector() {
            Set<Link<Entity>> eval(Game game, Player player) {
                this.eval(game, player)
            }
        }
    }

    Selector and(Selector selector2) {
        def selector1 = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Player player) {
                selector1.eval(game, player).intersect(selector2.eval(game, player))
            }
        }
    }

    Selector bitwiseNegate() {
        def selector = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Player player) {
                def result = [] as Set
                ZoneType.values().each { result += game.zones[it] }
                result - selector.eval(game, player)
            }
        }
    }
}