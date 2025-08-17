package com.NetWeaver.DTO;

import com.NetWeaver.DTO.Modes.CrawlModeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrawlRequest {
    @JsonProperty("start_url")
    private URI startUrl;

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

    public URI startUrl() {
        return this.startUrl;
    }

    public int depth() {
        return this.maxDepth;
    }

    public int pages() {
        return this.maxPages;
    }

    public long timeLimit() {
        return this.timeLimit;
    }

    public List<String> restrictions() {
        return this.restrictions;
    }

    public String userAgent() {
        return this.userAgent;
    }

    public CrawlModeDTO mode() {
        return this.mode;
    }
}
