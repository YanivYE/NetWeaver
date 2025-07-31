package com.NetWeaver.DTO.Modes;

import com.NetWeaver.CrawlMode;
import com.NetWeaver.DTO.CrawlModeDTO;

public class CorrelationModeDTO implements CrawlModeDTO {
    private final CrawlMode type = CrawlMode.CORRELATION;

    @Override
    public CrawlMode getType() {
        return type;
    }
}
