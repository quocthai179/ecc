// app/app.js
var numStars = 1e3;
for (let i = 0; i < numStars; i++) {
  const star = document.createElement("div");
  star.className = "star";
  const xy = getRandomPosition();
  star.style.left = xy[0] + "px";
  star.style.bottom = xy[1] + "px";
  document.body.append(star);
}
function getRandomPosition() {
  const width = window.innerWidth;
  const height = window.innerHeight;
  const randomX = Math.floor(Math.random() * width);
  const randomY = Math.floor(Math.random() * height);
  return [randomX, randomY];
}

// live-reload.js
var isReco = false;
console.log("web-bundler live-reload is enabled");
function connectToChanges() {
  console.debug("connecting to web-bundler live-reload: " + isReco);
  const eventSource = new EventSource("/web-bundler/live");
  eventSource.onopen = () => {
    if (isReco) {
      location.reload();
    }
    console.debug("connected to web-bundler live-reload");
  };
  eventSource.addEventListener("bundling-error", (e) => {
    eventSource.close();
    location.reload();
  });
  eventSource.addEventListener("change", (e) => {
    if (!e.data) {
      return;
    }
    const { added, removed, updated } = JSON.parse(e.data);
    const updatedCss = updated.filter((p) => p.endsWith(".css")).length;
    if (!added.length && !removed.length && updated.length > 0 && updatedCss === updated.length) {
      for (const link of document.getElementsByTagName("link")) {
        const url = new URL(link.href);
        for (const css of updated)
          if (url.host === location.host && url.pathname === css) {
            console.log("Live-reload: " + css);
            const next = link.cloneNode();
            next.href = css + "?" + Math.random().toString(36).slice(2);
            next.onload = () => link.remove();
            next.onerror = (e2) => {
              next.remove();
              console.error(e2);
            };
            link.parentNode.insertBefore(next, link.nextSibling);
            return;
          }
      }
    }
    location.reload();
  });
  eventSource.onerror = (e) => {
    console.debug("web-bundler live-reload connection lost");
    isReco = true;
  };
}
fetch("/web-bundler/live").then((response) => {
  if (response.status === 429) {
    return Promise.reject(new Error("There are too many live-reload open connections."));
  }
  return response;
}).then(connectToChanges).catch((error) => {
  console.error("Error:", error.message);
});
//# sourceMappingURL=/static/bundle/main.js.map
