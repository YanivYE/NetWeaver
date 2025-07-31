document.addEventListener("DOMContentLoaded", function () {
  initializeFormSubmission();
});

// Form submission functionality
const initializeFormSubmission = () => {
  const crawlerForm = document.getElementById("crawlerForm");
  const responseDiv = document.getElementById("response");

  // TODO: send crawl POST and and display result based on chosen mode
  crawlerForm.addEventListener("submit", function (e) {
    e.preventDefault();

    // Show loading state
    responseDiv.innerHTML = `
        <div class="text-center">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Loading...</span>
          </div>
          <p class="mt-2">Initializing crawler...</p>
        </div>
      `;

    // Collect form data
    const formData = new FormData(crawlerForm);
    const formObject = {};

    // Convert FormData to object
    for (let [key, value] of formData.entries()) {
      // Handle multiple values for checkboxes
      if (formObject[key]) {
        if (Array.isArray(formObject[key])) {
          formObject[key].push(value);
        } else {
          formObject[key] = [formObject[key], value];
        }
      } else {
        formObject[key] = value;
      }
    }

    // Send data to server
    fetch("/crawl", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formObject),
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
