package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CorrelationModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.CORRELATION;

    @JsonProperty("target_url")
    private String targetUrl;

    @JsonProperty("graph_type")
    private String graphType;

    @JsonProperty("link_types")
    private String[] linkTypes;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
