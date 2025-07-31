package com.NetWeaver.DTO;

import com.NetWeaver.CrawlMode;

public class SearchModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SEARCH;
    
    @Override
    public CrawlMode getType() {
        return null;
    }
}
