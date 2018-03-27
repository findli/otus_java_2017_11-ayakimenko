package ru.otus.L7.atm;

/**
 * Created by abyakimenko on 26.03.2018.
 */
public enum Denomination {

    FIVE_THOUSANDS(5000),
    TWO_THOUSANDS(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDREDS(500),
    TWO_HUNDREDS(200),
    ONE_HUNDRED(100);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
