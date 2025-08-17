package com.NetWeaver.DTO.Modes;

import com.NetWeaver.Enums.Asset;
import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.OutputFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SitemapModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.SITEMAP;

    @JsonProperty("include_assets")
    private Asset[] includeAssets;

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

    public Asset[] getIncludeAssets() {
        return this.includeAssets;
    }

    public boolean getResolveRedirects() {
        return this.resolveRedirects;
    }

    public boolean getIncludeBrokenLinks() {
        return this.includeBrokenLinks;
    }

    public OutputFormat getOutputFormat() {
        return this.outputFormat;
    }
}
