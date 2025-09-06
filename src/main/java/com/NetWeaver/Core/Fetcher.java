package com.NetWeaver.Core;

public class Fetcher {
    private final HttpClient client;
    private final Duration requestTimeout;
    private final int maxBytes;

    public HttpFetcher(Duration connectTimeout, Duration requestTimeout, boolean followRedirects, int maxBytes) {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(followRedirects ? HttpClient.Redirect.NORMAL : HttpClient.Redirect.NEVER)
                .connectTimeout(connectTimeout != null ? connectTimeout : Duration.ofSeconds(10))
                .build();
        this.requestTimeout = requestTimeout != null ? requestTimeout : Duration.ofSeconds(20);
        this.maxBytes = maxBytes > 0 ? maxBytes : 3 * 1024 * 1024; // 3MB cap
    }

    @Override
    public CrawlEngine.FetchResult fetch(URI uri, String userAgent) throws IOException {
        HttpRequest req = HttpRequest.newBuilder(uri)
                .timeout(requestTimeout)
                .GET()
                .header("User-Agent", userAgent != null ? userAgent : "NetWeaverBot/1.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate")
                .build();
        try {
            HttpResponse<byte[]> resp = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
            int status = resp.statusCode();
            byte[] body = resp.body();
            if (body == null) body = new byte[0];

            String contentType = resp.headers().firstValue("content-type").orElse(null);
            String contentEncoding = resp.headers().firstValue("content-encoding").orElse(null);

            // decompress if needed
            if (contentEncoding != null) {
                String enc = contentEncoding.toLowerCase(Locale.ROOT);
                if (enc.contains("gzip")) body = ungzip(body);
                else if (enc.contains("deflate")) body = inflate(body);
            }

            // size cap
            if (body.length > maxBytes) body = Arrays.copyOf(body, maxBytes);

            // normalize content-type media part (without charset)
            if (contentType != null) {
                int semi = contentType.indexOf(';');
                contentType = (semi > 0 ? contentType.substring(0, semi) : contentType).trim().toLowerCase(Locale.ROOT);
            }

            return new CrawlEngine.FetchResult(status, contentType, body);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while fetching " + uri, e);
        }
    }

    private byte[] ungzip(byte[] data) throws IOException {
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data));
             ByteArrayOutputStream out = new ByteArrayOutputStream(Math.min(data.length * 2, maxBytes))) {
            pump(gis, out, maxBytes);
            return out.toByteArray();
        }
    }

    private byte[] inflate(byte[] data) throws IOException {
        try (InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(data));
             ByteArrayOutputStream out = new ByteArrayOutputStream(Math.min(data.length * 2, maxBytes))) {
            pump(iis, out, maxBytes);
            return out.toByteArray();
        }
    }

    private void pump(InputStream in, OutputStream out, int limit) throws IOException {
        byte[] buf = new byte[8192];
        int total = 0, n;
        while ((n = in.read(buf)) != -1) {
            if (total + n > limit) {
                out.write(buf, 0, limit - total);
                break;
            }
            out.write(buf, 0, n);
            total += n;
        }
    }
}
