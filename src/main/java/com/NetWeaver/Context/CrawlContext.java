package com.NetWeaver.Context;

import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Sink.PageSink;

import java.net.URI;
import java.util.UUID;

public class CrawlContext {

    private UUID crawlId;
    private final URI startUri;
    private final int maxDepth;
    private final int maxPages;
    private final long timeLimit;
    private final String userAgent;
    private final ModeHandler handler;
    private final PageSink sink;

    public CrawlContext(UUID crawlId,
                        URI startUri,
                        int maxDepth,
                        int maxPages,
                        long timeLimit,
                        String userAgent,
                        ModeHandler handler,
                        PageSink sink) {
        this.startUri = startUri;
        this.maxDepth = maxDepth;
        this.maxPages = maxPages;
        this.timeLimit = timeLimit;
        this.userAgent = userAgent;
        this.handler = handler;
        this.sink = sink;
    }

    public UUID getCrawlId() { return crawlId; }

    public void setCrawlId(UUID id) { this.crawlId = id; }

    public URI getStartUri() {
        return startUri;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public ModeHandler getHandler() {
        return handler;
    }

    public PageSink getSink() {
        return sink;
    }
}
