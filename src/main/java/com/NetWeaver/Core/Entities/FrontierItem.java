package com.NetWeaver.Core.Entities;

import java.net.URI;

public class FrontierItem {
    private final URI uri;
    private final int depth;
    private final double priority;

    public FrontierItem(URI uri, int depth, double priority) {
        this.uri = uri;
        this.depth = depth;
        this.priority = priority;
    }
    public URI uri() { return uri; }
    public int depth() { return depth; }
    public double priority() { return priority; }
}