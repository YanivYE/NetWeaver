package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SEARCH;

    // TODO: Add enums for types
    @JsonProperty("keyword")
    private String searchKeyword;

    @JsonProperty("fields")
    private String[] searchFields;

    @JsonProperty("strategy")
    private String matchStrategy;

    @JsonProperty("min_relevance")
    private float minRelevance;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
