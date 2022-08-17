package com.vldby.pockerhand.enums;

public enum Rank implements Comparable<Rank>{
    TWO("2", 1),
    THREE("3", 2),
    FOUR("4", 3),
    FIVE("5", 4),
    SIX("6", 5),
    SEVEN("7", 6),
    EIGHT("8", 7),
    NINE("9", 8),
    TEN("T", 9),
    JACK("J", 10),
    QUEEN("Q", 11),
    KING("K", 12),
    ACE("A", 13);

    final String symbol;
    final int level;

    Rank(String symbol, int level) {
        this.symbol = symbol;
        this.level = level;
    }

    public static Rank fromSymbol(String symbol) {
        for (Rank rank : Rank.values())
            if (rank.getSymbol().equals(symbol))
                return rank;

        return null;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLevel() {
        return level;
    }
}
