package com.vldby.pockerhand.enums;

public enum ComboType {
    KICKER(1), //Старшая карта
    PAIR(2), //Две карты одного достоинства
    TWO_PAIR(3), //Две карты одного достоинства, две карты другого достоинства
    SET(4), //Три карты одного достоинства
    STRAIGHT(5), //Пять карт, которые выстроились по старшинству
    FLUSH(6), //Пять карт одной масти
    FULL_HOUSE(7), //Три плюс два
    CARE(8), //Четыре карты одного достоинства
    STRAIGHT_FLUSH(9), //Пять карт одной масти, которые выстроились по старшинству
    ROYAL_FLUSH(10); //Пять карт от 10 до туза одной масти

    private final int level;

    ComboType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
