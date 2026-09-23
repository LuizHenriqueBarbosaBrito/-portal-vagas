// Funções utilitárias para conversar com a API REST do backend.

const API_BASE = '/api';

async function apiGet(caminho) {
  const resposta = await fetch(`${API_BASE}${caminho}`);
  if (!resposta.ok) {
    throw new Error(`Erro ao buscar dados (status ${resposta.status})`);
  }
  return resposta.json();
}

async function apiPost(caminho, dados) {
  const resposta = await fetch(`${API_BASE}${caminho}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados)
  });

  if (!resposta.ok) {
    const corpo = await resposta.json().catch(() => ({}));
    const erro = new Error(corpo.mensagem || `Erro ao enviar dados (status ${resposta.status})`);
    erro.detalhes = corpo.erros;
    throw erro;
  }

  return resposta.json();
}

async function apiPut(caminho, dados) {
  const resposta = await fetch(`${API_BASE}${caminho}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dados)
  });

  if (!resposta.ok) {
    const corpo = await resposta.json().catch(() => ({}));
    const erro = new Error(corpo.mensagem || `Erro ao enviar dados (status ${resposta.status})`);
    erro.detalhes = corpo.erros;
    throw erro;
  }

  return resposta.json();
}

async function apiPatch(caminho) {
  const resposta = await fetch(`${API_BASE}${caminho}`, { method: 'PATCH' });

  if (!resposta.ok) {
    const corpo = await resposta.json().catch(() => ({}));
    throw new Error(corpo.mensagem || `Erro ao atualizar status (status ${resposta.status})`);
  }

  return resposta.json();
}

function escaparHtml(texto) {
  const div = document.createElement('div');
  div.textContent = texto ?? '';
  return div.innerHTML;
}
