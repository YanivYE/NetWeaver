package com.NetWeaver.Models.DTO;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.MatchStrategy;
import com.NetWeaver.Enums.SearchField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SEARCH;

    @JsonProperty("search_keyword")
    private String searchKeyword;

    @JsonProperty("search_fields")
    private List<SearchField> searchFields;

    @JsonProperty("match_strategy")
    private MatchStrategy matchStrategy;

    @JsonProperty("min_relevance")
    private float minRelevance;

    @Override
    public CrawlMode getType() {
        return type;
    }

    public String searchKeyword() {
        return this.searchKeyword;
    }

    public List<SearchField> searchFields() {
        return this.searchFields;
    }

    public MatchStrategy matchStrategy() {
        return this.matchStrategy;
    }

    public float minRelevance() {
        return this.minRelevance;
    }
}
