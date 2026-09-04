# PicPay Hiring Hub

Sistema web para gerenciar candidatos no processo de contratação do PicPay. O projeto demonstra, em uma interface utilizável, os métodos HTTP `POST`, `GET`, `PUT`, `PATCH` e `DELETE`.

## Funcionalidades

- Cadastro de candidato com ID único;
- Listagem e busca por nome, cargo ou status;
- Consulta individual por ID;
- Edição completa com `PUT` e atualização parcial com `PATCH`;
- Exclusão com `DELETE`;
- Indicadores em tempo real: total, em análise, aprovados e contratados;
- Validações e mensagens adequadas para dados inválidos, ID duplicado e recurso não encontrado.

## Tecnologias

- Java 21, Spring Boot 4.1.1 e Spring MVC;
- Thymeleaf;
- HTML, CSS e JavaScript (Fetch API);
- Bean Validation, Lombok, Maven e JUnit 5.

## Arquitetura e comunicação

Os dados ficam temporariamente em uma `ArrayList<Funcionario>`, conforme solicitado no desafio, sem banco de dados. A página inicial é servida pelo Thymeleaf e o JavaScript consome a API REST com `fetch`.

```text
Usuário -> HTML/CSS/JavaScript -> HTTP/Fetch -> Controller REST
        -> Service -> Repository -> ArrayList<Funcionario>
```

## Modelo

| Campo | Tipo | Obrigatório |
|---|---|:---:|
| `id` | número | Sim |
| `nome` | texto | Sim |
| `email` | texto | Sim |
| `telefone` | texto | Não |
| `cargo` | texto | Sim |
| `departamento` | texto | Não |
| `salario` | decimal >= 0 | Não |
| `cidade` | texto | Não |
| `status` | enum | Não |

Status aceitos: `EM_ANALISE`, `APROVADO`, `REPROVADO` e `CONTRATADO`.

## API REST

| Método | Endpoint | Finalidade |
|:---:|---|---|
| `POST` | `/funcionarios` | Cadastra um candidato |
| `GET` | `/funcionarios` | Lista todos e atualiza indicadores |
| `GET` | `/funcionarios/{id}` | Consulta pelo ID |
| `PUT` | `/funcionarios/{id}` | Atualiza todos os dados |
| `PATCH` | `/funcionarios/{id}` | Atualiza somente campos enviados |
| `DELETE` | `/funcionarios/{id}` | Exclui o candidato |

Exemplo para `POST` e `PUT`:

```json
{
  "id": 1,
  "nome": "Ana Souza",
  "email": "ana.souza@exemplo.com",
  "cargo": "Desenvolvedora Java",
  "departamento": "Tecnologia",
  "salario": 7500.00,
  "cidade": "São Paulo",
  "status": "EM_ANALISE"
}
```

Exemplo de `PATCH`:

```json
{ "status": "APROVADO", "salario": 8200.00 }
```

## Executar localmente

Pré-requisito: JDK 21 ou superior.

```powershell
.\mvnw.cmd spring-boot:run
```

Em Linux/macOS:

```bash
./mvnw spring-boot:run
```

Abra [http://localhost:8080](http://localhost:8080). Os registros são apagados no reinício porque a atividade exige armazenamento temporário em memória.

## Testes

```powershell
.\mvnw.cmd test
```

Há testes de inicialização, validação do DTO, mapeamento e repositório. Além disso, a entrega foi validada manualmente em POST → PUT → PATCH → GET → DELETE, com retorno `404` após a exclusão.

## Roteiro de apresentação

1. Justifique Thymeleaf + JavaScript: interface no mesmo projeto e comunicação HTTP explícita com o Spring Boot.
2. Cadastre um candidato e mostre o `POST` atualizando tabela e indicadores.
3. Consulte pelo ID com `GET /funcionarios/{id}`.
4. Clique em **Editar** e salve todos os dados com `PUT`.
5. Altere apenas status ou salário no quadro de atualização parcial com `PATCH`.
6. Pesquise por nome, cargo ou status e remova o registro com `DELETE`.
7. Cite a limitação atual: dados temporários por exigência da atividade. Como evolução, pode-se adicionar banco de dados e autenticação.

## Estrutura

```text
src/main/java/com/picpay/api/
├── controller/     # Página inicial e API REST
├── service/        # Regras de CRUD
├── repository/     # ArrayList em memória
├── dto/            # Transporte e validações
├── model/          # Funcionario e StatusFuncionario
└── exception/      # Tratamento de erros
src/main/resources/
├── templates/      # Página Thymeleaf
└── static/         # CSS e JavaScript com Fetch API
```

## Autoria

Desenvolvido por Giovanna Quirino para o desafio de gerenciamento de candidatos com Spring Boot.
