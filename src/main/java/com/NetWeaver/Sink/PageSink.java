package com.NetWeaver.Sink;

import com.NetWeaver.Core.Entities.PageData;

public interface PageSink extends AutoCloseable {
    void handle(PageData pageData);
    @Override void close();
}