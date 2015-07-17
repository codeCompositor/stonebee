package core

trait Entity implements PlayerOwnable {
    String name
    Link link
    Link<Player> player
    boolean pendingDestroy
}
