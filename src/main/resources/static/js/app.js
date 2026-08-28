const modal = document.querySelector('#employeeModal');
const sidebar = document.querySelector('#sidebar');

function openModal() {
    if (!modal) return;
    modal.hidden = false;
    document.body.classList.add('modal-open');
    modal.querySelector('input')?.focus();
}

function closeModal() {
    if (!modal) return;
    modal.hidden = true;
    document.body.classList.remove('modal-open');
}

document.addEventListener('click', event => {
    if (event.target.closest('[data-open-modal]')) openModal();
    if (event.target.closest('[data-close-modal]')) closeModal();
    if (event.target === modal) closeModal();
});
document.addEventListener('keydown', event => { if (event.key === 'Escape' && modal && !modal.hidden) closeModal(); });
document.querySelector('#menuToggle')?.addEventListener('click', event => {
    const open = sidebar.classList.toggle('open');
    event.currentTarget.setAttribute('aria-expanded', String(open));
});
document.querySelectorAll('.sidebar a').forEach(link => link.addEventListener('click', () => sidebar.classList.remove('open')));

if (document.body.dataset.openModal === 'true') openModal();
