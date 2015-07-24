package core

import org.testng.internal.collections.Pair

class Selector {
    Pair<String, ?>[] program

    Selector(Pair<String, ?>... program) {
        this.program = program
    }

    List<Entity> eval(Game game, Player player) {
        def result = []
        def owner = program.find { it.first() == "side" }.second()
        program.findAll { it.first() == "zone" }*.second().each {
            owner.zones[it].findAll { entity ->
                def flag = false
                program.each { flag |= entity.getFrom(game)[it.first()] == it.second() }
                flag
            }
        }
        result
    }
}