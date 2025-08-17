package com.NetWeaver.Models;

import com.NetWeaver.Enums.CrawlStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CrawlResult {

    @JsonProperty("crawl_id")
    private final UUID crawlId;

    @JsonProperty("status")
    private final CrawlStatus status;

    @JsonProperty("pages_crawled")
    private final int pagesCrawled;

    @JsonProperty("duration")
    private final long duration;

    @JsonProperty("message")
    private final String message;

    public CrawlResult(UUID crawlId, CrawlStatus status, int pagesCrawled, long duration, String message) {
        this.crawlId = crawlId;
        this.status = status;
        this.pagesCrawled = pagesCrawled;
        this.duration = duration;
        this.message = message;
    }

    public UUID getCrawlId() {
        return crawlId;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public int getPagesCrawled() {
        return pagesCrawled;
    }

    public long getDuration() {
        return duration;
    }

    public String getMessage() {
        return message;
    }
}
