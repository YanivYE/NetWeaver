package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SitemapModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SITEMAP;

    @JsonProperty("format")
    private String outputFormat;

    @JsonProperty("include_assets")
    private String[] includeAssets;

    @JsonProperty("resolve_redirects")
    private boolean resolveRedirects;

    @JsonProperty("include_broken_links")
    private boolean includeBrokenLinks;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
