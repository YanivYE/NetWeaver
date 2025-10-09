package com.NetWeaver.Core;

import com.NetWeaver.Core.Entities.FetchResult;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class RobotsService {
    private static final Pattern SITEMAP = Pattern.compile("^sitemap:", Pattern.CASE_INSENSITIVE);
    private final Map<String, RobotsTxt> cache = new ConcurrentHashMap<>();
    private final Fetcher fetcher;

    public RobotsService(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public boolean isAllowed(URI uri, String userAgent) {
        String host = uri.getHost();
        if (host == null) return true;
        RobotsTxt rules = cache.computeIfAbsent(host, h -> load(uri, userAgent));
        return rules == null || rules.allowed(uri.getPath(), userAgent);
    }

    private RobotsTxt load(URI any, String userAgent) {
        try {
            URI robotsUri = new URI(any.getScheme(), any.getAuthority(), "/robots.txt", null, null);
            FetchResult r = fetcher.fetch(robotsUri, userAgent);
            if (!r.ok()) return RobotsTxt.allowAll();
            String body = new String(r.body(), java.nio.charset.StandardCharsets.UTF_8);
            return RobotsTxt.parse(body);
        } catch (Exception e) {
            return RobotsTxt.allowAll();
        }
    }

    /* Simple robots.txt parser (User-agent/Disallow/Allow) */
    static final class RobotsTxt {
        private final Map<String, List<String>> disallow = new HashMap<>();
        private final Map<String, List<String>> allow = new HashMap<>();

        static RobotsTxt allowAll() { return new RobotsTxt(); }

        static RobotsTxt parse(String text) {
            RobotsTxt rt = new RobotsTxt();
            String currentAgent = "*";
            for (String raw : text.split("\\r?\\n")) {
                String line = raw.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                if (SITEMAP.matcher(line).find()) continue;

                int idx = line.indexOf(':');
                if (idx <= 0) continue;
                String k = line.substring(0, idx).trim().toLowerCase(Locale.ROOT);
                String v = line.substring(idx + 1).trim();

                switch (k) {
                    case "user-agent":
                        currentAgent = v.toLowerCase(Locale.ROOT);
                        break;
                    case "disallow":
                        rt.disallow.computeIfAbsent(currentAgent, a -> new ArrayList<>()).add(v);
                        break;
                    case "allow":
                        rt.allow.computeIfAbsent(currentAgent, a -> new ArrayList<>()).add(v);
                        break;
                }
            }
            return rt;
        }

        boolean allowed(String path, String ua) {
            String agent = ua != null ? ua.toLowerCase(Locale.ROOT) : "*";
            List<String> d = union(disallow.get("*"), disallow.get(agent));
            List<String> a = union(allow.get("*"), allow.get(agent));
            // Longest match wins; Allow overrides Disallow if longer
            String dis = longestPrefixMatch(path, d);
            String alw = longestPrefixMatch(path, a);
            if (alw == null && dis == null) return true;
            if (alw == null) return false;
            if (dis == null) return true;
            return alw.length() >= dis.length();
        }

        private List<String> union(List<String> a, List<String> b) {
            if (a == null) return b == null ? List.of() : b;
            if (b == null) return a;
            List<String> out = new ArrayList<>(a.size() + b.size());
            out.addAll(a); out.addAll(b);
            return out;
        }

        private String longestPrefixMatch(String path, List<String> prefixes) {
            String best = null;
            for (String p : prefixes) {
                if (p.isEmpty()) continue;
                if (path.startsWith(p)) {
                    if (best == null || p.length() > best.length()) best = p;
                }
            }
            return best;
        }
    }

}
