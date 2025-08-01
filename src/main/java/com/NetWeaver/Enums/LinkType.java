package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LinkType {
    INTERNAL,
    EXTERNAL;

    @JsonCreator
    public static LinkType fromValue(String value) {
        return LinkType.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
