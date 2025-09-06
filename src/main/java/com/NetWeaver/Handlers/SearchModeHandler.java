package com.NetWeaver.Handlers;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Models.DTO.CrawlModeDTO;
import com.NetWeaver.Models.DepthUrl;
import com.NetWeaver.Models.ParsedPage;
import com.NetWeaver.Sink.PageSink;

import java.util.List;

public class SearchModeHandler implements ModeHandler {
    public SearchModeHandler() {}

    @Override
    public void onPage(CrawlContext ctx, ParsedPage page, int depth, PageSink sink) {

    }

    @Override
    public List<DepthUrl> linksToFollow(CrawlContext ctx, ParsedPage page, int depth) {
        return List.of();
    }

    @Override
    public CrawlResult finish(CrawlContext ctx, PageSink sink) {
        return null;
    }
}
