const modal = document.querySelector('#employeeModal');
const sidebar = document.querySelector('#sidebar');

function openModal() {
    modal.hidden = false;
    document.body.classList.add('modal-open');
    modal.querySelector('input')?.focus();
}

function closeModal() {
    modal.hidden = true;
    document.body.classList.remove('modal-open');
}

document.querySelectorAll('[data-open-modal]').forEach(button => button.addEventListener('click', openModal));
document.querySelectorAll('[data-close-modal]').forEach(button => button.addEventListener('click', closeModal));
modal.addEventListener('click', event => { if (event.target === modal) closeModal(); });
document.addEventListener('keydown', event => { if (event.key === 'Escape' && !modal.hidden) closeModal(); });
document.querySelector('#menuToggle')?.addEventListener('click', event => {
    const open = sidebar.classList.toggle('open');
    event.currentTarget.setAttribute('aria-expanded', String(open));
});
document.querySelectorAll('.sidebar a').forEach(link => link.addEventListener('click', () => sidebar.classList.remove('open')));

if (document.body.dataset.openModal === 'true') openModal();
