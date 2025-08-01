package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Asset {
    IMAGES,
    CSS,
    JS;

    @JsonCreator
    public static Asset fromValue(String value) {
        return Asset.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
