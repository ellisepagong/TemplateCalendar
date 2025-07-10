function showElement(elem) {
  elem.style.display = "block"; 
  void elem.offsetWidth;
  elem.classList.remove("hidden");
  elem.classList.add("visible");
}

function hideElement(elem) {
  elem.classList.remove("visible");
  elem.classList.add("hidden");
  elem.addEventListener("transitionend", function handler() {
    elem.style.display = "none";
    elem.removeEventListener("transitionend", handler);
  });
}

document.getElementById("to-register").addEventListener("click", () => {
  hideElement(document.getElementById("login-card"));
  showElement(document.getElementById("register-card"));
});

document.getElementById("to-login").addEventListener("click", () => {
  hideElement(document.getElementById("register-card"));
  showElement(document.getElementById("login-card"));
});

document.getElementById("login").addEventListener("click", ()=>{
  // TODO: add authentication
    window.location.href = "dashboard.html";
});
