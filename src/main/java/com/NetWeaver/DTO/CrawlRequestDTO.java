package com.NetWeaver.DTO;

import com.NetWeaver.DTO.Modes.CrawlModeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlRequestDTO {
    @JsonProperty("start_url")
    private String startUrl;

    @JsonProperty("max_depth")
    private int maxDepth;

    @JsonProperty("max_pages")
    private int maxPages;

    @JsonProperty("time_limit")
    private long timeLimit;

    @JsonProperty("restrictions")
    private List<String> restrictions;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("mode")
    private CrawlModeDTO mode;

    public String getStartUrl() {
        return this.startUrl;
    }

    public int getDepth() {
        return this.maxDepth;
    }

    public int getPages() {
        return this.maxPages;
    }

    public long getTimeLimit() {
        return this.timeLimit;
    }

    public List<String> getRestrictions() {
        return this.restrictions;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public CrawlModeDTO getMode() {
        return this.mode;
    }
}
