package com.NetWeaver.Services;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Handlers.ModeHandler;
import com.NetWeaver.Models.CrawlRequest;
import com.NetWeaver.Models.CrawlResult;
import com.NetWeaver.Enums.CrawlStatus;
import com.NetWeaver.Engine.CrawlEngine;
import com.NetWeaver.Handlers.ModeHandlerFactory;
import com.NetWeaver.Sink.PageSink;
import com.NetWeaver.Sink.PageSinkFactory;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class CrawlService {

    @Autowired
    private JobService jobService;

    private ExecutorService executor;
    private CrawlEngine crawlEngine;
    private PageSinkFactory sinkFactory;
    private ModeHandlerFactory handlerFactory;

    public CrawlResult start(CrawlRequest req) {
        UUID crawlId = UUID.randomUUID();
        Instant startedAt = Instant.now();

        jobService.createJob(crawlId, req.startUrl(), req.mode().type(), startedAt, CrawlStatus.QUEUED);

        ModeHandler handler = handlerFactory.getHandler(req.mode());

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
