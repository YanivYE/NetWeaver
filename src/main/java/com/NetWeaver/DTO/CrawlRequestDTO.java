package com.NetWeaver.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlRequestDTO {
    @JsonProperty("start_url")
    private String startUrl;

    @JsonProperty("depth")
    private int depth;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("time_limit")
    private int timeLimit;

    @JsonProperty("restrictions")
    private String[] restrictions;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("mode")
    private CrawlModeDTO mode;


}
