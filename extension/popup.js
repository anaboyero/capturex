const API_BASE_URL = "http://localhost:8080";
const GITHUB_BASE_REPO_REGEX = /^https:\/\/github\.com\/[^/]+\/[^/]+$/;

let currentUrl = "";

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
    throw new Error("Artifact could not be created");
  }
}

chrome.tabs.query({ active: true, currentWindow: true }, async function (tabs) {
  currentUrl = tabs[0].url;
  document.getElementById("url").textContent = currentUrl;

  if (!GITHUB_BASE_REPO_REGEX.test(currentUrl)) {
    return;
  }

  try {
    const proposedDescription = await requestProposal(currentUrl);
    document.getElementById("description").value = proposedDescription;
  } catch (error) {
    console.error("Could not request proposal", error);
  }
});

document.getElementById("createBtn").addEventListener("click", async () => {
  const description = document.getElementById("description").value;
  const lessonLearned = document.getElementById("lessonLearned").value;

  if (!description || !description.trim()) {
    console.error("Description is required to create a learning artifact");
    return;
  }

  try {
    await createArtifact(currentUrl, description, lessonLearned);
    console.log("Artifact created");
  } catch (error) {
    console.error(error.message);
  }
});
