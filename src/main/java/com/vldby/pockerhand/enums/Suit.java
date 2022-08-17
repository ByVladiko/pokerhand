package com.vldby.pockerhand.enums;

public enum Suit {
    SPADES("S"),//����
    HEARTS("H"),//�����
    DIAMONDS("D"),//����
    CLUBS("C");//������

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public static Suit fromSymbol(String symbol) {
        for (Suit suit : Suit.values())
            if (suit.getSymbol().equals(symbol))
                return suit;

        return null;
    }

    public String getSymbol() {
        return symbol;
    }
}
