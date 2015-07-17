package core.card;

import core.Link;
import core.Player;

public class DeckZone extends Zone<Card> {
    private int fatigueDamage;

    /**
     * Maximal number of cards in deck zone
     */
    private final static int MAX_SIZE = 60;

    public DeckZone() {
        this(null);
    }

    public DeckZone(Link<Player> player) {
        super(MAX_SIZE, player, ZoneType.DECK);
        fatigueDamage = 0;
    }

//    public Card drawCard() { // TODO
//       /* if (isEmpty())
//            return new Fatigue(fatigueDamage++);*/
//
//        Card card = list().get(size() - 1);
//        remove(size() - 1);
//
//        return card;
//    }
//
//    public List<Card> drawCards(int n) {
//        List<Card> cards = new ArrayList<Card>();
//        for (int i = 0; i < n; ++i)
//            cards.add(drawCard());
//
//        return cards;
//    }

    @Override
    public DeckZone copy() {
        DeckZone d = (DeckZone) super.copy();
        d.fatigueDamage = fatigueDamage;
        return d;
    }
}
