package ru.otus.L8;

public class SimpleEntity {

    private int id;
    private int counter;
    private Integer count;
    private long random;
    private double doubleRandom;

    public SimpleEntity(int id, int counter, Integer count, long random) {
        this.id = id;
        this.counter = counter;
        this.count = count;
        this.random = random;
        this.doubleRandom = 55.123;
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
}