package com.NetWeaver.Services;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.CrawlStatus;
import com.NetWeaver.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public void updateStatus(UUID crawlId, CrawlStatus crawlStatus) {
        this.jobRepository.updateStatus(crawlId, crawlStatus);
    }

    public void createJob(UUID crawlId, URI uri, CrawlMode type, Instant startedAt, CrawlStatus crawlStatus) {
        this.jobRepository.createJob(crawlId, uri, type, startedAt, crawlStatus);
    }
}
