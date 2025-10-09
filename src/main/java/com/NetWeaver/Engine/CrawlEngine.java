package com.NetWeaver.Engine;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Core.*;
import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Sink.PageSink;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class CrawlEngine {
    private final Fetcher fetcher;
    private final Parser parser;
    private final RobotsService robots;
    private final RateLimiter rateLimiter;
    private final UrlNormalizer normalizer;
    private final Frontier frontier;
    private final Clock clock;

    public CrawlEngine(Fetcher fetcher,
                       Parser parser,
                       RobotsService robots,
                       RateLimiter rateLimiter,
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

    public void run(CrawlContext ctx) {
        Objects.requireNonNull(ctx.getHandler(), "ModeHandler is required");
        Objects.requireNonNull(ctx.getSink(), "PageSink is required");

        final ModeHandler handler = ctx.getHandler();
        final PageSink sink = ctx.getSink();
        final Instant started = clock.instant();

//        // Prepare
//        final String startUrlStr = ctx.getStartUrl();
//        final URI startUri = toUriOrNull(startUrlStr);
//        if (startUri == null) return;
//
//        final Set<String> visited = ConcurrentHashMap.newKeySet();
//        final int maxDepth = Math.max(0, ctx.getMaxDepth());
//        final int maxPages = Math.max(1, ctx.getMaxPages());
//        final int timeLimitSec = Math.max(0, ctx.getTimeLimit());
//        final Instant deadline = timeLimitSec > 0 ? started.plusSeconds(timeLimitSec) : Instant.MAX;
//
//        // Seed frontier
//        frontier.clear();
//        String seed = normalizer.normalize(startUri);
//        frontier.offer(new FrontierItem(startUri, 0, scorePriority(startUri, startUri)));
//        visited.add(seed);
//
//        int pagesProcessed = 0;
//
//        while (!frontier.isEmpty()) {
//            // Time limit check
//            if (clock.instant().isAfter(deadline)) break;
//
//            final FrontierItem current = frontier.poll();
//            if (current == null) break;
//
//            final URI uri = current.uri();
//            final int depth = current.depth();
//
//            // Depth check
//            if (depth > maxDepth) continue;
//
//            // Robots + politeness
//            if (!robots.isAllowed(uri, ctx.getUserAgent())) {
//                // optionally record a skip in sink
//                continue;
//            }
//            rateLimiter.awaitPermit(uri); // per-host polite delay
//
//            // Fetch page
//            FetchResult fetchResult;
//            try {
//                fetchResult = fetcher.fetch(uri, ctx.getUserAgent());
//            } catch (IOException e) {
//                // optionally sink error metric
//                continue;
//            }
//            if (!fetchResult.ok()) {
//                // optionally sink HTTP status
//                continue;
//            }
//
//            // Parse to get title, text, links, contentType
//            ParsedPage page;
//            try {
//                page = parser.parse(uri, fetchResult);
//            } catch (Exception parseErr) {
//                // optionally sink parse error
//                continue;
//            }
//
//            // Handler hook: index/graph/map this page
//            try {
//                handler.onPage(ctx, page, depth, sink);
//            } catch (Exception handlerErr) {
//                // don't let handler crash the crawl
//            }
//
//            pagesProcessed++;
//            if (pagesProcessed >= maxPages) break;
//
//            // Get next links to follow from handler (mode-specific strategy)
//            List<UrlWithDepth> nexts = Collections.emptyList();
//            try {
//                nexts = handler.linksToFollow(ctx, page, depth);
//            } catch (Exception handlerErr) {
//                // ignore and continue
//            }
//            if (nexts == null || nexts.isEmpty()) continue;
//
//            for (UrlWithDepth n : nexts) {
//                if (n == null || n.url == null) continue;
//
//                URI normalized = normalizeChild(uri, n.url);
//                if (normalized == null) continue;
//
//                String key = normalizer.normalize(normalized);
//
//                // Deduplicate & push to frontier
//                if (visited.add(key)) {
//                    // Optional: domain restriction (if you have ctx.domainRestrictions())
//                    if (!isWithinRestrictions(startUri, normalized, ctx)) continue;
//
//                    int nextDepth = n.depth;
//                    if (nextDepth <= maxDepth) {
//                        double priority = scorePriority(startUri, normalized);
//                        frontier.offer(new FrontierItem(normalized, nextDepth, priority));
//                    }
//                }
//            }
//        }

//        // Handler finalize
//        try {
//            handler.finish(ctx, sink);
//        } catch (Exception ignored) {
//        }
    }

    private boolean isWithinRestrictions(URI start, URI target, CrawlContext ctx) {
        // If you keep domainRestrictions in the request/context, enforce them here.
        // Example: same-host restriction by default
        if (start.getHost() == null || target.getHost() == null) return false;
        return start.getHost().equalsIgnoreCase(target.getHost());
        // If you want configurable: check ctx.getDomainRestrictions() list/regexes.
    }

    private URI normalizeChild(URI base, URI child) {
        try {
            URI abs = base.resolve(child); // handles relative links
            // Strip fragments, normalize default ports, lower-case host, etc.
            return new URI(
                    abs.getScheme() != null ? abs.getScheme().toLowerCase(Locale.ROOT) : "http",
                    abs.getUserInfo(),
                    abs.getHost() != null ? abs.getHost().toLowerCase(Locale.ROOT) : null,
                    defaultPort(abs.getScheme(), abs.getPort()),
                    normalizePath(abs.getPath()),
                    normalizeQuery(abs.getQuery()),
                    null
            );
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private URI toUriOrNull(String u) {
        try {
            return new URI(u);
        } catch (Exception e) {
            return null;
        }
    }

    private int defaultPort(String scheme, int port) {
        if (port == -1) return -1;
        if ("http".equalsIgnoreCase(scheme) && port == 80) return -1;
        if ("https".equalsIgnoreCase(scheme) && port == 443) return -1;
        return port;
    }

    private String normalizePath(String p) {
        if (p == null || p.isBlank()) return "/";
        // Collapse multiple slashes; remove "/./"; keep trailing slash semantics
        String s = p.replaceAll("/\\./", "/");
        s = s.replaceAll("/{2,}", "/");
        return s;
    }

    private String normalizeQuery(String q) {
        if (q == null || q.isBlank()) return null;
        // Simple normalization: sort by key (stable), drop tracking params if you like
        String[] parts = q.split("&");
        Arrays.sort(parts);
        return String.join("&", parts);
    }

    /** Higher score == earlier crawl. Bias to internal, shallow, shorter URLs. */
    private double scorePriority(URI start, URI url) {
        double score = 0.0;
        if (sameHost(start, url)) score += 2.0;
        score += -0.001 * (url.toString().length());
        // You can add path hints (e.g., prefer /docs/, /about/ etc.) or content-type hints if known.
        return score;
    }

    private boolean sameHost(URI a, URI b) {
        if (a.getHost() == null || b.getHost() == null) return false;
        return a.getHost().equalsIgnoreCase(b.getHost());
    }
}
