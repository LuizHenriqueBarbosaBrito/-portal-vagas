// Lógica do formulário de publicação de vaga.

const formVaga = document.getElementById('form-vaga');
const alertaSucesso = document.getElementById('alerta-sucesso');
const alertaErro = document.getElementById('alerta-erro');

formVaga.addEventListener('submit', async (evento) => {
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
    contato: document.getElementById('contato').value.trim()
  };

  try {
    await apiPost('/vagas', dados);
    alertaSucesso.classList.add('visivel');
    formVaga.reset();
    alertaSucesso.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } catch (erro) {
    alertaErro.textContent = erro.message || 'Não foi possível publicar a vaga. Confira os campos e tente novamente.';
    alertaErro.classList.add('visivel');
    console.error(erro);
  }
});
