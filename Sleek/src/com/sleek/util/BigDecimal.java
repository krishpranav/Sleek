package com.sleek.util;

import java.math.BigInteger;

public class BigDecimal {
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);

    TBigDecimal peer;

    public static BigDecimal getInstance(BigInteger value, int scale) {
        return new BigDecimal(value.shiftLeft(scale), scale);
    }

    public BigDecimal(BigInteger bigInt, int scale) {
        peer = new TBigDecimal(bigInt.peer, scale);
    }

    private BigDecimal(BigDecimal limBigDec) {
        peer = new TBigDecimal(limBigDec.toString());
    }

    private BigDecimal(TBigDecimal peer) {
        this.peer = peer;
    }

    public BigDecimal adjustScale(int newScale) {
        return new BigDecimal(peer.setScale(newScale));
    }

    public BigDecimal add(BigDecimal b) {
        return new BigDecimal(peer.add(b.peer));
    }

    public BigDecimal add(BigInteger b) {
        return new BigDecimal(peer.add(new TBigDecimal(b.peer, 0)));
    }

    public BigDecimal negate() {
        return new BigDecimal(peer.negate());
    }

    public BigDecimal subtract(BigDecimal b) {
        return new BigDecimal(peer.subtract(b.peer));
    }

    public BigDecimal subtract(BigInteger b) {
        return new BigDecimal(peer.subtract(new TBigDecimal(b.peer, 0)));
    }

    public BigDecimal multiply(BigDecimal b) {
        return new BigDecimal(peer.multiply(b.peer));
    }

    public BigDecimal multiply(BigInteger b) {
        return new BigDecimal(peer.multiply(new TBigDecimal(b.peer, 0)));
    }

    public BigDecimal divide(BigDecimal b) {
        return new BigDecimal(peer.divide(b.peer));
    }

    public BigDecimal divide(BigInteger b) {
        return new BigDecimal(peer.divide(new TBigDecimal(b.peer, 0)));
    }

    public BigDecimal shiftLeft(int n) {
        throw new RuntimeException("Not implemented yet");
    }

    public int compareTo(BigDecimal val) {
        return peer.compareTo(val.peer);
    }

    public int compareTo(BigInteger val) {
        return peer.compareTo(new TBigDecimal(val.peer, 0));
    }

    public BigInteger floor() {
        BigInteger out = new BigInteger(peer.toBigInteger());
        if (peer.signum() < 0) {
            return out.subtract(new BigInteger("1", 0));
        }
        return out;
    }

    public BigInteger round() {
        BigInteger out = new BigInteger(peer.toBigInteger());
        BigDecimal outD = new BigDecimal(out, 0);

        BigInteger next = peer.signum() < 0 ? out.subtract(BigInteger.ONE) : out.add(BigInteger.ONE);
        BigDecimal nextD = new BigDecimal(next, 0);

        BigDecimal diffThis = new BigDecimal(outD.peer.abs().subtract(peer.abs()).abs());
        BigDecimal diffNext = new BigDecimal(outD.peer.abs().subtract(nextD.peer.abs()).abs());

        return diffThis.compareTo(diffNext) > 0 ? out : next;

    }

    public int intValue() {
        return floor().intValue();
    }

    public long longValue() {
        return floor().longValue();
    }

    public int getScale() {
        return peer.scale();
    }

    public String toString() {
        return peer.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BigDecimal)) {
            return false;
        }

        BigDecimal other = (BigDecimal) o;
        return peer.equals(other.peer);
    }

    public int hashCode() {
        return peer.hashCode();
    }

}