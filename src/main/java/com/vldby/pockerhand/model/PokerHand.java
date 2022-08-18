package com.vldby.pockerhand.model;

import com.vldby.pockerhand.exception.ParseException;
import com.vldby.pockerhand.service.CombinationEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vldby.pockerhand.model.Card.CARD_SIZE;

public class PokerHand implements Comparable<PokerHand> {

    public static final String CARDS_DELIM = " ";
    public static final int HAND_SIZE = 5;

    private final List<Card> cards = new ArrayList<>(HAND_SIZE);
    private final Combination combination;

    public PokerHand(String input) throws ParseException {
        init(input);
        combination = new CombinationEvaluator(this).evaluateCombination();
    }

    public PokerHand(List<Card> cards) throws ParseException {
        if (cards.size() != HAND_SIZE)
            throw new ParseException("Hand size must be " + HAND_SIZE);

        this.cards.addAll(cards);
        combination = new CombinationEvaluator(this).evaluateCombination();
    }

    private void init(String input) throws ParseException {
        String[] strCards = input.split(CARDS_DELIM);
        for (int i = 0; i < strCards.length; i++) {
            try {
                addCard(strCards[i]);
            } catch (ParseException e) {
                int index = i * (CARD_SIZE + CARDS_DELIM.length()) + e.getErrorOffset();
                throw new ParseException("Invalid syntax on position: " + index, index, e);
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public Combination getCombination() {
        return combination;
    }

    private void addCard(String card) throws ParseException {
        Card parsedCard = new Card(card);
        addCard(parsedCard);
    }

    private void addCard(Card card) throws ParseException {
        if (cards.size() >= HAND_SIZE)
            throw new ParseException("Hand size must be " + HAND_SIZE);

        cards.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PokerHand pokerHand = (PokerHand) o;

        return cards.equals(pokerHand.cards);
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }

    @Override
    public String toString() {
        return cards.stream()
                .map(card -> card.getRank().getSymbol() + card.getSuit().getSymbol())
                .collect(Collectors.joining(CARDS_DELIM));
    }

    @Override
    public int compareTo(PokerHand pokerHand) {
        return -combination.compareTo(pokerHand.getCombination());
    }
}