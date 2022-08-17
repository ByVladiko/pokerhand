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

    public PokerHand generateHand() throws ParseException {
        Random random = new Random();
        Rank[] ranks = Rank.values();
        Suit[] suits = Suit.values();
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < PokerHand.HAND_SIZE; i++) {
            Rank rank = ranks[random.nextInt(ranks.length - 1)];
            Suit suit = suits[random.nextInt(suits.length - 1)];
            cards.add(new Card(rank, suit));
        }

        return new PokerHand(cards);
    }

}
