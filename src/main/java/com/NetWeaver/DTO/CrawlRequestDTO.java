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


    public String startUrl() {
        return this.startUrl;
    }

    public int depth() {
        return this.depth;
    }

    public int pages() {
        return this.pages;
    }

    public int timeLimit() {
        return this.timeLimit;
    }

    public String[] restrictions() {
        return this.restrictions;
    }

    public String userAgent() {
        return this.userAgent;
    }

    public CrawlModeDTO mode() {
        return this.mode;
    }
}
