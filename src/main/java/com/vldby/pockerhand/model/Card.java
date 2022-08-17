package com.vldby.pockerhand.model;

import com.vldby.pockerhand.enums.Rank;
import com.vldby.pockerhand.enums.Suit;
import com.vldby.pockerhand.exception.ParseException;

public class Card implements Comparable<Card> {

    public static final int CARD_SIZE = 2;

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String input) throws ParseException {
        if (input == null || input.isBlank())
            throw new ParseException("Empty value");

        if (input.length() != 2)
            throw new ParseException("Invalid number of characters in input string", 0);

        rank = Rank.fromSymbol(input.substring(0, 1));
        if (rank == null)
            throw new ParseException("Invalid rank", 0);

        suit = Suit.fromSymbol(input.substring(1, 2));
        if (suit == null)
            throw new ParseException("Invalid suit", 1);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Card card = (Card) o;

        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return 31 * rank.hashCode() + suit.hashCode();
    }

    @Override
    public int compareTo(Card card) {
        return rank.getLevel() - card.rank.getLevel();
    }
}
