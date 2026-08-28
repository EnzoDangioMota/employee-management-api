const modal = document.querySelector("#candidateModal");
const form = document.querySelector("#candidateForm");
const feedback = document.querySelector("#formFeedback");
const sidebar = document.querySelector("#sidebar");
const menuToggle = document.querySelector("#menuToggle");

const openModal = () => {
    modal.hidden = false;
    document.body.classList.add("modal-open");
    modal.querySelector("input").focus();
};

const closeModal = () => {
    modal.hidden = true;
    document.body.classList.remove("modal-open");
    feedback.textContent = "";
};

document.querySelectorAll("[data-open-modal]").forEach((button) => {
    button.addEventListener("click", openModal);
});

document.querySelectorAll("[data-close-modal]").forEach((button) => {
    button.addEventListener("click", closeModal);
});

modal.addEventListener("click", (event) => {
    if (event.target === modal) {
        closeModal();
    }
});

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && !modal.hidden) {
        closeModal();
    }
});

form.addEventListener("submit", (event) => {
    event.preventDefault();
    feedback.textContent = "Formulário pronto para integração com o endpoint POST /funcionarios.";
});

menuToggle.addEventListener("click", () => {
    const isOpen = sidebar.classList.toggle("open");
    menuToggle.setAttribute("aria-expanded", String(isOpen));
});

document.querySelectorAll(".sidebar a").forEach((link) => {
    link.addEventListener("click", () => {
        sidebar.classList.remove("open");
        menuToggle.setAttribute("aria-expanded", "false");
    });
});
