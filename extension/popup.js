chrome.tabs.query(
  { active: true, currentWindow: true },
  function (tabs) {

    const url = tabs[0].url;
    document.getElementById("url").textContent = url;

  }
);

// botón
document.getElementById("createBtn")
  .addEventListener("click", () => {

    const description =
      document.getElementById("description").value;

    const lessonLearned =
      document.getElementById("lessonLearned").value;

    console.log("Artifact data:");
    console.log(description);
    console.log(lessonLearned);
  });