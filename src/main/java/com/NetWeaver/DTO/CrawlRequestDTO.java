package com.NetWeaver.DTO;

import com.NetWeaver.DTO.Modes.CrawlModeDTO;
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
    private long timeLimit;

    @JsonProperty("restrictions")
    private String[] restrictions;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("mode")
    private CrawlModeDTO mode;


    public String getStartUrl() {
        return this.startUrl;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getPages() {
        return this.pages;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }

    public String[] getRestrictions() {
        return this.restrictions;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public CrawlModeDTO getMode() {
        return this.mode;
    }
}
