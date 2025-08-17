package com.NetWeaver.DTO.Modes;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.MatchStrategy;
import com.NetWeaver.Enums.SearchField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SEARCH;

    @JsonProperty("search_keyword")
    private String searchKeyword;

    @JsonProperty("search_fields")
    private SearchField[] searchFields;

    @JsonProperty("match_strategy")
    private MatchStrategy matchStrategy;

    @JsonProperty("min_relevance")
    private float minRelevance;

    @Override
    public CrawlMode getType() {
        return type;
    }

    public String getSearchKeyword() {
        return this.searchKeyword;
    }

    public SearchField[] getSearchFields() {
        return this.searchFields;
    }

    public MatchStrategy getMatchStrategy() {
        return this.matchStrategy;
    }

    public float getMinRelevance() {
        return this.minRelevance;
    }
}
