package com.vldby.pockerhand.enums;

public enum Suit {
    SPADES("S"),//Пики
    HEARTS("H"),//Черви
    DIAMONDS("D"),//Буби
    CLUBS("C");//Крести

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
