package core

import core.card.ZoneType

import static core.Side.ALL_SIDES
import static core.card.ZoneType.PLAY

class Selector {
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

    Set<Link<Entity>> eval(Game game, Link<Player> player) {
        eval(game, player[game])
    }

    Set<Link<Entity>> eval(Game game, Player player) {
        def result = [] as Set
        def holder = side.getHolder(game, player)
        result += holder.zones[zone].findAll { entity ->
            for (command in program) {
                if (command instanceof List) {
                    switch (command.size()) {
                        case 1:
                            if (!entity[game].tags[command[0]])
                                return false
                            break
                        case 2:
                            if (!(entity[game].tags[command[0]] == command[1]))
                                return false
                            break
                        case 3:
                            if (!command[1].compare(entity[game][command[0]], command[2]))
                                return false
                    }
                } else if (!entity[game].tags.containsValue(command))
                    return false
            }
            true
        }
        result
    }

    Selector or(Selector selector2) {
        def selector1 = this
        new Selector() {
            Set<Link<Entity>> eval(Game game, Player player) {
                selector1.eval(game, player) + selector2.eval(game, player)
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