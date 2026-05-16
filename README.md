#  Padaria — Sistema de Gestão Financeira

API REST para gestão financeira de uma padaria, desenvolvida com Java 21 e Spring Boot. O sistema controla vendas, compras, estoque, contas a pagar e fluxo de caixa diário, com autenticação via JWT.

---

##  Tecnologias

- Java 21
- Spring Boot 3.4
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Lombok
- Maven

---

## Funcionalidades

- **Autenticação** — registro e login com token JWT
- **Fornecedores** — cadastro e gerenciamento de fornecedores
- **Produtos** — cadastro com unidade, preço e categoria
- **Estoque** — controle de quantidade real com alerta de estoque mínimo
- **Compras** — registro de compras com atualização automática do estoque
- **Vendas** — registro de vendas com baixa automática do estoque
- **Contas a Pagar** — controle de despesas com filtro por status
- **Caixa Diário** — criação automática com totalização de vendas, despesas e saldo

---

##  Autenticação

A API utiliza autenticação stateless com JWT. Todas as rotas exceto `/v1/auth/**` exigem o token no header:

```
Authorization: Bearer <token>
```

### Roles disponíveis
| Role | Descrição |
|---|---|
| `ADMIN` | Acesso total ao sistema |
| `OPERADOR` | Acesso operacional |

---

##  Modelagem

```
Usuario
Fornecedor
Produto
EstoqueItem       → referencia Produto
Compra            → referencia Fornecedor
ItemCompra        → referencia Compra e Produto
Venda             → referencia CaixaDiario
ItemVenda         → referencia Venda e Produto
ContaPagar        → referencia Fornecedor (opcional)
CaixaDiario
```

---

##  Endpoints

### Auth
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/auth/registrar` | Registra novo usuário |
| POST | `/v1/auth/login` | Realiza login e retorna token |

### Fornecedores
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/fornecedores` | Cria fornecedor |
| GET | `/v1/fornecedores` | Lista todos |
| GET | `/v1/fornecedores/{id}` | Busca por ID |
| PUT | `/v1/fornecedores/{id}` | Atualiza |
| DELETE | `/v1/fornecedores/{id}` | Remove |

### Produtos
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/produtos` | Cria produto |
| GET | `/v1/produtos` | Lista todos |
| GET | `/v1/produtos/{id}` | Busca por ID |
| PUT | `/v1/produtos/{id}` | Atualiza |
| DELETE | `/v1/produtos/{id}` | Remove |

### Estoque
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/estoque` | Cria item de estoque |
| GET | `/v1/estoque` | Lista todos |
| GET | `/v1/estoque/{id}` | Busca por ID |
| PUT | `/v1/estoque/{id}` | Atualiza quantidade |

### Compras
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/compras` | Registra compra e atualiza estoque |
| GET | `/v1/compras` | Lista todas |
| GET | `/v1/compras/{id}` | Busca por ID |
| PATCH | `/v1/compras/{id}/pagar` | Marca como paga |
| PATCH | `/v1/compras/{id}/cancelar` | Cancela e reverte estoque |

### Vendas
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/vendas` | Registra venda e atualiza estoque e caixa |
| GET | `/v1/vendas` | Lista todas |
| GET | `/v1/vendas/{id}` | Busca por ID |

### Contas a Pagar
| Método | Rota | Descrição |
|---|---|---|
| POST | `/v1/contas-pagar` | Cria conta |
| GET | `/v1/contas-pagar` | Lista todas |
| GET | `/v1/contas-pagar?status=PENDENTE` | Filtra por status |
| GET | `/v1/contas-pagar/{id}` | Busca por ID |
| PATCH | `/v1/contas-pagar/{id}/pagar` | Paga conta e atualiza caixa |
| PATCH | `/v1/contas-pagar/{id}/cancelar` | Cancela conta |
| DELETE | `/v1/contas-pagar/{id}` | Remove |

### Caixa Diário
| Método | Rota | Descrição |
|---|---|---|
| GET | `/v1/caixa-diario` | Lista todos |
| GET | `/v1/caixa-diario/{id}` | Busca por ID |
| PATCH | `/v1/caixa-diario/{id}/fechar` | Fecha o caixa do dia |

---

## ️ Como executar

### Pré-requisitos
- Java 21
- PostgreSQL
- Maven

### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/seuusuario/padaria.git
```

2. Crie o banco de dados:
```sql
CREATE DATABASE padaria;
```

3. Configure o `application.properties`:
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Execute o projeto:
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

##  Estrutura do Projeto

```
src/main/java/com/gabrielf/padaria/
├── config/       # Security, JWT Filter, Token Provider
├── controller/   # Endpoints REST
├── dto/          # Records de Request e Response
├── exception/    # Handler global de exceções
├── model/        # Entidades JPA
├── repository/   # Interfaces Spring Data
└── service/      # Regras de negócio
```

---

## Regras de Negócio

- Ao registrar uma **compra**, o estoque dos produtos é incrementado automaticamente
- Ao cancelar uma **compra**, o estoque é revertido
- Ao registrar uma **venda**, o estoque é decrementado e o caixa do dia é atualizado
- O **caixa diário** é criado automaticamente na primeira venda do dia
- Ao pagar uma **conta**, o valor é lançado como despesa no caixa do dia
- O **saldo** do caixa é sempre calculado como `totalVendas - totalDespesas`
