package com.NetWeaver.Models.DTO;

import com.NetWeaver.Enums.Asset;
import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.OutputFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SitemapModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SITEMAP;

    @JsonProperty("include_assets")
    private List<Asset> includeAssets;

    @JsonProperty("resolve_redirects")
    private boolean resolveRedirects;

    @JsonProperty("include_broken_links")
    private boolean includeBrokenLinks;

    @JsonProperty("output_format")
    private OutputFormat outputFormat;

    @Override
    public CrawlMode getType() {
        return type;
    }

    public List<Asset> includeAssets() {
        return this.includeAssets;
    }

    public boolean resolveRedirects() {
        return this.resolveRedirects;
    }

    public boolean includeBrokenLinks() {
        return this.includeBrokenLinks;
    }

    public OutputFormat outputFormat() {
        return this.outputFormat;
    }
}
