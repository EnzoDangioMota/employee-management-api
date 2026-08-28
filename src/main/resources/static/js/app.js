const form=document.querySelector('#employeeForm'),feedback=document.querySelector('#formFeedback'),sidebar=document.querySelector('#sidebar');
form?.addEventListener('submit',e=>{e.preventDefault();feedback.textContent='Formulário pronto para integração com POST /funcionarios.'});
document.querySelector('#menuToggle')?.addEventListener('click',()=>sidebar.classList.toggle('open'));
document.querySelectorAll('.sidebar a').forEach(a=>a.addEventListener('click',()=>sidebar.classList.remove('open')));
