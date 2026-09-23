// Lógica da página de edição de vaga: carrega os dados atuais e salva alterações.

const idVaga = new URLSearchParams(window.location.search).get('id');
const formEditar = document.getElementById('form-editar-vaga');
const alertaSucesso = document.getElementById('alerta-sucesso');
const alertaErro = document.getElementById('alerta-erro');

async function carregarVaga() {
  if (!idVaga) {
    alertaErro.textContent = 'Vaga não informada. Volte ao painel e tente novamente.';
    alertaErro.classList.add('visivel');
    formEditar.classList.add('oculto');
    return;
  }

  try {
    const vaga = await apiGet(`/vagas/${idVaga}`);
    document.getElementById('titulo').value = vaga.titulo;
    document.getElementById('empresa').value = vaga.empresa;
    document.getElementById('descricao').value = vaga.descricao;
    document.getElementById('categoria').value = vaga.categoria;
    document.getElementById('tipoContrato').value = vaga.tipoContrato;
    document.getElementById('localizacao').value = vaga.localizacao;
    document.getElementById('salario').value = vaga.salario || '';
    document.getElementById('contato').value = vaga.contato;
    document.getElementById('ativa').checked = vaga.ativa;
  } catch (erro) {
    alertaErro.textContent = 'Não foi possível carregar os dados da vaga.';
    alertaErro.classList.add('visivel');
    formEditar.classList.add('oculto');
    console.error(erro);
  }
}

formEditar.addEventListener('submit', async (evento) => {
  evento.preventDefault();
  alertaSucesso.classList.remove('visivel');
  alertaErro.classList.remove('visivel');

  const dados = {
    titulo: document.getElementById('titulo').value.trim(),
    empresa: document.getElementById('empresa').value.trim(),
    descricao: document.getElementById('descricao').value.trim(),
    categoria: document.getElementById('categoria').value,
    tipoContrato: document.getElementById('tipoContrato').value,
    localizacao: document.getElementById('localizacao').value.trim(),
    salario: document.getElementById('salario').value.trim(),
    contato: document.getElementById('contato').value.trim(),
    ativa: document.getElementById('ativa').checked
  };

  try {
    await apiPut(`/vagas/${idVaga}`, dados);
    alertaSucesso.classList.add('visivel');
    alertaSucesso.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } catch (erro) {
    alertaErro.textContent = erro.message || 'Não foi possível salvar as alterações.';
    alertaErro.classList.add('visivel');
    console.error(erro);
  }
});

carregarVaga();
