package com.NetWeaver.Handlers;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;

public interface ModeHandler {
    void onPage(CrawlContext ctx, ParsedPage page, int depth, PageSink sink);
    List<UrlWithDepth> linksToFollow(CrawlContext ctx, ParsedPage page, int depth);
    CrawlResult finish(CrawlContext ctx, PageSink sink);
}

