package com.NetWeaver.Core;

import com.NetWeaver.Core.Entities.FetchResult;
import com.NetWeaver.Core.Entities.PageData;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Parser {
    private final int maxTextChars;

    public Parser(int maxTextChars) {
        this.maxTextChars = maxTextChars;
    }

    public PageData parse(URI uri, FetchResult fetchResult) throws Exception {
        String ct = fetchResult.contentType() != null ? fetchResult.contentType().toLowerCase(Locale.ROOT) : "";
        byte[] body = fetchResult.body();

        if (isHtml(ct, body)) {
            // Let Jsoup detect charset from meta if needed (charsetName=null)
            Document doc = (Document) Jsoup.parse(new ByteArrayInputStream(body), null, uri.toString());

//            String title = Optional.ofNullable(doc.title()).orElse("");
//            String text = doc.body() != null ? doc.body().text() : "";
            String text = "";
            String title = "";

            if (text.length() > maxTextChars) text = text.substring(0, maxTextChars);

            // Collect links (HTTP/HTTPS only)
            List<URI> links = new ArrayList<>();
//            addLinks(links, doc, "a", "href", uri);
//            addLinks(links, doc, "img", "src", uri);
//            addLinks(links, doc, "link", "href", uri);
//            addLinks(links, doc, "script", "src", uri);

            return new PageData(uri, title, text, links, nonNullCt(ct, "text/html"));
        }

        if (ct.startsWith("text/plain") || ct.startsWith("application/xml") || ct.startsWith("text/xml")) {
            String text = new String(body, StandardCharsets.UTF_8);
            if (text.length() > maxTextChars) text = text.substring(0, maxTextChars);
            return new PageData(uri, "", text, List.of(), fetchResult.contentType());
        }

        // Unknown/binary types → return with no text/links (handlers can still record assets by content-type)
        return new PageData(uri, "", "", List.of(), fetchResult.contentType());
    }

    private boolean isHtml(String contentType, byte[] body) {
        if (contentType.startsWith("text/html") || contentType.startsWith("application/xhtml+xml")) return true;
        // Heuristic sniff for servers that omit content-type
        String head = new String(body, 0, Math.min(body.length, 256), StandardCharsets.ISO_8859_1).toLowerCase(Locale.ROOT);
        return head.contains("<html") || head.contains("<!doctype html") || head.contains("<head") || head.contains("<body");
    }

//    private void addLinks(List<URI> out, Document doc, String tag, String attr, URI base) {
//        for (Element e : doc.select(tag + "[" + attr + "]")) {
//            String abs = e.hasAttr("abs:" + attr) ? e.attr("abs:" + attr) : e.attr(attr);
//            if (abs == null || abs.isBlank()) continue;
//            try {
//                URI u = base.resolve(abs);
//                String scheme = u.getScheme();
//                if (scheme != null && (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
//                    out.add(u);
//                }
//            } catch (Exception ignored) {}
//        }
//    }

    private String nonNullCt(String ct, String fallback) {
        return (ct == null || ct.isBlank()) ? fallback : ct;
    }
}
