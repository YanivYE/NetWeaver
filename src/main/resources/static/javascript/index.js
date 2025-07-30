// Wait for DOM to be fully loaded before initializing
document.addEventListener("DOMContentLoaded", function () {
  // Initialize theme system
  initializeTheme();

  // Initialize form functionality
  initializeForm();

  // Initialize mode switching
  initializeModeSwitching();

  // Initialize form submission
  initializeFormSubmission();
});

// Theme toggle functionality
function initializeTheme() {
  const themeToggle = document.getElementById("themeToggle");
  const themeIcon = document.getElementById("themeIcon");
  const body = document.body;

  // Check for saved theme preference or default to dark
  const savedTheme = localStorage.getItem("theme") || "dark";
  if (savedTheme === "light") {
    body.classList.add("light-mode");
    themeIcon.className = "bi bi-sun-fill";
  }

  themeToggle.addEventListener("click", function () {
    body.classList.toggle("light-mode");

    if (body.classList.contains("light-mode")) {
      themeIcon.className = "bi bi-sun-fill";
      localStorage.setItem("theme", "light");
    } else {
      themeIcon.className = "bi bi-moon-fill";
      localStorage.setItem("theme", "dark");
    }
  });
}

// Clear form functionality
function initializeForm() {
  const clearForm = document.getElementById("clearForm");
  const crawlerForm = document.getElementById("crawlerForm");

  clearForm.addEventListener("click", function () {
    if (confirm("Are you sure you want to clear all form fields?")) {
      crawlerForm.reset();

      // Hide all mode-specific fields
      document.querySelectorAll(".mode-specific").forEach((div) => {
        div.style.display = "none";
      });

      // Reset mode dropdown to placeholder
      document.getElementById("mode").selectedIndex = 0;
    }
  });
}

// Mode change functionality
function initializeModeSwitching() {
  document.getElementById("mode").addEventListener("change", function () {
    // Hide all mode-specific fields
    document.querySelectorAll(".mode-specific").forEach((div) => {
      div.style.display = "none";
    });

    // Show the relevant fields based on selected mode
    const mode = this.value;
    if (mode === "search") {
      document.getElementById("search-fields").style.display = "block";
    } else if (mode === "correlation") {
      document.getElementById("correlation-fields").style.display = "block";
    } else if (mode === "sitemap") {
      document.getElementById("sitemap-fields").style.display = "block";
    }
  });
}

// Form submission functionality
function initializeFormSubmission() {
  const crawlerForm = document.getElementById("crawlerForm");
  const responseDiv = document.getElementById("response");

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
}
