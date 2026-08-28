<div align="center">

<h1>💚 PicPay Hiring Hub</h1>

<p>
  <strong>Uma experiência simples e organizada para gerenciar candidatos do processo seletivo PicPay.</strong>
</p>

<p>
  <img alt="Java 21" src="https://img.shields.io/badge/Java-21-21C25E?style=for-the-badge&logo=openjdk&logoColor=white">
  <img alt="Spring Boot 4.1.1" src="https://img.shields.io/badge/Spring_Boot-4.1.1-21C25E?style=for-the-badge&logo=springboot&logoColor=white">
  <img alt="Thymeleaf" src="https://img.shields.io/badge/Thymeleaf-UI-21C25E?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Maven-Wrapper-333333?style=for-the-badge&logo=apachemaven&logoColor=white">
</p>

</div>

## Sobre o projeto

O **PicPay Hiring Hub** é uma aplicação web para apoiar o time de Recursos Humanos no cadastro e acompanhamento de candidatos. A proposta é transformar uma API Spring Boot em uma solução visual que possa ser utilizada sem depender de ferramentas como Postman ou Insomnia.

O projeto foi iniciado como parte de um desafio de desenvolvimento com Java e Spring Boot. Os dados serão mantidos temporariamente em memória com `ArrayList`, sem a necessidade de banco de dados nesta etapa.

> [!NOTE]
> O repositório contém atualmente a estrutura inicial da aplicação Spring Boot. As funcionalidades descritas abaixo representam o escopo planejado para o desafio.

## Funcionalidades planejadas

- Cadastrar novos candidatos;
- Listar todos os candidatos;
- Consultar um candidato pelo ID;
- Editar todos os dados de um cadastro;
- Atualizar parcialmente cargo, salário ou status;
- Excluir candidatos;
- Pesquisar por nome, cargo ou status;
- Exibir indicadores do processo seletivo.

Os candidatos poderão assumir os status `EM_ANALISE`, `APROVADO`, `REPROVADO` e `CONTRATADO`.

## Endpoints previstos

| Método | Endpoint | Descrição |
|:---:|---|---|
| `POST` | `/funcionarios` | Cadastra um candidato |
| `GET` | `/funcionarios` | Lista todos os candidatos |
| `GET` | `/funcionarios/{id}` | Consulta um candidato pelo ID |
| `PUT` | `/funcionarios/{id}` | Atualiza completamente um cadastro |
| `PATCH` | `/funcionarios/{id}` | Atualiza campos específicos |
| `DELETE` | `/funcionarios/{id}` | Remove um candidato |

## Escolha do front-end

O **Thymeleaf** foi escolhido por sua integração direta com o Spring Boot. Ele permite construir páginas HTML dinâmicas no mesmo projeto, reduzindo a complexidade de configuração e mantendo o foco nos conceitos centrais do desafio: Spring MVC, métodos HTTP e operações de CRUD.

```text
Usuário → Thymeleaf → Spring MVC → Regras da aplicação → ArrayList<Funcionario>
```

Essa abordagem é adequada para o primeiro ciclo da aplicação e poderá ser substituída futuramente por um front-end separado, caso o nível de interatividade aumente.

## Tecnologias

- Java 21;
- Spring Boot 4.1.1;
- Spring MVC;
- Thymeleaf;
- Maven Wrapper;
- JUnit e Spring Boot Test;
- HTML, CSS e JavaScript para a interface.

## Como executar

### Pré-requisitos

- JDK 21 ou superior instalado;
- Git instalado.

Clone o repositório:

```bash
git clone https://github.com/EnzoDangioMota/Picpay-API-Desafio.git
cd Picpay-API-Desafio
```

No Windows, execute:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS, execute:

```bash
./mvnw spring-boot:run
```

Depois, acesse [http://localhost:8080](http://localhost:8080).

## Testes

No Windows:

```powershell
.\mvnw.cmd test
```

No Linux ou macOS:

```bash
./mvnw test
```

## Estrutura atual

```text
src/
├── main/
│   ├── java/com/picpay/api/
│   │   ├── model/
│   │   │   ├── Funcionario.java
│   │   │   └── StatusFuncionario.java
│   │   ├── repository/
│   │   │   └── FuncionarioRepository.java
│   │   └── ApiApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/picpay/api/
        ├── repository/
        │   └── FuncionarioRepositoryTests.java
        └── ApiApplicationTests.java
```

A aplicação está separada por responsabilidades. As camadas `controller` e `service`, além dos templates em `resources/templates`, serão adicionadas junto das funcionalidades HTTP e da interface.

## Roadmap

- [x] Criar a estrutura base do Spring Boot;
- [x] Configurar Spring MVC e Thymeleaf;
- [x] Adicionar teste de inicialização do contexto;
- [x] Criar o modelo `Funcionario`;
- [x] Implementar o armazenamento em `ArrayList`;
- [ ] Implementar os endpoints REST;
- [ ] Adicionar validações e tratamento de erros;
- [ ] Criar formulário, listagem e edição com Thymeleaf;
- [ ] Implementar busca e indicadores;
- [ ] Ampliar a cobertura de testes.

## Autoria

Desenvolvido por **Giovanna Quirino** como parte do desafio Spring Boot de gerenciamento de candidatos.

## Licença

Este projeto está disponível sob os termos da licença [MIT](LICENSE).

<div align="center">
  <sub>Feito com Java, Spring e a energia verde do PicPay 💚</sub>
</div>
