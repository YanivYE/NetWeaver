package com.NetWeaver.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CrawlMode {
    SEARCH,
    CORRELATION,
    SITEMAP;

    @JsonCreator
    public static CrawlMode fromValue(String value) {
        return CrawlMode.valueOf(value.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
