package com.vldby.pockerhand;

import com.vldby.pockerhand.exception.ParseException;
import com.vldby.pockerhand.model.PokerHand;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        try {
            ArrayList<PokerHand> hands = new ArrayList<>();
            hands.add(new PokerHand("KS 2H 5C JD TD"));
            hands.add(new PokerHand("2C 3C AC 4C 5C"));
            Collections.sort(hands);
            hands.forEach(System.out::println);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

