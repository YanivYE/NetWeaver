package com.NetWeaver.Core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Locale;

public class UrlNormalizer {
    public String normalize(URI uri) {
        try {
            URI norm = new URI(
                    uri.getScheme() != null ? uri.getScheme().toLowerCase(Locale.ROOT) : "http",
                    uri.getUserInfo(),
                    uri.getHost() != null ? uri.getHost().toLowerCase(Locale.ROOT) : null,
                    portOrDefault(uri),
                    normalizePath(uri.getPath()),
                    normalizeQuery(uri.getQuery()),
                    null
            );
            return norm.toString();
        } catch (URISyntaxException e) {
            return uri.toString();
        }
    }
    private int portOrDefault(URI u) {
        if (u.getPort() == -1) return -1;
        if ("http".equalsIgnoreCase(u.getScheme()) && u.getPort() == 80) return -1;
        if ("https".equalsIgnoreCase(u.getScheme()) && u.getPort() == 443) return -1;
        return u.getPort();
    }
    private String normalizePath(String p) {
        if (p == null || p.isBlank()) return "/";
        String s = p.replaceAll("/\\./", "/").replaceAll("/{2,}", "/");
        return s;
    }
    private String normalizeQuery(String q) {
        if (q == null || q.isBlank()) return null;
        String[] parts = q.split("&");
        Arrays.sort(parts);
        return String.join("&", parts);
    }
}
