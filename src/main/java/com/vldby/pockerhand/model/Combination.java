package com.vldby.pockerhand.model;

import com.vldby.pockerhand.enums.ComboType;

import java.util.List;

public class Combination implements Comparable<Combination> {

    private final ComboType comboType;
    private final List<Card> kickers;

    public Combination(ComboType comboType, List<Card> kickers) {
        this.comboType = comboType;
        this.kickers = kickers;
    }

    public ComboType getComboType() {
        return comboType;
    }

    @Override
    public int compareTo(Combination combination) {
        int result = comboType.getLevel() - combination.getComboType().getLevel();
        if (result != 0)
            return result;

        for (int i = kickers.size() - 1; i >= 0; i--) {
            result = kickers.get(i).getRank().getLevel() - combination.kickers.get(i).getRank().getLevel();
            if (result != 0)
                return result;
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combination that = (Combination) o;

        return comboType == that.comboType || compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        int result = comboType != null ? comboType.hashCode() : 0;
        result = 31 * result + (kickers != null ? kickers.hashCode() : 0);
        return result;
    }
}
