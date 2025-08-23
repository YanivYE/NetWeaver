package com.NetWeaver.Sink;

import com.NetWeaver.Models.PageData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySink implements PageSink {
    private final Map<String, PageData> pages = new ConcurrentHashMap<>();

    @Override
    public void handle(PageData page) {
        pages.put(page.getUrl(), page);
    }

    public Map<String, PageData> getAllPages() {
        return pages;
    }
}

