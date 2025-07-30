// Theme toggle functionality
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

// Clear form functionality
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

// Mode change functionality
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
