package com.NetWeaver.Sink;

import com.NetWeaver.Enums.CrawlMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PageSinkFactory {
    @Autowired
    private InMemorySink inMemorySink;

    @Autowired
    private DatabaseSink databaseSink;

    @Autowired
    private FileSink fileSink;

    public PageSink createSink(UUID jobId, CrawlMode mode) {
        List<PageSink> sinks = new ArrayList<>();

        // Always include In-Memory
        sinks.add(inMemorySink);

        // Mode-specific sinks
        switch (mode) {
            case SEARCH, CORRELATION -> sinks.add(databaseSink);
            case SITEMAP -> sinks.add(fileSink); // only Sitemap gets a file sink
        }

        return new CompositeSink(sinks);
    }

}
