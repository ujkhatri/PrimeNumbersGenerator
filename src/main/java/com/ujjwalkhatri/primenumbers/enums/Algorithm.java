package com.ujjwalkhatri.primenumbers.enums;

public enum Algorithm {
    DEFAULT,
    SIEVE;

    public static final String PARAM_NAME = "algorithm";
    public static final String DEFAULT_VALUE = "DEFAULT";

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}

