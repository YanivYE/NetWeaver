package com.NetWeaver.DTO.Modes;

import com.NetWeaver.Enums.CrawlMode;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SearchModeDTO.class, name = "search"),
        @JsonSubTypes.Type(value = CorrelationModeDTO.class, name = "correlation"),
        @JsonSubTypes.Type(value = SitemapModeDTO.class, name = "sitemap")
})
public interface CrawlModeDTO {
    CrawlMode getType();
}
