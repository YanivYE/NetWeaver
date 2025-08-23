package com.NetWeaver.Sink;

import com.NetWeaver.Models.PageData;

public interface PageSink extends AutoCloseable {
    void handle(PageData pageData);
    @Override void close();
}