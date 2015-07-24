package core.selector

import core.TagType
import org.testng.internal.collections.Pair


class SelectorUnity extends Selector {
    SelectorUnity(Pair<TagType, ?>... program) {
        super(program)
    }
}
