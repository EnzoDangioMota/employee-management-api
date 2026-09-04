const api = '/funcionarios';
const state = { funcionarios: [], editando: null };
const $ = (selector) => document.querySelector(selector);

async function request(url, options = {}) {
  const response = await fetch(url, { headers: { 'Content-Type': 'application/json' }, ...options });
  if (!response.ok) {
    const body = await response.json().catch(() => ({}));
    throw new Error(body.mensagem || 'Não foi possível concluir a operação.');
  }
  return response.status === 204 ? null : response.json();
}

function message(text, type = 'success') { const feedback = $('#feedback'); feedback.textContent = text; feedback.className = `feedback ${type}`; }
function label(status) { return (status || 'NÃO INFORMADO').replaceAll('_', ' '); }
function values(form) { const data = Object.fromEntries(new FormData(form)); return Object.fromEntries(Object.entries(data).map(([key, value]) => [key, value === '' ? null : value])); }

function render() {
  const term = $('#search').value.trim().toLowerCase(); const status = $('#statusFilter').value;
  const filtered = state.funcionarios.filter(f => !term || [f.nome, f.cargo, f.status].some(v => (v || '').toLowerCase().includes(term))).filter(f => !status || f.status === status);
  $('#employees').innerHTML = filtered.map(f => `<tr><td>${f.id}</td><td><b>${f.nome}</b><br><small>${f.email}</small></td><td>${f.cargo}</td><td>${f.departamento || '—'}</td><td>${f.cidade || '—'}</td><td><span class="status">${label(f.status)}</span></td><td class="actions"><button data-edit="${f.id}">Editar</button><button class="delete" data-delete="${f.id}">Excluir</button></td></tr>`).join('');
  $('#empty').hidden = filtered.length !== 0; $('#resultCount').textContent = `${filtered.length} candidato(s) encontrado(s)`;
  const count = statusName => state.funcionarios.filter(f => f.status === statusName).length;
  $('#total').textContent = state.funcionarios.length; $('#emAnalise').textContent = count('EM_ANALISE'); $('#aprovados').textContent = count('APROVADO'); $('#contratados').textContent = count('CONTRATADO');
}

async function load() { state.funcionarios = await request(api); render(); }
function edit(id) { const f = state.funcionarios.find(item => item.id === id); if (!f) return; state.editando = id; for (const [key, value] of Object.entries(f)) { const field = $(`#employeeForm [name="${key}"]`); if (field) field.value = value ?? ''; } $('#employeeForm [name="id"]').readOnly = true; $('#formTitle').textContent = `Editar candidato #${id}`; $('#formMode').textContent = 'PUT · ATUALIZAÇÃO COMPLETA'; $('#saveButton').textContent = 'Salvar alterações (PUT)'; $('#cancelEdit').hidden = false; $('#employeeForm').scrollIntoView({ behavior: 'smooth' }); }
function clearEdit() { state.editando = null; $('#employeeForm').reset(); $('#employeeForm [name="id"]').readOnly = false; $('#formTitle').textContent = 'Cadastrar candidato'; $('#formMode').textContent = 'POST · NOVO CADASTRO'; $('#saveButton').textContent = 'Cadastrar (POST)'; $('#cancelEdit').hidden = true; }

$('#employeeForm').addEventListener('submit', async event => { event.preventDefault(); try { const payload = values(event.currentTarget); await request(state.editando ? `${api}/${state.editando}` : api, { method: state.editando ? 'PUT' : 'POST', body: JSON.stringify(payload) }); message(state.editando ? 'Cadastro atualizado com PUT.' : 'Candidato cadastrado com POST.'); clearEdit(); await load(); } catch (error) { message(error.message, 'error'); } });
$('#patchForm').addEventListener('submit', async event => { event.preventDefault(); const payload = values(event.currentTarget); const id = payload.id; delete payload.id; Object.keys(payload).forEach(key => payload[key] === null && delete payload[key]); if (!Object.keys(payload).length) return message('Informe ao menos um campo para atualizar.', 'error'); try { await request(`${api}/${id}`, { method: 'PATCH', body: JSON.stringify(payload) }); message('Cadastro atualizado parcialmente com PATCH.'); event.currentTarget.reset(); await load(); } catch (error) { message(error.message, 'error'); } });
$('#employees').addEventListener('click', async event => { const id = Number(event.target.dataset.edit || event.target.dataset.delete); if (event.target.dataset.edit) return edit(id); if (event.target.dataset.delete && confirm('Deseja excluir este candidato?')) { try { await request(`${api}/${id}`, { method: 'DELETE' }); message('Candidato excluído com DELETE.'); await load(); } catch (error) { message(error.message, 'error'); } } });
$('#search').addEventListener('input', render); $('#statusFilter').addEventListener('change', render); $('#newButton').addEventListener('click', () => { clearEdit(); $('#employeeForm').scrollIntoView({ behavior: 'smooth' }); }); $('#cancelEdit').addEventListener('click', clearEdit);
$('#searchId').addEventListener('click', async () => { const id = $('#idLookup').value; if (!id) return message('Informe um ID para consultar.', 'error'); try { const f = await request(`${api}/${id}`); state.funcionarios = [f]; $('#search').value = ''; $('#statusFilter').value = ''; render(); message(`Consulta GET concluída para o candidato #${id}.`); } catch (error) { message(error.message, 'error'); } });
load().catch(error => message(error.message, 'error'));
