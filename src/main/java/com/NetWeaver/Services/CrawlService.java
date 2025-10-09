package com.NetWeaver.Services;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Engine.CrawlEngine;
import com.NetWeaver.Enums.CrawlStatus;
import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Handlers.ModeHandlerFactory;
import com.NetWeaver.Models.CrawlRequest;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Sink.PageSink;
import com.NetWeaver.Sink.PageSinkFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CrawlService {

    private final JobService jobService;
    private final CrawlEngine crawlEngine;
    private final PageSinkFactory sinkFactory;
    private final ModeHandlerFactory handlerFactory;
    private final ExecutorService executor;

    @Autowired
    public CrawlService(
            JobService jobService,
            CrawlEngine crawlEngine,
            PageSinkFactory sinkFactory,
            ModeHandlerFactory handlerFactory
    ) {
        this.jobService = jobService;
        this.crawlEngine = crawlEngine;
        this.sinkFactory = sinkFactory;
        this.handlerFactory = handlerFactory;

        // You can configure thread pool size as needed (configurable via application.yml)
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public CrawlResult start(CrawlRequest req) {
        UUID crawlId = UUID.randomUUID();
        Instant startedAt = Instant.now();

        jobService.createJob(crawlId, req.startUrl(), req.mode().type(), startedAt, CrawlStatus.QUEUED);

        ModeHandler handler = handlerFactory.getHandler(req.mode().type());

        executor.submit(() -> {
            jobService.updateStatus(crawlId, CrawlStatus.RUNNING);
            try (PageSink sink = sinkFactory.createSink(crawlId, req.mode().type())) {
                crawlEngine.run(mapContext(crawlId, req, handler, sink));
                jobService.updateStatus(crawlId, CrawlStatus.COMPLETED);
            } catch (Exception e) {
                e.printStackTrace();
                jobService.updateStatus(crawlId, CrawlStatus.FAILED);
            }
        });

        return new CrawlResult(
                crawlId,
                CrawlStatus.QUEUED,
                0,
                0,
                "Crawl submitted successfully"
        );
    }

    private CrawlContext mapContext(UUID crawlId, CrawlRequest req, ModeHandler handler, PageSink sink) {
        return new CrawlContext(
                crawlId,
                req.startUrl(),
                req.depth(),
                req.pages(),
                req.timeLimit(),
                req.userAgent(),
                handler,
                sink
        );
    }
}
