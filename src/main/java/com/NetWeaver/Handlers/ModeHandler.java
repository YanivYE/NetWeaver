package com.NetWeaver.Handlers;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Sink.PageSink;

public abstract class ModeHandler {
    protected CrawlContext context;

    public ModeHandler(CrawlContext context) {
        this.context = context;
    }

    public abstract void onPage(ParsedPage page, int depth, PageSink sink);
    public abstract List<UrlWithDepth> linksToFollow(ParsedPage page, int depth);
    public abstract CrawlResult finish(PageSink sink);
}
