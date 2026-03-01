const API_BASE_URL = "http://localhost:8080";
const GITHUB_BASE_REPO_REGEX = /^https:\/\/github\.com\/[^/]+\/[^/]+$/;

let currentUrl = "";

function setStatus(message, kind = "") {
  const statusEl = document.getElementById("status");
  statusEl.textContent = message;
  statusEl.className = kind ? kind : "";
}

async function requestProposal(projectUrl) {
  const response = await fetch(`${API_BASE_URL}/learning-artifacts/proposal`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ projectUrl })
  });

  if (!response.ok) {
    return "";
  }

  const proposal = await response.json();
  return proposal.description || "";
}

async function createArtifact(projectUrl, description, lessonLearned) {
  const response = await fetch(`${API_BASE_URL}/learning-artifacts`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ projectUrl, description, lessonLearned })
  });

  if (!response.ok) {
    let errorMessage = "Artifact could not be created";
    try {
      const body = await response.json();
      if (body && body.message) {
        errorMessage = body.message;
      }
    } catch (_ignored) {
      // Keep generic error message when API does not return JSON.
    }
    throw new Error(errorMessage);
  }

  try {
    const artifact = await response.json();
    return artifact && artifact.id != null ? artifact.id : null;
  } catch (_ignored) {
    return null;
  }
}

chrome.tabs.query({ active: true, currentWindow: true }, async function (tabs) {
  currentUrl = tabs[0].url;
  const urlField = document.getElementById("url");
  urlField.value = currentUrl;
  const descriptionField = document.getElementById("description");
  descriptionField.value = "";

  if (!GITHUB_BASE_REPO_REGEX.test(currentUrl)) {
    return;
  }

  try {
    const proposedDescription = await requestProposal(currentUrl);
    descriptionField.value = proposedDescription || "";
  } catch (error) {
    console.error("Could not request proposal", error);
    descriptionField.value = "";
  }
});

document.getElementById("createBtn").addEventListener("click", async () => {
  const createBtn = document.getElementById("createBtn");
  const defaultButtonLabel = "Create Artifact";
  const projectUrl = document.getElementById("url").value.trim();
  const description = document.getElementById("description").value.trim();
  const lessonLearned = document.getElementById("lessonLearned").value.trim();

  setStatus("Validating fields...", "info");

  if (!projectUrl) {
    setStatus("Project URL is required.", "error");
    return;
  }

  if (!description) {
    setStatus("Description is required.", "error");
    return;
  }

  if (!lessonLearned) {
    setStatus("Lesson Learned is required.", "error");
    return;
  }

  try {
    createBtn.disabled = true;
    createBtn.textContent = "Creating...";
    setStatus("Creating artifact...", "info");
    const artifactId = await createArtifact(projectUrl, description, lessonLearned);
    if (artifactId != null) {
      setStatus(`Learning Artifact created successfully. ID: ${artifactId}`, "success");
    } else {
      setStatus("Learning Artifact created successfully.", "success");
    }
  } catch (error) {
    setStatus(error.message, "error");
  } finally {
    createBtn.disabled = false;
    createBtn.textContent = defaultButtonLabel;
  }
});
