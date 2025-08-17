package com.NetWeaver.Engine;

import com.NetWeaver.Context.CrawlContext;
import com.NetWeaver.Models.CrawlResult;

public interface CrawlEngine {
    CrawlResult run(CrawlContext context) throws Exception;
}
