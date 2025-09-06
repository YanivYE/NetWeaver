package com.NetWeaver.Core.Entities;

import java.net.URI;

public class DepthUrl {
    public final URI url;
    public final int depth;

    public DepthUrl(URI url, int depth) {
        this.url = url;
        this.depth = depth;
    }
}
