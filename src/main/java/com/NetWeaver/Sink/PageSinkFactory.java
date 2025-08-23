package com.NetWeaver.Sink;

import com.NetWeaver.Enums.CrawlMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PageSinkFactory {
    @Autowired
    private InMemorySink inMemorySink;

    @Autowired
    private DatabaseSink databaseSink;

    @Autowired
    private KafkaSink kafkaSink;

    @Autowired
    private FileSinkFactory fileSinkFactory; // fileSinkFactory.forJob(uuid)

    public PageSink createSink(UUID jobId, CrawlMode mode) {
        List<PageSink> sinks = new ArrayList<>();

        // Always include these
        sinks.add(databaseSink);

        // Mode-specific sinks
        switch (mode) {
            case SEARCH -> sinks.add(kafkaSink);
            case CORRELATION -> sinks.add(inMemorySink);
            case SITEMAP -> sinks.add(fileSinkFactory.forJob(jobId)); // only Sitemap gets a file sink
        }

        return new CompositeSink(sinks);
    }

}
