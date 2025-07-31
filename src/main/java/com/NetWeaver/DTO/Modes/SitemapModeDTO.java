package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;

public class SitemapModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SITEMAP;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
