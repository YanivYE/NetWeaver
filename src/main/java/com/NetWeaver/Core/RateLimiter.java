package com.NetWeaver.Core;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    private final Map<String, Instant> last = new ConcurrentHashMap<>();
    private final Duration minDelay;

    public RateLimiter(Duration minDelay) {
        this.minDelay = minDelay != null ? minDelay : Duration.ofMillis(400);
    }

    public void awaitPermit(URI uri) {
        String host = uri.getHost();
        if (host == null) return;
        Instant now = Instant.now();
        Instant prev = last.getOrDefault(host, Instant.EPOCH);
        Instant nextOk = prev.plus(minDelay);
        if (now.isBefore(nextOk)) {
            try {
                Thread.sleep(Duration.between(now, nextOk).toMillis());
            } catch (InterruptedException ignored) { Thread.currentThread().interrupt(); }
        }
        last.put(host, Instant.now());
    }
}
