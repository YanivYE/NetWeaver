package com.NetWeaver.Handlers;
import com.NetWeaver.Enums.CrawlMode;
import org.springframework.stereotype.Component;

@Component
public class ModeHandlerFactory {
    public ModeHandler getHandler(CrawlMode crawlMode) {
        return switch (crawlMode) {
            case CrawlMode.SEARCH -> new SearchModeHandler();
            case CrawlMode.CORRELATION -> new CorrelationModeHandler();
            case CrawlMode.SITEMAP -> new SitemapModeHandler();
        };
    }
}
