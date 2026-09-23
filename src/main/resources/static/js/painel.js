// Lógica do painel administrativo: listar todas as vagas e permitir editar/ativar/desativar.

const corpoTabela = document.getElementById('corpo-tabela');
const contadorPainel = document.getElementById('contador-painel');

function formatarData(dataIso) {
  if (!dataIso) return '';
  const [ano, mes, dia] = dataIso.split('-');
  return `${dia}/${mes}/${ano}`;
}

function criarLinha(vaga) {
  const linha = document.createElement('tr');

  linha.innerHTML = `
    <td>${escaparHtml(vaga.titulo)}</td>
    <td>${escaparHtml(vaga.empresa)}</td>
    <td>${escaparHtml(vaga.localizacao)}</td>
    <td><span class="badge ${vaga.ativa ? 'ativa' : 'inativa'}">${vaga.ativa ? 'Ativa' : 'Inativa'}</span></td>
    <td>${formatarData(vaga.dataPublicacao)}</td>
    <td>
      <div class="acoes-linha">
        <a class="botao botao-secundario botao-pequeno" href="editar-vaga.html?id=${vaga.id}">Editar</a>
        <button class="botao ${vaga.ativa ? 'botao-primario' : 'botao-sol'} botao-pequeno" data-id="${vaga.id}" data-ativa="${vaga.ativa}">
          ${vaga.ativa ? 'Desativar' : 'Reativar'}
        </button>
      </div>
    </td>
  `;

  linha.querySelector('button').addEventListener('click', alternarStatus);

  return linha;
}

async function alternarStatus(evento) {
  const botao = evento.currentTarget;
  const id = botao.dataset.id;
  const ativaAtual = botao.dataset.ativa === 'true';

  botao.disabled = true;
  try {
    await apiPatch(`/vagas/${id}/status?ativa=${!ativaAtual}`);
    carregarPainel();
  } catch (erro) {
    alert('Não foi possível atualizar o status da vaga. Tente novamente.');
    console.error(erro);
    botao.disabled = false;
  }
}

async function carregarPainel() {
  corpoTabela.innerHTML = '<tr><td colspan="6">Carregando vagas...</td></tr>';
  contadorPainel.textContent = '';

  try {
    const vagas = await apiGet('/vagas/todas');

    if (vagas.length === 0) {
      corpoTabela.innerHTML = '<tr><td colspan="6">Nenhuma vaga cadastrada ainda.</td></tr>';
      return;
    }

    contadorPainel.textContent = `${vagas.length} vaga${vagas.length > 1 ? 's' : ''} no total`;
    corpoTabela.innerHTML = '';
    vagas.forEach(vaga => corpoTabela.appendChild(criarLinha(vaga)));

  } catch (erro) {
    corpoTabela.innerHTML = '<tr><td colspan="6">Não foi possível carregar as vagas agora.</td></tr>';
    console.error(erro);
  }
}

carregarPainel();
