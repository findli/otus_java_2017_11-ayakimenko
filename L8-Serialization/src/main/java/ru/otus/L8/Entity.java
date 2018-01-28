package ru.otus.L8;

public class Entity {

    private int id;
    private String name;
    private Integer count;
    private transient long random;

    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
        this.count = 45;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}