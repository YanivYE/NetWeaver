package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MatchStrategy {
    EXACT,
    PARTIAL,
    REGEX;

    @JsonCreator
    public static MatchStrategy fromValue(String value) {
        return MatchStrategy.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
