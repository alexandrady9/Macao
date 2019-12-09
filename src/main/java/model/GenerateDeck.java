package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateDeck {

    public static List<Card> generate() {
        List<Card> cards = new ArrayList<Card>() {{
            for (Number number : Number.values()) {
                for (Suit suit : Suit.values()) {
                    if(!number.equals(Number.Joker)) {
                        if(!(suit.equals(Suit.Red) || suit.equals(Suit.Black))) {
                            add(new Card(suit, number));
                        }
                    }
                }
            }
            add(new Card(Suit.Red, Number.Joker));
            add(new Card(Suit.Black, Number.Joker));
        }};

        Collections.shuffle(cards);

        return cards;
    }
}
