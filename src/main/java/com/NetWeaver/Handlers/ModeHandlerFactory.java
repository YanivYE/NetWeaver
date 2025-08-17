package com.NetWeaver.Handlers;

import com.NetWeaver.Models.DTO.CrawlModeDTO;
import com.NetWeaver.Enums.CrawlMode;

public class ModeHandlerFactory {
    public ModeHandler getHandler(CrawlMode modeType, CrawlModeDTO payload) {
        return switch (modeType) {
            case CrawlMode.SEARCH -> new SearchModeHandler(payload);
            case CrawlMode.CORRELATION -> new CorrelationModeHandler(payload);
            case CrawlMode.SITEMAP -> new SitemapModeHandler(payload);
        };
    }
}
