package com.NetWeaver.Handlers;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Core.Entities.DepthUrl;
import com.NetWeaver.Core.Entities.ParsedPage;
import com.NetWeaver.Sink.PageSink;

import java.util.List;

public interface ModeHandler {
    void onPage(CrawlContext ctx, ParsedPage page, int depth, PageSink sink);
    List<DepthUrl> linksToFollow(CrawlContext ctx, ParsedPage page, int depth);
    CrawlResult finish(CrawlContext ctx, PageSink sink);
}
