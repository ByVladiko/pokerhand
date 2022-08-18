package com.vldby.pockerhand.model.util;

import com.vldby.pockerhand.enums.Rank;
import com.vldby.pockerhand.enums.Suit;
import com.vldby.pockerhand.exception.ParseException;
import com.vldby.pockerhand.model.Card;
import com.vldby.pockerhand.model.PokerHand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokerHandGenerator {

    private final Random random = new Random();
    private final Rank[] ranks = Rank.values();
    private final Suit[] suits = Suit.values();

    public PokerHand generateHand() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < PokerHand.HAND_SIZE; i++) {
            Rank rank = ranks[random.nextInt(ranks.length)];
            Suit suit = suits[random.nextInt(suits.length)];
            cards.add(new Card(rank, suit));
        }

        PokerHand pokerHand = null;
        try {
            pokerHand = new PokerHand(cards);
        } catch (ParseException ignore) {}

        return pokerHand;
    }

}
