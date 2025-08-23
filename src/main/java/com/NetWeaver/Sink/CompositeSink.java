package com.NetWeaver.Sink;

import com.NetWeaver.Models.PageData;
import java.util.List;

public class CompositeSink implements PageSink {
    private final List<PageSink> sinks;

    public CompositeSink(List<PageSink> sinks) {
        this.sinks = sinks;
    }

    @Override
    public void handle(PageData data) {
        for (PageSink sink : sinks) {
            sink.handle(data);
        }
    }

    @Override
    public void close() {
        for (PageSink sink : sinks) {
            try {
                sink.close();
            } catch (Exception ignored) {}
        }
    }
}

