package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SearchField {
    TITLE,
    BODY,
    META;

    @JsonCreator
    public static SearchField fromValue(String value) {
        return SearchField.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
