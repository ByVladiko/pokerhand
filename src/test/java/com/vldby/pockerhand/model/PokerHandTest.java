package com.vldby.pockerhand.model;

import com.vldby.pockerhand.exception.ParseException;
import com.vldby.pockerhand.model.util.PokerHandGenerator;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.vldby.pockerhand.enums.ComboType.*;
import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {

    @Test
    public void testCombinationDefinition() throws ParseException {
        assertSame(ROYAL_FLUSH, new PokerHand("AH KH TH JH QH").getCombination().getComboType());
        assertSame(ROYAL_FLUSH, new PokerHand("TH JH QH KH AH").getCombination().getComboType());

        assertSame(STRAIGHT_FLUSH, new PokerHand("3C AC 2C 5C 4C").getCombination().getComboType());
        assertSame(STRAIGHT_FLUSH, new PokerHand("4C 8C 7C 5C 6C").getCombination().getComboType());

        assertSame(CARE, new PokerHand("AS AH AC AD QC").getCombination().getComboType());
        assertSame(CARE, new PokerHand("2S 2H 2C 2D 3C").getCombination().getComboType());

        assertSame(FULL_HOUSE, new PokerHand("2S 5H 2C 5D 2C").getCombination().getComboType());
        assertSame(FULL_HOUSE, new PokerHand("AS 5H AC AD 5C").getCombination().getComboType());

        assertSame(FLUSH, new PokerHand("2S 3S 8S 9S JS").getCombination().getComboType());
        assertSame(FLUSH, new PokerHand("AS 9S TS 2S 4S").getCombination().getComboType());

        assertSame(STRAIGHT, new PokerHand("JS QH AS KD TC").getCombination().getComboType());
        assertSame(STRAIGHT, new PokerHand("3S 4H AS 2D 5C").getCombination().getComboType());

        assertSame(SET, new PokerHand("AC JD JH JD KH").getCombination().getComboType());
        assertSame(SET, new PokerHand("5C 5D AH 6D 5H").getCombination().getComboType());

        assertSame(TWO_PAIR, new PokerHand("AS KC KS QC QS").getCombination().getComboType());
        assertSame(TWO_PAIR, new PokerHand("8S 8C 9D 9C KD").getCombination().getComboType());

        assertSame(PAIR, new PokerHand("TS JC QD TH AH").getCombination().getComboType());
        assertSame(PAIR, new PokerHand("AS KC TD 3H 3H").getCombination().getComboType());

        assertSame(KICKER, new PokerHand("4H JH QH 7D 8H").getCombination().getComboType());
        assertSame(KICKER, new PokerHand("3H 9H AH 5D 4H").getCombination().getComboType());
    }

    @Test
    public void parseTest() {
        ParseException exception;

        exception = assertThrows(ParseException.class, () -> new PokerHand("44 JH QH 7D 8H"));
        assertEquals("Invalid syntax on position: 1", exception.getMessage());
        assertEquals(1, exception.getErrorOffset());

        exception = assertThrows(ParseException.class, () -> new PokerHand("4 JH H 7D 8H"));
        assertEquals("Invalid syntax on position: 0", exception.getMessage());
        assertEquals("Invalid number of characters in input string", exception.getCause().getMessage());

        exception = assertThrows(ParseException.class, () -> new PokerHand(""));
        assertEquals("Invalid syntax on position: -1", exception.getMessage());
        assertEquals("Empty value", exception.getCause().getMessage());

        exception = assertThrows(ParseException.class, () -> new PokerHand("TSJC QD TH AH"));
        assertEquals("Invalid syntax on position: 0", exception.getMessage());
        assertEquals("Invalid number of characters in input string", exception.getCause().getMessage());

        exception = assertThrows(ParseException.class, () -> new PokerHand("TS JC DQ TH AH"));
        assertEquals("Invalid syntax on position: 6", exception.getMessage());
        assertEquals("Invalid rank", exception.getCause().getMessage());
        assertEquals(6, exception.getErrorOffset());

        exception = assertThrows(ParseException.class, () -> new PokerHand("TE JC DQ TH AH"));
        assertEquals("Invalid syntax on position: 1", exception.getMessage());
        assertEquals("Invalid suit", exception.getCause().getMessage());
        assertEquals(1, exception.getErrorOffset());

        exception = assertThrows(ParseException.class, () -> new PokerHand("TS JC QD TH AH QS"));
        assertEquals("Hand size must be " + PokerHand.HAND_SIZE, exception.getCause().getMessage());
    }

    @Test
    public void comparisonTest() throws ParseException {
        assertTrue(new PokerHand("2S 3S 8S 9S JS").compareTo(new PokerHand("2S 3S 7S TS JS")) > 0);
        assertTrue(new PokerHand("AS KC KS QC QS").compareTo(new PokerHand("AS KC KH JD JH")) < 0);
        assertTrue(new PokerHand("8S 8C 9D 9C KD").compareTo(new PokerHand("8S 8C 9D TH 9C")) < 0);
        assertTrue(new PokerHand("8S 9H TS JD QC").compareTo(new PokerHand("8H 9S TH JS JD")) < 0);
        assertTrue(new PokerHand("AS AH AC AD QC").compareTo(new PokerHand("AS AH AC AD KC")) > 0);
        assertTrue(new PokerHand("TS JC QD TH AH").compareTo(new PokerHand("8S 8H TS TH AH")) > 0);
        assertEquals(0, new PokerHand("AS KC TD 3H 3H").compareTo(new PokerHand("AD KH TD 3H 3H")));
        assertTrue(new PokerHand("2H TH JH KH TH").compareTo(new PokerHand("AC AD JH JD KH")) < 0);
        assertEquals(0, new PokerHand("KD KC TH TS AS").compareTo(new PokerHand("KD KC TH TS AC")));
    }

    @Test
    public void pokerHandGeneratorTest() {
        PokerHandGenerator pokerHandGenerator = new PokerHandGenerator();
        IntStream.range(0, 100).forEach((i) -> assertDoesNotThrow(() -> {
            PokerHand pokerHand = pokerHandGenerator.generateHand();
            System.out.println(i + ": " + pokerHand + " " + pokerHand.getCombination().getComboType());
        }));
    }

}