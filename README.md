# Portal de Vagas de Monte Santo/BA

Plataforma web para centralização de vagas de emprego locais, conectando
comércios, associações de moradores, produtores rurais e a comunidade de
Monte Santo/BA a oportunidades de trabalho perto de casa.

## Tecnologias

- **Backend:** Java 21 + Spring Boot 3 (Spring Web, Spring Data JPA, Bean
  Validation) + banco de dados H2 (embarcado, sem necessidade de instalar
  servidor de banco de dados).
- **Frontend:** HTML, CSS e JavaScript puro, servido diretamente pelo
  Spring Boot (`src/main/resources/static`).

## Como executar

Pré-requisitos: **JDK 21** e **Maven** instalados na sua máquina.

```bash
# 1. Entre na pasta do projeto
cd plataforma-vagas

# 2. Empacote a aplicação
mvn clean package -DskipTests

# 3. Execute
java -jar target/plataforma-vagas.jar
```

A aplicação sobe em **http://localhost:8080** — essa é a própria página
do portal (não precisa de nenhum outro servidor para o front-end).

Na primeira execução, o banco de dados é criado automaticamente em
`./data/plataformavagas.mv.db`, já com 5 vagas de exemplo para você navegar
e testar as buscas.

O console do H2 (para inspecionar as tabelas, se quiser) fica em
`http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:file:./data/plataformavagas`, usuário `sa`, senha em
branco).

## Painel administrativo

O painel (`/painel.html`, onde dá pra editar, ativar e desativar vagas)
exige login. As credenciais padrão ficam em `application.properties`:

```
spring.security.user.name=admin
spring.security.user.password=monteVagas
```

**Troque a senha antes de deixar a aplicação acessível para outras pessoas**
— edite o valor de `spring.security.user.password` nesse arquivo. O
navegador vai pedir usuário e senha na primeira vez que você acessar o
painel; depois disso, ele lembra até você fechar o navegador.

## Estrutura do projeto

```
plataforma-vagas/
├── pom.xml
├── src/main/java/com/portalvagas/
│   ├── PlataformaVagasApplication.java
│   ├── model/          → entidades Vaga e Curriculo
│   ├── repository/     → acesso a dados (Spring Data JPA)
│   ├── controller/     → API REST (/api/vagas, /api/curriculos)
│   ├── config/         → carga de dados de exemplo (DataSeeder)
│   └── exception/      → tratamento de erros da API
└── src/main/resources/
    ├── application.properties
    └── static/                → frontend
        ├── index.html         → busca e listagem de vagas
        ├── publicar-vaga.html → formulário para publicar vaga
        ├── cadastro-curriculo.html → formulário de currículo
        ├── css/style.css
        └── js/
```

## Endpoints da API

| Método | Rota                 | Descrição                                      |
|--------|-----------------------|-------------------------------------------------|
| GET    | `/api/vagas`           | Lista vagas ativas (filtros: `busca`, `categoria`, `localizacao`) |
| GET    | `/api/vagas/{id}`      | Detalhe de uma vaga                             |
| POST   | `/api/vagas`           | Publica uma nova vaga                           |
| PUT    | `/api/vagas/{id}`      | Atualiza uma vaga                               |
| DELETE | `/api/vagas/{id}`      | Encerra uma vaga (desativa, não apaga)          |
| GET    | `/api/curriculos`      | Lista currículos cadastrados                    |
| POST   | `/api/curriculos`      | Cadastra um novo currículo                      |
| DELETE | `/api/curriculos/{id}` | Remove um currículo                             |

