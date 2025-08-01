package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GraphType {
    DIRECTED,
    UNDIRECTED,
    WEIGHTED;

    @JsonCreator
    public static GraphType fromValue(String value) {
        return GraphType.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
