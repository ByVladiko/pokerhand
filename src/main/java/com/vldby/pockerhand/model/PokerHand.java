package com.vldby.pockerhand.model;

import com.vldby.pockerhand.enums.ComboType;
import com.vldby.pockerhand.enums.Rank;
import com.vldby.pockerhand.enums.Suit;
import com.vldby.pockerhand.exception.ParseException;

import java.util.*;
import java.util.stream.Collectors;

import static com.vldby.pockerhand.enums.ComboType.*;
import static com.vldby.pockerhand.enums.Rank.ACE;
import static com.vldby.pockerhand.model.Card.CARD_SIZE;

public class PokerHand implements Comparable<PokerHand> {

    public static final String CARDS_DELIM = " ";
    public static final int HAND_SIZE = 5;

    private final List<Card> cards = new ArrayList<>();
    private final Map<Suit, List<Card>> suitRepeats = new HashMap<>();
    private final Map<Rank, List<Card>> rankRepeats = new HashMap<>();
    private final Combination combination;

    public PokerHand(String input) throws ParseException {
        init(input);
        combination = evaluateCombination();
    }

    public PokerHand(List<Card> cards) throws ParseException {
        this.cards.addAll(cards);
        validate();
        for (Card card : cards) {
            addRankRepeat(card);
            addSuitRepeat(card);
        }
        combination = evaluateCombination();
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
        validate();
        for (Card card : cards) {
            addRankRepeat(card);
            addSuitRepeat(card);
        }
    }

    private Combination evaluateCombination() {
        List<Card> cards = new ArrayList<>(this.cards);
        Collections.sort(cards);

        final int lastIndex = HAND_SIZE - 1;
        Card lastCard = cards.get(lastIndex);
        Card firstCard = cards.get(0);

        if (suitRepeats.values().size() == 1) {
            if (rankRepeats.size() == HAND_SIZE &&
                    lastCard.getRank().getLevel() - firstCard.getRank().getLevel() == lastIndex) {
                if (lastCard.getRank() == ACE)
                    return new Combination(ROYAL_FLUSH, List.of(lastCard));

                return new Combination(STRAIGHT_FLUSH, List.of(lastCard));
            }

            if (rankRepeats.size() == HAND_SIZE && lastCard.getRank() == ACE
                    && cards.get(lastIndex - 1).getRank().getLevel() - cards.get(0).getRank().getLevel() == lastIndex - 1)
                return new Combination(STRAIGHT_FLUSH, List.of(lastCard));


            return new Combination(FLUSH, cards);
        }

        if (rankRepeats.size() == HAND_SIZE &&
                (lastCard.getRank().getLevel() - firstCard.getRank().getLevel() == lastIndex
                || (lastCard.getRank() == ACE
                && cards.get(lastIndex - 1).getRank().getLevel() - cards.get(0).getRank().getLevel() == lastIndex - 1)))
            return new Combination(STRAIGHT, List.of(lastCard));

        List<Card> currentKickers = null;
        ComboType combination = KICKER;
        for (int i = 0; i < rankRepeats.values().size(); i++) {
            List<Card> repeatRankCards = new ArrayList<>(rankRepeats.values()).get(i);
            if (repeatRankCards.size() == 4) {
                Card kicker = this.cards.get(0).getRank() == repeatRankCards.get(0).getRank()
                        ? this.cards.get(lastIndex)
                        : this.cards.get(0);
                return new Combination(CARE, List.of(kicker, repeatRankCards.get(0)));
            }

            if (repeatRankCards.size() == 3) {
                if (combination == PAIR)
                    return new Combination(FULL_HOUSE, List.of(currentKickers.get(0), repeatRankCards.get(0)));

                combination = SET;
                currentKickers = repeatRankCards;
                continue;
            }

            if (repeatRankCards.size() == 2) {
                if (combination == PAIR) {
                    List<Card> kickers = getSingleKickers();
                    kickers.add(currentKickers.get(0));
                    kickers.add(repeatRankCards.get(0));
                    return new Combination(TWO_PAIR, kickers);
                }

                if (combination == SET)
                    return new Combination(FULL_HOUSE, List.of(currentKickers.get(0), repeatRankCards.get(0)));

                combination = PAIR;
                currentKickers = repeatRankCards;
            }
        }

        if (combination == PAIR || combination == SET) {
            List<Card> kickers = getSingleKickers();
            kickers.add(currentKickers.get(0));
            return new Combination(combination, kickers);
        }

        return new Combination(combination, cards);
    }

    private List<Card> getSingleKickers() {
        return rankRepeats.values()
                .stream()
                .filter(cardsRank -> cardsRank.size() == 1)
                .map(lists -> lists.get(0))
                .collect(Collectors.toList());
    }

    public Card addCard(String card) throws ParseException {
        Card parsedCard = new Card(card);
        addCard(parsedCard);
        return parsedCard;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Combination getCombination() {
        return combination;
    }

    private void addSuitRepeat(Card card) {
        Suit suit = card.getSuit();
        if (suitRepeats.containsKey(suit))
            suitRepeats.get(suit).add(card);

        ArrayList<Card> cards = new ArrayList();
        cards.add(card);
        suitRepeats.put(suit, cards);
    }

    private void addRankRepeat(Card card) {
        Rank rank = card.getRank();
        if (rankRepeats.containsKey(rank)) {
            rankRepeats.get(rank).add(card);
            return;
        }

        ArrayList<Card> cards = new ArrayList();
        cards.add(card);
        rankRepeats.put(rank, cards);
    }

    private void validate() throws ParseException {
        if (cards.size() != HAND_SIZE)
            throw new ParseException("Hand size must be " + HAND_SIZE);
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