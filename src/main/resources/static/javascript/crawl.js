document.addEventListener("DOMContentLoaded", function () {
  initializeFormSubmission();
});

// Form submission functionality
const initializeFormSubmission = () => {
  const crawlerForm = document.getElementById("crawlerForm");

  crawlerForm.addEventListener("submit", function (event) {
    event.preventDefault(); // stop default form POST

    // Shared fields
    const startUrl = document.getElementById("startUrl").value.trim();
    const depth = parseInt(document.getElementById("depth").value, 10);
    const maxPages = document.getElementById("maxPages").value
      ? parseInt(document.getElementById("maxPages").value, 10)
      : undefined;
    const timeLimit = document.getElementById("timeLimit").value
      ? parseInt(document.getElementById("timeLimit").value, 10)
      : undefined;
    const restrictions = document
      .getElementById("domainRestrictions")
      .value.split(",")
      .map((r) => r.trim())
      .filter((r) => r !== "");
    const userAgent = document.getElementById("userAgent").value.trim();

    // Mode type
    const modeType = document.getElementById("mode").value;

    // Mode-specific fields
    let modeData = { type: modeType };

    if (modeType === "search") {
      modeData.keyword = document.getElementById("searchKeyword").value.trim();
      modeData.search_fields = Array.from(
        document.querySelectorAll(
          "#search-fields input[name='searchFields']:checked"
        )
      ).map((el) => el.value);
      modeData.strategy = document.getElementById("matchStrategy").value;
      modeData.min_relevance = parseFloat(
        document.getElementById("minRelevance").value
      );
    }

    if (modeType === "correlation") {
      modeData.target_url = document.getElementById("targetUrl").value.trim();
      modeData.graph_type = document.getElementById("graphType").value;
      modeData.link_types = Array.from(
        document.querySelectorAll(
          "#correlation-fields input[name='linkTypes']:checked"
        )
      ).map((el) => el.value);
    }

    if (modeType === "sitemap") {
      modeData.include_assets = Array.from(
        document.querySelectorAll(
          "#sitemap-fields input[name='includeAssets']:checked"
        )
      ).map((el) => el.value);
      modeData.resolve_redirects =
        document.getElementById("resolveRedirects").checked;
      modeData.include_broken_links =
        document.getElementById("includeBrokenLinks").checked;
      modeData.output_format = document.getElementById("outputFormat").value;
    }

    // Final object
    const payload = {
      start_url: startUrl,
      depth: depth,
      ...(maxPages !== undefined && { max_pages: maxPages }),
      ...(timeLimit !== undefined && { time_limit: timeLimit }),
      ...(restrictions.length > 0 && { restrictions }),
      ...(userAgent && { user_agent: userAgent }),
      mode: modeData,
    };

    console.log("Payload to send:", payload);

    // Example sending JSON to backend
    fetch("/api/crawl", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        // Display success response
        responseDiv.innerHTML = `
          <div class="alert alert-success">
            <h5>✅ Crawler Started Successfully!</h5>
            <p><strong>Mode:</strong> ${data.mode || "Unknown"}</p>
            <p><strong>Start URL:</strong> ${data.startUrl || "Unknown"}</p>
            <p><strong>Status:</strong> ${data.status || "Running"}</p>
            ${
              data.message
                ? `<p><strong>Message:</strong> ${data.message}</p>`
                : ""
            }
            ${data.jobId ? `<p><strong>Job ID:</strong> ${data.jobId}</p>` : ""}
          </div>
        `;
      })
      .catch((error) => {
        console.error("Error:", error);
        // Display error response
        responseDiv.innerHTML = `
          <div class="alert alert-danger">
            <h5>❌ Error Starting Crawler</h5>
            <p><strong>Error:</strong> ${error.message}</p>
            <p>Please check your configuration and try again.</p>
          </div>
        `;
      });
  });
};
