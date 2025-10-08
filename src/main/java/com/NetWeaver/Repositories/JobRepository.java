package com.NetWeaver.Repositories;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.CrawlStatus;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

public class JobRepository {
    public void updateStatus(UUID crawlId, CrawlStatus crawlStatus) {
    }

    public void createJob(UUID crawlId, URI uri, CrawlMode type, Instant startedAt, CrawlStatus crawlStatus) {
        // Log and set in DB job status
    }
}
