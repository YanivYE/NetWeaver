package com.NetWeaver.Sink;

import com.NetWeaver.Core.Entities.PageData;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSink implements PageSink {

    @Override
    public void handle(PageData pageData) {

    }

    @Override
    public void close() {

    }
}
