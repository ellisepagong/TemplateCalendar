
document.getElementById("to-register").addEventListener("click", () => {
  document.getElementById("login-card").classList.add("hidden");
  document.getElementById("register-card").classList.remove("hidden")
  document.getElementById("register-card").classList.add("container-register");
});

document.getElementById("to-login").addEventListener("click", () => {
  document.getElementById("login-card").classList.remove("hidden");
  document.getElementById("register-card").classList.add("hidden")
});

document.getElementById("login").addEventListener("click", ()=>{
  // TODO: add authentication
    window.location.href = "dashboard.html";
});
