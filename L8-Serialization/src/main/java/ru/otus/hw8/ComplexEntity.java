package ru.otus.hw8;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ComplexEntity {

    private BigDecimal decimal;
    private BigInteger integer;
    private SimpleEntity complex;
    private Entity embeddedEntity;

    public ComplexEntity(BigDecimal decimal, BigInteger integer) {
        this.decimal = decimal;
        this.integer = integer;
        this.complex = new SimpleEntity(11, 12, 55, 555);
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

    public SimpleEntity getComplex() {
        return complex;
    }

    public void setComplex(SimpleEntity complex) {
        this.complex = complex;
    }
}