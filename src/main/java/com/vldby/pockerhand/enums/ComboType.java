package com.vldby.pockerhand.enums;

public enum ComboType {
    KICKER(1), //������� �����
    PAIR(2), //��� ����� ������ �����������
    TWO_PAIR(3), //��� ����� ������ �����������, ��� ����� ������� �����������
    SET(4), //��� ����� ������ �����������
    STRAIGHT(5), //���� ����, ������� ����������� �� �����������
    FLUSH(6), //���� ���� ����� �����
    FULL_HOUSE(7), //��� ���� ���
    CARE(8), //������ ����� ������ �����������
    STRAIGHT_FLUSH(9), //���� ���� ����� �����, ������� ����������� �� �����������
    ROYAL_FLUSH(10); //���� ���� �� 10 �� ���� ����� �����

    private final int level;

    ComboType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
