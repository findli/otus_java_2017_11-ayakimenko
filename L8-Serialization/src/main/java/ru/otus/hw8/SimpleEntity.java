package ru.otus.hw8;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SimpleEntity {

    private int id;
    private int counter;
    private Integer count;
    private BigInteger bigInteger;
    private Long longCount;
    private long random;
    private double doubleRandom;
    private BigDecimal bigDecimal;
    private boolean bigBool;
    private String strVal;

    public SimpleEntity(int id, int counter, Integer count, long random) {
        this.id = id;
        this.counter = counter;
        this.count = count;
        this.longCount = 113l;
        this.bigInteger = BigInteger.valueOf(100500);
        this.longCount = 88888l;
        this.random = random;
        this.doubleRandom = 55.123;
        this.bigDecimal = BigDecimal.valueOf(1234.6777);
        this.bigBool = true;
        this.strVal = "SHORT STRING";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public long getRandom() {
        return random;
    }

    public void setRandom(long random) {
        this.random = random;
    }

    public double getDoubleRandom() {
        return doubleRandom;
    }

    public void setDoubleRandom(double doubleRandom) {
        this.doubleRandom = doubleRandom;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public Long getLongCount() {
        return longCount;
    }

    public void setLongCount(Long longCount) {
        this.longCount = longCount;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public boolean isBigBool() {
        return bigBool;
    }

    public void setBigBool(boolean bigBool) {
        this.bigBool = bigBool;
    }

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }
}