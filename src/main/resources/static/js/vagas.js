// Lógica da página inicial: carregar, filtrar e exibir vagas.

const CATEGORIAS = [
  'Construção Civil', 'Serviços Gerais', 'Comércio', 'Agricultura',
  'Artesanato e Costura', 'Educação', 'Saúde e Cuidados', 'Transporte', 'Outros'
];

const listaVagasEl = document.getElementById('lista-vagas');
const contadorEl = document.getElementById('contador-resultados');
const campoBusca = document.getElementById('campo-busca');
const campoCategoria = document.getElementById('campo-categoria');
const campoLocalizacao = document.getElementById('campo-localizacao');
const botaoBuscar = document.getElementById('botao-buscar');

function preencherCategorias() {
  CATEGORIAS.forEach(categoria => {
    const opcao = document.createElement('option');
    opcao.value = categoria;
    opcao.textContent = categoria;
    campoCategoria.appendChild(opcao);
  });
}

function formatarData(dataIso) {
  if (!dataIso) return '';
  const [ano, mes, dia] = dataIso.split('-');
  return `${dia}/${mes}/${ano}`;
}

function criarCartaoVaga(vaga) {
  const cartao = document.createElement('article');
  cartao.className = 'cartao-vaga';

  cartao.innerHTML = `
    <div class="indicador" aria-hidden="true"></div>
    <div>
      <h3>${escaparHtml(vaga.titulo)}</h3>
      <p class="empresa">${escaparHtml(vaga.empresa)}</p>
      <p class="descricao">${escaparHtml(vaga.descricao)}</p>
      <div class="etiquetas">
        <span class="etiqueta">${escaparHtml(vaga.categoria)}</span>
        <span class="etiqueta local">📍 ${escaparHtml(vaga.localizacao)}</span>
        <span class="etiqueta">${escaparHtml(vaga.tipoContrato)}</span>
        ${vaga.salario ? `<span class="etiqueta">${escaparHtml(vaga.salario)}</span>` : ''}
      </div>
    </div>
    <div class="acao-vaga">
      <a class="botao botao-sol" href="tel:${escaparHtml(vaga.contato.replace(/\D/g, ''))}">Ligar / WhatsApp</a>
      <span class="contato">${escaparHtml(vaga.contato)}</span>
      <span class="contato">Publicada em ${formatarData(vaga.dataPublicacao)}</span>
    </div>
  `;

  return cartao;
}

async function carregarVagas() {
  listaVagasEl.innerHTML = '<p class="mensagem-vazia">Carregando vagas...</p>';
  contadorEl.textContent = '';

  const parametros = new URLSearchParams();
  if (campoBusca.value.trim()) parametros.set('busca', campoBusca.value.trim());
  if (campoCategoria.value) parametros.set('categoria', campoCategoria.value);
  if (campoLocalizacao.value.trim()) parametros.set('localizacao', campoLocalizacao.value.trim());

  try {
    const vagas = await apiGet(`/vagas?${parametros.toString()}`);

    listaVagasEl.innerHTML = '';

    if (vagas.length === 0) {
      listaVagasEl.innerHTML = '<p class="mensagem-vazia">Nenhuma vaga encontrada com esses filtros. Tente ampliar a busca.</p>';
      contadorEl.textContent = '';
      return;
    }

    contadorEl.textContent = `${vagas.length} vaga${vagas.length > 1 ? 's' : ''} encontrada${vagas.length > 1 ? 's' : ''}`;
    vagas.forEach(vaga => listaVagasEl.appendChild(criarCartaoVaga(vaga)));

  } catch (erro) {
    listaVagasEl.innerHTML = '<p class="mensagem-vazia">Não foi possível carregar as vagas agora. Tente novamente em instantes.</p>';
    console.error(erro);
  }
}

botaoBuscar.addEventListener('click', carregarVagas);
[campoBusca, campoLocalizacao].forEach(campo => {
  campo.addEventListener('keydown', evento => {
    if (evento.key === 'Enter') carregarVagas();
  });
});
campoCategoria.addEventListener('change', carregarVagas);

preencherCategorias();
carregarVagas();
