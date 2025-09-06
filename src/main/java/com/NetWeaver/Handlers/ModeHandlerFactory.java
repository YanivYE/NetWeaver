package com.NetWeaver.Handlers;

import com.NetWeaver.Models.DTO.CrawlModeDTO;
import com.NetWeaver.Enums.CrawlMode;

public class ModeHandlerFactory {
    public ModeHandler getHandler(CrawlModeDTO crawlMode) {
        return switch (crawlMode.type()) {
            case CrawlMode.SEARCH -> new SearchModeHandler();
            case CrawlMode.CORRELATION -> new CorrelationModeHandler();
            case CrawlMode.SITEMAP -> new SitemapModeHandler();
        };
    }
}
