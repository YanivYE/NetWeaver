package com.NetWeaver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CrawlMode {
    SEARCH("search"),
    CORRELATION("correlation"),
    SITEMAP("sitemap");

    private final String value;

    CrawlMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static CrawlMode fromValue(String value) throws IllegalArgumentException{
        for (CrawlMode mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Invalid crawl mode: " + value);
    }
}
