package com.NetWeaver.Context;

import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Sink.PageSink;

import java.net.URI;

public class CrawlContext {

    private final URI startUri;
    private final int maxDepth;
    private final int maxPages;
    private final long timeLimit;
    private final String userAgent;
    private final ModeHandler handler;
    private final PageSink sink;

    public CrawlContext(URI startUri,
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

    // Creates a new context with the same config but different sink
    public CrawlContext withSink(PageSink newSink) {
        return new CrawlContext(
                this.startUri,
                this.maxDepth,
                this.maxPages,
                this.timeLimit,
                this.userAgent,
                this.handler,
                newSink
        );
    }
}
