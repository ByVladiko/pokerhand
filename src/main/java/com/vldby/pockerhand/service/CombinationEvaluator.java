package com.vldby.pockerhand.service;

import com.vldby.pockerhand.enums.ComboType;
import com.vldby.pockerhand.enums.Rank;
import com.vldby.pockerhand.enums.Suit;
import com.vldby.pockerhand.model.Card;
import com.vldby.pockerhand.model.Combination;
import com.vldby.pockerhand.model.PokerHand;

import java.util.*;
import java.util.stream.Collectors;

import static com.vldby.pockerhand.enums.ComboType.*;
import static com.vldby.pockerhand.enums.Rank.ACE;
import static com.vldby.pockerhand.model.PokerHand.HAND_SIZE;

public class CombinationEvaluator {

    private final Map<Suit, List<Card>> suitRepeats = new HashMap<>();
    private final Map<Rank, List<Card>> rankRepeats = new HashMap<>();
    private final List<Card> cards;

    public CombinationEvaluator(PokerHand pokerHand) {
        cards = new ArrayList<>(pokerHand.getCards());
        Collections.sort(cards);
        for (Card card : pokerHand.getCards()) {
            addRankRepeat(card);
            addSuitRepeat(card);
        }
    }

    public Combination evaluateCombination() {
        List<Card> cards = new ArrayList<>(this.cards);
        Collections.sort(cards);

        if (suitRepeats.values().size() == 1)
            return getFlushSubtype(cards);

        if (rankRepeats.size() == HAND_SIZE
                && (getLastCard().getLevel() - getFirstCard().getLevel() == getLastIndex()
                    || (getLastCard().getRank() == ACE
                    && cards.get(getLastIndex() - 1).getLevel() - cards.get(0).getLevel() == getLastIndex() - 1)))
            return new Combination(STRAIGHT, List.of(getLastCard()));

        List<Card> currentKickers = null;
        ComboType comboType = KICKER;
        for (int i = 0; i < rankRepeats.values().size(); i++) {
            List<Card> repeatRankCards = new ArrayList<>(rankRepeats.values()).get(i);
            switch (repeatRankCards.size()) {
                case 4:
                    return resolveCare(repeatRankCards);
                case 3:
                    if (comboType == PAIR)
                        return new Combination(FULL_HOUSE, List.of(currentKickers.get(0), repeatRankCards.get(0)));

                    comboType = SET;
                    currentKickers = repeatRankCards;
                    continue;
                case 2:
                    if (comboType == PAIR)
                        return resolveTwoPair(currentKickers, repeatRankCards);

                    if (comboType == SET)
                        return new Combination(FULL_HOUSE, List.of(currentKickers.get(0), repeatRankCards.get(0)));

                    comboType = PAIR;
                    currentKickers = repeatRankCards;
            }
        }

        if (comboType == PAIR || comboType == SET) {
            List<Card> kickers = getSingleKickers();
            kickers.add(currentKickers.get(0));
            return new Combination(comboType, kickers);
        }

        return new Combination(comboType, cards);
    }

    private Combination resolveTwoPair(List<Card> currentKickers, List<Card> repeatRankCards) {
        List<Card> kickers = getSingleKickers();
        kickers.add(currentKickers.get(0));
        kickers.add(repeatRankCards.get(0));
        return new Combination(TWO_PAIR, kickers);
    }

    private Combination resolveCare(List<Card> repeatRankCards) {
        Card kicker = this.cards.get(0).getRank() == repeatRankCards.get(0).getRank() ? this.cards.get(getLastIndex()) : this.cards.get(0);
        return new Combination(CARE, List.of(kicker, repeatRankCards.get(0)));
    }

    private Combination getFlushSubtype(List<Card> cards) {
        if (rankRepeats.size() == HAND_SIZE) {
            if (getLastCard().getLevel() - getFirstCard().getLevel() == getLastIndex()) {
                if (getLastCard().getRank() == ACE) return new Combination(ROYAL_FLUSH, List.of(getLastCard()));

                return new Combination(STRAIGHT_FLUSH, List.of(getLastCard()));
            }

            if (getLastCard().getRank() == ACE && cards.get(getLastIndex() - 1).getLevel() - cards.get(0).getLevel() == getLastIndex() - 1)
                return new Combination(STRAIGHT_FLUSH, List.of(getLastCard()));
        }

        return new Combination(FLUSH, cards);
    }

    private void addRankRepeat(Card card) {
        Rank rank = card.getRank();
        if (rankRepeats.containsKey(rank)) {
            rankRepeats.get(rank).add(card);
            return;
        }

        List<Card> cards = new ArrayList<>();
        cards.add(card);
        rankRepeats.put(rank, cards);
    }

    private void addSuitRepeat(Card card) {
        Suit suit = card.getSuit();
        if (suitRepeats.containsKey(suit)) suitRepeats.get(suit).add(card);

        List<Card> cards = new ArrayList<>();
        cards.add(card);
        suitRepeats.put(suit, cards);
    }

    private List<Card> getSingleKickers() {
        return rankRepeats.values().stream().filter(cardsRank -> cardsRank.size() == 1).map(lists -> lists.get(0)).collect(Collectors.toList());
    }

    private int getLastIndex() {
        return HAND_SIZE - 1;
    }

    private Card getLastCard() {
        return cards.get(getLastIndex());
    }

    private Card getFirstCard() {
        return cards.get(0);
    }
}
