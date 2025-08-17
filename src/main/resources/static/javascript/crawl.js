document.addEventListener("DOMContentLoaded", () => {
  initializeFormSubmission();
});

// --- Helpers ---
const getValue = (el, parser = (v) => v.trim()) =>
  el && el.value ? parser(el.value) : undefined;

const getCheckedValues = (selector) =>
  Array.from(document.querySelectorAll(selector + ":checked")).map(
    (el) => el.value
  );

// --- Form Initialization ---
const initializeFormSubmission = () => {
  const form = document.getElementById("crawlerForm");
  const responseDiv = document.getElementById("response");

  const elements = {
    startUrl: document.getElementById("startUrl"),
    maxDepth: document.getElementById("maxDepth"),
    maxPages: document.getElementById("maxPages"),
    timeLimit: document.getElementById("timeLimit"),
    restrictions: document.getElementById("domainRestrictions"),
    userAgent: document.getElementById("userAgent"),
    modeType: document.getElementById("mode"),

    // Search mode
    searchKeyword: document.getElementById("searchKeyword"),
    matchStrategy: document.getElementById("matchStrategy"),
    minRelevance: document.getElementById("minRelevance"),

    // Correlation mode
    targetUrl: document.getElementById("targetUrl"),
    graphType: document.getElementById("graphType"),

    // Sitemap mode
    resolveRedirects: document.getElementById("resolveRedirects"),
    includeBrokenLinks: document.getElementById("includeBrokenLinks"),
    outputFormat: document.getElementById("outputFormat"),
  };

  form.addEventListener("submit", (e) => {
    e.preventDefault();

    // --- Shared fields ---
    const payload = {
      start_url: getValue(elements.startUrl) ?? undefined,
      max_depth: elements.maxDepth.value
        ? parseInt(elements.maxDepth.value, 10)
        : undefined,
      max_pages: elements.maxPages.value
        ? parseInt(elements.maxPages.value, 10)
        : undefined,
      time_limit: elements.timeLimit.value
        ? parseInt(elements.timeLimit.value, 10)
        : undefined,
      restrictions: elements.restrictions.value
        ? elements.restrictions.value
            .split(",")
            .map((r) => r.trim())
            .filter(Boolean)
        : undefined,
      user_agent: elements.userAgent.value
        ? elements.userAgent.value.trim()
        : undefined,
    };

    // --- Mode-specific fields ---
    const modeType = elements.modeType.value;
    let mode = { type: modeType };

    switch (modeType) {
      case "search":
        mode.search_keyword = getValue(elements.searchKeyword) ?? undefined;
        mode.search_fields = getCheckedValues(
          "#search-fields input[name='search_fields']"
        );
        mode.match_strategy = getValue(elements.matchStrategy) ?? undefined;
        mode.min_relevance = elements.minRelevance.value
          ? parseFloat(elements.minRelevance.value)
          : undefined;
        break;

      case "correlation":
        mode.target_url = getValue(elements.targetUrl) ?? undefined;
        mode.graph_type = getValue(elements.graphType) ?? undefined;
        mode.link_types = getCheckedValues(
          "#correlation-fields input[name='link_types']"
        );
        break;

      case "sitemap":
        mode.include_assets = getCheckedValues(
          "#sitemap-fields input[name='include_assets']"
        );
        mode.resolve_redirects = elements.resolveRedirects.checked;
        mode.include_broken_links = elements.includeBrokenLinks.checked;
        mode.output_format = getValue(elements.outputFormat) ?? undefined;
        break;
    }

    payload.mode = mode;

    console.log("Payload to send:", payload);

    fetch("/crawl", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
        return res.json();
      })
      .then((data) => {
        responseDiv.innerHTML = `
          <div class="alert alert-success">
            <h5>✅ Crawler Started Successfully!</h5>
            <p><strong>Mode:</strong> ${data.mode?.type || "Unknown"}</p>
            <p><strong>Start URL:</strong> ${data.start_url || "Unknown"}</p>
            <p><strong>Status:</strong> ${data.status || "Running"}</p>
            ${
              data.message
                ? `<p><strong>Message:</strong> ${data.message}</p>`
                : ""
            }
            ${data.jobId ? `<p><strong>Job ID:</strong> ${data.jobId}</p>` : ""}
          </div>`;
      })
      .catch((err) => {
        console.error("Error:", err);
        responseDiv.innerHTML = `
          <div class="alert alert-danger">
            <h5>❌ Error Starting Crawler</h5>
            <p><strong>Error:</strong> ${err.message}</p>
            <p>Please check your configuration and try again.</p>
          </div>`;
      });
  });
};
