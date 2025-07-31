package com.NetWeaver.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlRequestDTO {
    @JsonProperty
    private String startUrl;

    @JsonProperty
    private int depth;

    @JsonProperty
    private int pages;

    @JsonProperty
    private int timeLimit;

    @JsonProperty
    private String[] restrictions;

    @JsonProperty
    private String userAgent;

    @JsonProperty
    private CrawlMode mode;

    
}
