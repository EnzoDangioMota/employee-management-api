<div align="center">

# ms-employee-management

**Microsserviço de gestão de funcionários com interface web integrada em Spring Boot.**

![Java 21](https://img.shields.io/badge/Java-21-1BA285?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot 4.1.1](https://img.shields.io/badge/Spring_Boot-4.1.1-1BA285?style=for-the-badge&logo=springboot&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Frontend-1BA285?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-263244?style=for-the-badge&logo=apachemaven&logoColor=white)

</div>

## Sobre o projeto

O **`ms-employee-management`** é um microsserviço Spring Boot responsável pelo cadastro, consulta e análise de funcionários. Sua interface é renderizada no servidor por Thymeleaf, permitindo utilizar as operações do serviço sem depender de Postman ou de um frontend separado.

A interface utiliza uma identidade visual escura, baseada no layout Moonlight, preservando o verde `#1BA285` como cor principal. Sidebar, cabeçalho e abas são reutilizados por meio de fragments Thymeleaf.

```text
Service:  ms-employee-management
Domain:   employee management
Owner:    equipe-rh
Lifecycle: production
Runtime:  Spring Boot
Storage:  in-memory
```

Os dados são armazenados temporariamente em um `ArrayList`. Portanto, os registros são removidos quando a aplicação é encerrada ou reiniciada.

## Funcionalidades implementadas

- Cadastro de funcionários em modal;
- Validação dos dados enviados pelo formulário;
- Listagem dos funcionários cadastrados;
- Busca por nome, e-mail, cargo ou departamento;
- Filtro por status;
- Exclusão com confirmação;
- Indicadores calculados a partir dos registros;
- Dashboard de distribuição por status;
- Dashboard de funcionários por departamento;
- Cálculo de taxa de contratação e salário médio;
- Layout responsivo para desktop e dispositivos móveis;
- Componentes compartilhados com fragments Thymeleaf.

Os status disponíveis são:

- `EM_ANALISE`
- `APROVADO`
- `REPROVADO`
- `CONTRATADO`

## Páginas

| Rota | Página | Descrição |
|---|---|---|
| `/` | Visão geral | Resumo do quadro de funcionários e acesso ao cadastro |
| `/funcionarios` | Funcionários | Lista, pesquisa, filtro e exclusão de registros |
| `/indicadores` | Indicadores | Métricas e dashboards atualizados com os dados cadastrados |

## Operações HTTP

| Método | Endpoint | Descrição |
|:---:|---|---|
| `GET` | `/` | Renderiza a visão geral |
| `GET` | `/funcionarios` | Renderiza a lista com busca e filtro opcionais |
| `GET` | `/indicadores` | Renderiza os dashboards |
| `POST` | `/funcionarios` | Valida e cadastra um funcionário |
| `POST` | `/funcionarios/remover` | Remove um funcionário pelo ID |

## Tecnologias

- Java 21;
- Spring Boot 4.1.1;
- Spring MVC;
- Thymeleaf;
- Bean Validation;
- Lombok;
- HTML, CSS e JavaScript;
- Maven Wrapper;
- JUnit, Mockito e Spring Boot Test.

## Arquitetura

```text
Navegador
   ↓
Templates Thymeleaf + fragments compartilhados
   ↓
HomeController
   ↓
FuncionarioDTO ↔ FuncionarioMapper ↔ Funcionario
   ↓
FuncionarioRepository
   ↓
ArrayList<Funcionario>
```

O frontend continua no mesmo projeto Spring Boot. Essa escolha reduz a complexidade de configuração e mantém o foco no Spring MVC e no fluxo completo entre formulário, controller e repositório.

## Como executar

### Pré-requisitos

- JDK 21 ou superior;
- Git.

Clone o repositório:

```bash
git clone https://github.com/EnzoDangioMota/Picpay-API-Desafio.git
cd Picpay-API-Desafio
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

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

O projeto possui testes para inicialização do contexto Spring, validação do DTO, mapeamento e operações do repositório.

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/picpay/api/
│   │   ├── controller/HomeController.java
│   │   ├── dto/
│   │   │   ├── FuncionarioDTO.java
│   │   │   └── FuncionarioMapper.java
│   │   ├── model/
│   │   │   ├── Funcionario.java
│   │   │   └── StatusFuncionario.java
│   │   └── repository/FuncionarioRepository.java
│   └── resources/
│       ├── static/
│       │   ├── css/styles.css
│       │   └── js/app.js
│       └── templates/
│           ├── fragments/layout.html
│           ├── index.html
│           ├── funcionarios.html
│           └── indicadores.html
└── test/java/com/picpay/api/
    ├── dto/
    └── repository/
```

## Próximas evoluções

- Implementar edição completa e parcial de funcionários;
- Adicionar consulta individual por ID;
- Criar uma camada de serviço para as regras de negócio;
- Persistir os dados em um banco de dados;
- Ampliar os testes da camada MVC;
- Adicionar paginação à lista de funcionários.

## Autoria

Desenvolvido por **Giovanna Quirino** como parte de um desafio de desenvolvimento com Spring Boot e frontend integrado.

## Licença

Este projeto está disponível sob os termos da licença [MIT](LICENSE).

<div align="center">
  <sub>ms-employee-management — employee management powered by Spring Boot.</sub>
</div>
