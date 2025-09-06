package com.NetWeaver.Core.Entities;

public class FetchResult {
    private final int status;
    private final String contentType;
    private final byte[] body;

    public FetchResult(int status, String contentType, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public boolean ok() { return status >= 200 && status < 300 && body != null; }
    public int status() { return status; }
    public String contentType() { return contentType; }
    public byte[] body() { return body; }
}
