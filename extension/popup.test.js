const test = require("node:test");
const assert = require("node:assert/strict");
const fs = require("node:fs");
const path = require("node:path");
const vm = require("node:vm");

const POPUP_JS_PATH = path.join(__dirname, "popup.js");
const POPUP_JS_SOURCE = fs.readFileSync(POPUP_JS_PATH, "utf8");

function createElement() {
  return {
    value: "",
    textContent: "",
    listeners: {},
    addEventListener(event, handler) {
      this.listeners[event] = handler;
    }
  };
}

function createDocument() {
  const elements = {
    url: createElement(),
    description: createElement(),
    lessonLearned: createElement(),
    createBtn: createElement(),
    status: createElement()
  };

  return {
    getElementById(id) {
      const element = elements[id];
      if (!element) {
        throw new Error(`Unexpected element id: ${id}`);
      }
      return element;
    },
    elements
  };
}

async function runPopupScriptWithUrl(url) {
  const pendingTasks = [];
  const document = createDocument();
  const fetchCalls = [];

  const chrome = {
    tabs: {
      query(_queryInfo, callback) {
        const maybePromise = callback([{ url }]);
        if (maybePromise && typeof maybePromise.then === "function") {
          pendingTasks.push(maybePromise);
        }
      }
    }
  };

  const context = vm.createContext({
    chrome,
    document,
    fetch: (...args) => {
      fetchCalls.push(args);
      return Promise.resolve({
        ok: true,
        json: async () => ({ description: "ignored" })
      });
    },
    console
  });

  vm.runInContext(POPUP_JS_SOURCE, context, { filename: "popup.js" });
  await Promise.all(pendingTasks);

  return { document, fetchCalls };
}

async function runCreateFlow({ url, createResponseBody }) {
  const document = createDocument();
  const pendingTasks = [];

  const chrome = {
    tabs: {
      query(_queryInfo, callback) {
        const maybePromise = callback([{ url }]);
        if (maybePromise && typeof maybePromise.then === "function") {
          pendingTasks.push(maybePromise);
        }
      }
    }
  };

  const context = vm.createContext({
    chrome,
    document,
    fetch: () => Promise.resolve({
      ok: true,
      json: async () => createResponseBody
    }),
    console
  });

  vm.runInContext(POPUP_JS_SOURCE, context, { filename: "popup.js" });
  await Promise.all(pendingTasks);

  document.elements.url.value = "https://example.com/repo";
  document.elements.description.value = "Description";
  document.elements.lessonLearned.value = "Valuable lesson";

  await document.elements.createBtn.listeners.click();
  return document;
}

test("keeps description empty when active tab is not GitHub", async () => {
  const { document, fetchCalls } = await runPopupScriptWithUrl("https://example.com/docs");

  assert.equal(document.elements.description.value, "");
  assert.equal(fetchCalls.length, 0);
});

test("shows artifact id in success status after creating artifact", async () => {
  const document = await runCreateFlow({
    url: "https://example.com/docs",
    createResponseBody: { id: 42 }
  });

  assert.equal(document.elements.status.textContent, "Learning Artifact created successfully. ID: 42");
});
