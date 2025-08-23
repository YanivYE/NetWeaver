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
        CrawlContext context = mapContext(req, handler);

        executor.submit(() -> {
            jobService.updateStatus(crawlId, CrawlStatus.RUNNING);
            try (PageSink sink = sinkFactory.createSink(crawlId)) {
                crawlEngine.run(context.withSink(sink));
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
