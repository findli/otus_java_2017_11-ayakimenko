package ru.otus.L8;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CustomTest {

    private BigDecimal decimal;
    private BigInteger integer;
    //private boolean goodCondition;
    private Entity embeddedEntity;

    public CustomTest(Date date, BigDecimal decimal, BigInteger integer) {
        this.decimal = decimal;
        this.integer = integer;
        this.embeddedEntity = new Entity(integer.intValue(), "embeddedEntity");
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public BigInteger getInteger() {
        return integer;
    }

    public void setInteger(BigInteger integer) {
        this.integer = integer;
    }

    public Entity getEmbeddedEntity() {
        return embeddedEntity;
    }

    public void setEmbeddedEntity(Entity embeddedEntity) {
        this.embeddedEntity = embeddedEntity;
    }
}