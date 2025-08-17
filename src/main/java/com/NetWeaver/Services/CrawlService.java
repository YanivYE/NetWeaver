package com.NetWeaver.Services;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlRequest;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Enums.CrawlStatus;
import com.NetWeaver.Engine.CrawlEngine;
import com.NetWeaver.Handlers.ModeHandlerFactory;
import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Sink.PageSink;
import com.NetWeaver.Sink.PageSinkFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class CrawlService {

    private final ExecutorService executor;
    private final CrawlEngine crawlEngine;
    private final PageSinkFactory sinkFactory;
    private final ModeHandlerFactory handlerFactory;
    private final JobService jobService;

    @Autowired
    public CrawlService(
            ExecutorService executor,
            CrawlEngine crawlEngine,
            PageSinkFactory sinkFactory,
            ModeHandlerFactory handlerFactory,
            JobService jobService
    ) {
        this.executor = executor;
        this.crawlEngine = crawlEngine;
        this.sinkFactory = sinkFactory;
        this.handlerFactory = handlerFactory;
        this.jobService = jobService;
    }

    public CrawlResult start(CrawlRequest req) {
        UUID crawlId = UUID.randomUUID();
        Instant startedAt = Instant.now();

        jobService.createJob(crawlId, req.startUrl(), req.mode().type(), startedAt, CrawlStatus.QUEUED);

        ModeHandler handler = handlerFactory.getHandler(req.mode().type(), req.mode());
        CrawlContext context = mapContext(req, handler);

        executor.submit(() -> {
            jobService.updateStatus(crawlId, CrawlStatus.RUNNING);
            try (PageSink sink = sinkFactory.createSink(crawlId)) {
                crawlEngine.run(context.withSink(sink));
                jobService.updateStatus(crawlId, CrawlStatus.COMPLETED);
            } catch (Exception e) {
                e.printStackTrace();
                jobService.markFailed(crawlId, e.getMessage());
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

    private CrawlContext mapContext(CrawlRequest req, ModeHandler handler) {
        return new CrawlContext(
                req.startUrl(),
                req.depth(),
                req.pages(),
                req.timeLimit(),
                req.userAgent(),
                handler,
                null
        );
    }

}
