package com.NetWeaver.Sink;
import com.NetWeaver.Core.Entities.PageData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySink implements PageSink {
    private final Map<String, PageData> pages = new ConcurrentHashMap<>();

    @Override
    public void handle(PageData pageData) {

    }

    @Override
    public void close() {

    }
}

