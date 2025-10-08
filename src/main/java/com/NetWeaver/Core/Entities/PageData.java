// com/NetWeaver/Handlers/ParsedPage.java
package com.NetWeaver.Core.Entities;

import java.net.URI;
import java.util.List;

/** Replace with your real ParsedPage if you already have it. */
public class PageData {
    private final URI uri;
    private final String title;
    private final String textContent; // normalized text
    private final List<URI> links;    // discovered links
    private final String contentType; // "text/html", "image/png", etc.

    public PageData(URI uri, String title, String textContent, List<URI> links, String contentType) {
        this.uri = uri;
        this.title = title;
        this.textContent = textContent;
        this.links = links;
        this.contentType = contentType;
    }

    public URI uri() { return uri; }
    public String title() { return title; }
    public String text() { return textContent; }
    public List<URI> links() { return links; }
    public String contentType() { return contentType; }
}
