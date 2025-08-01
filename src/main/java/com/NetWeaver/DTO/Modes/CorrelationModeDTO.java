package com.NetWeaver.DTO.Modes;

import com.NetWeaver.Enums.CrawlMode;
import com.NetWeaver.Enums.GraphType;
import com.NetWeaver.Enums.LinkType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CorrelationModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.CORRELATION;

    @JsonProperty("target_url")
    private String targetUrl;

    @JsonProperty("graph_type")
    private GraphType graphType;

    @JsonProperty("link_types")
    private LinkType[] linkTypes;

    @Override
    public CrawlMode getType() {
        return type;
    }

    public GraphType getGraphType() {
        return this.graphType;
    }

    public LinkType[] getLinkTypes() {
        return this.linkTypes;
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }
}
