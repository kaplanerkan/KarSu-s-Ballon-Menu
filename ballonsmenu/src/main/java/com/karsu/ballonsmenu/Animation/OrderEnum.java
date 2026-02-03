package com.karsu.ballonsmenu.Animation;

/**
 * Created by Erkan Kaplan on 2026-02-03
 */

public enum OrderEnum {

    DEFAULT(0),
    REVERSE(1),
    RANDOM (2),

    Unknown(-1);

    private final int value;

    OrderEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderEnum getEnum(int value) {
        if (value < 0 || value >= values().length) return Unknown;
        return values()[value];
    }
}

