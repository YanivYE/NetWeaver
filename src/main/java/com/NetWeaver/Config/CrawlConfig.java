// src/main/java/com/NetWeaver/Config/CrawlConfig.java
package com.NetWeaver.Config;

import com.NetWeaver.Core.*;
import com.NetWeaver.Core.Fetcher;
import com.NetWeaver.Engine.CrawlEngine;
import com.NetWeaver.Core.Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Duration;

@Configuration
public class CrawlConfig {

    @Bean
    public Fetcher httpFetcher() {
        return new Fetcher(
                Duration.ofSeconds(10),   // connect timeout
                Duration.ofSeconds(25),   // request timeout
                true,                     // follow redirects
                3 * 1024 * 1024           // max bytes
        );
    }

    @Bean
    public Parser htmlParser() {
        return new Parser(20_000);
    }

    @Bean
    public CrawlEngine crawlEngine(Fetcher fetcher, Parser parser) {
        return new CrawlEngine(
                fetcher,
                parser,
                new RobotsService(fetcher),
                new RateLimiter(Duration.ofMillis(500)),
                new UrlNormalizer(),
                new Frontier(),
                Clock.systemUTC()
        );
    }
}
