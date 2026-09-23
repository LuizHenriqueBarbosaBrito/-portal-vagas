// Lógica do formulário de cadastro de currículo.

const formCurriculo = document.getElementById('form-curriculo');
const alertaSucesso = document.getElementById('alerta-sucesso');
const alertaErro = document.getElementById('alerta-erro');

formCurriculo.addEventListener('submit', async (evento) => {
  evento.preventDefault();
  alertaSucesso.classList.remove('visivel');
  alertaErro.classList.remove('visivel');

  const dados = {
    nomeCompleto: document.getElementById('nomeCompleto').value.trim(),
    telefone: document.getElementById('telefone').value.trim(),
    email: document.getElementById('email').value.trim(),
    areaInteresse: document.getElementById('areaInteresse').value,
    escolaridade: document.getElementById('escolaridade').value,
    experiencia: document.getElementById('experiencia').value.trim()
  };

  try {
    await apiPost('/curriculos', dados);
    alertaSucesso.classList.add('visivel');
    formCurriculo.reset();
    alertaSucesso.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } catch (erro) {
    alertaErro.textContent = erro.message || 'Não foi possível concluir o cadastro. Confira os campos e tente novamente.';
    alertaErro.classList.add('visivel');
    console.error(erro);
  }
});
