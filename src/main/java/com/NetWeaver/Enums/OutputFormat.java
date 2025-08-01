package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OutputFormat {
    TREE,
    XML,
    JSON,
    CSV;

    @JsonCreator
    public static OutputFormat fromValue(String value) {
        return OutputFormat.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
