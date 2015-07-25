package core


enum ComparisonType {
    /**
     * Equals
     */
    E,
    /**
     * Not equals
     */
            NE,
    /**
     * More than
     */
            M,
    /**
     * Less than
     */
            L,
    /**
     * More than or equals
     */
            ME,
    /**
     * Less than or equals
     */
            LE

    boolean compare(Comparable obj1, Comparable obj2) {
        switch (this) {
            case E:
                return obj1 == obj2
            case NE:
                return obj1 != obj2
            case M:
                return obj1 > obj2
            case L:
                return obj1 < obj2
            case ME:
                return obj1 >= obj2
            case LE:
                return obj1 <= obj2
        }
        false
    }
}