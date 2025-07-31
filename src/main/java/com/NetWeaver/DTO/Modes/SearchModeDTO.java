package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;

public class SearchModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SEARCH;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
