package com.NetWeaver.Engine;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;

public class CrawlEngine {
    private final Fetcher fetcher;
    private final Parser parser;
    private final RobotsService robots;
    private final PerHostRateLimiter rateLimiter;
    private final UrlNormalizer normalizer;
    private final Frontier frontier;
    private final Clock clock;

    public CrawlEngine(Fetcher fetcher,
                       Parser parser,
                       RobotsService robots,
                       PerHostRateLimiter rateLimiter,
                       UrlNormalizer normalizer,
                       Frontier frontier,
                       Clock clock) {
        this.fetcher = fetcher;
        this.parser = parser;
        this.robots = robots;
        this.rateLimiter = rateLimiter;
        this.normalizer = normalizer;
        this.frontier = frontier;
        this.clock = clock != null ? clock : Clock.systemUTC();
    }

    public CrawlResult run(CrawlContext context) throws Exception {

    }
}
