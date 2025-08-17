package com.NetWeaver.DTO.Modes;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.GraphType;
import com.NetWeaver.Enums.LinkType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.List;

public class CorrelationModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.CORRELATION;

    @JsonProperty("target_url")
    private URI targetUrl;

    @JsonProperty("graph_type")
    private GraphType graphType;

    @JsonProperty("link_types")
    private List<LinkType> linkTypes;

    @Override
    public CrawlMode type() {
        return type;
    }

    public GraphType graphType() {
        return this.graphType;
    }

    public List<LinkType> linkTypes() {
        return this.linkTypes;
    }

    public URI targetUrl() {
        return this.targetUrl;
    }
}
