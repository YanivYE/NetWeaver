package com.NetWeaver.Handlers;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Core.Entities.DepthUrl;
import com.NetWeaver.Core.Entities.PageData;
import com.NetWeaver.Sink.PageSink;

import java.util.List;

public class SearchModeHandler implements ModeHandler {
    public SearchModeHandler() {}

    @Override
    public void onPage(CrawlContext ctx, PageData page, int depth, PageSink sink) {

    }

    @Override
    public List<DepthUrl> linksToFollow(CrawlContext ctx, PageData page, int depth) {
        return List.of();
    }

    @Override
    public CrawlResult finish(CrawlContext ctx, PageSink sink) {
        return null;
    }
}
