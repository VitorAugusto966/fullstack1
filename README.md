# 📋 Desafio Técnico Fullstack 1 - JTech

## API RESTful para Gerenciamento de Tarefas

### 📌 Visão Geral do Projeto

API desenvolvida como parte do desafio técnico da **JTech** para gerenciamento de tarefas (TODO List).
Permite criar, listar, buscar, atualizar e excluir tarefas, com validação robusta, tratamento de erros padronizado e testes automatizados (unitários, de controller e de integração).

O projeto inclui também um **frontend em Vue 3**, permitindo interagir com a API de forma dinâmica e responsiva.

---

## 🛠 Stack Utilizada

* **Java 21**
* **Spring Boot 3**
* **Spring Web**
* **Spring Data JPA + Hibernate**
* **Banco de Dados**: PostgreSQL (produção) / H2 (testes)
* **Bean Validation (Jakarta)**
* **Frontend**: Vue 3, Vue Router, Vuelidate, Axios, Tailwind CSS
* **Testes**: JUnit 5 + Mockito + TestRestTemplate (integração)
* **Build Tool**: Maven
* **Outros**: Lombok, Spring Boot DevTools

---

## 🚀 Como Rodar Localmente

### Pré-requisitos

* Java 21 instalado
* Maven instalado
* Node.js + npm ou Yarn
* PostgreSQL rodando localmente ou via Docker

### Backend

1. **Clone o repositório**

   ```bash
   git clone https://github.com/VitorAugusto966/fullstack1.git
   cd fullstack1/backend
   ```

2. **Configure o banco de dados PostgreSQL**

   ```sql
   CREATE DATABASE jtech_task;
   ```

3. **Configure o application.properties**

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/jtech_task
       username: postgres
       password: sua_senha
       driver-class-name: org.postgresql.Driver
     jpa:
       hibernate:
         ddl-auto: update
       properties:
         hibernate:
           dialect: org.hibernate.dialect.PostgreSQLDialect
       open-in-view: false
   ```

4. **Execute a aplicação**

   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (Vue 3)

1. **Entre na pasta do frontend**

   ```bash
   cd ../jtech-tasklist-frontend
   ```

2. **Instale as dependências**

   ```bash
   npm install
   # ou
   yarn install
   ```

3. **Execute a aplicação Vue**

   ```bash
   npm run dev
   # ou
   yarn dev
   ```

4. **Acesse o frontend**

   * Disponível em: `http://localhost:5173` (Vite padrão)

---

## 🧪 Como Rodar os Testes

### Backend

```bash
./mvnw test
```

### Testes de Integração com H2

Os testes de integração sobem o contexto completo do Spring Boot e validam o fluxo real da API contra um banco H2 em memória:

* Criação de tarefa
* Listagem
* Busca por ID
* Atualização
* Exclusão

Eles estão localizados em:

```
src/test/java/com/jtech/backend/TaskIntegrationTest.java
```

### Frontend

* Para testes de unidade ou componentes, usar Jest/Vitest conforme configuração do projeto Vue.

---

## 📂 Estrutura de Pastas

```
src/main/java/com/jtech/backend
 ├── controller         # Endpoints REST
 ├── service            # Regras de negócio
 ├── repository         # Interface JPA
 ├── model              # Entidade e Enum
 ├── dto                # Objetos de entrada/saída
 ├── exception          # Tratamento de erros

jtech-tasklist-frontend/
 ├── src/components     # Componentes Vue
 ├── src/views          # Telas / páginas
 ├── src/router         # Vue Router
 ├── src/services       # Chamadas Axios para API
 ├── src/styles         # CSS / Tailwind
```

---

## 🧠 Decisões Técnicas

* **PostgreSQL** para persistência em produção; **H2** para testes rápidos e integração.
* **DTOs** para desacoplar a entidade do modelo de entrada/saída.
* **Service layer** centraliza regras e transações, mantendo o controller fino.
* **GlobalExceptionHandler** garante respostas consistentes e claras em erros.
* **Lombok** reduz boilerplate e melhora legibilidade.
* **Testes de integração** garantem que o fluxo completo da API funciona de ponta a ponta.
* **Frontend Vue 3 + Tailwind** para interface moderna, responsiva e modular.

---

## 🔮 Melhorias Futuras

* **Autenticação & Autorização**: implementar segurança com **Spring Security + JWT**, permitindo que cada usuário tenha suas próprias tarefas.
* **Paginação e Filtros Avançados**: suporte a **paginação, ordenação e filtros por status, data de criação e título**.
* **Soft Delete & Auditoria**: manter histórico de exclusões (soft delete) e registrar datas de criação/atualização com auditoria automática.
* **Documentação com Swagger / OpenAPI**: expor uma interface interativa para explorar os endpoints e facilitar integração com clientes externos.
* **Testes End-to-End (E2E)**: criar testes com **Testcontainers** (PostgreSQL em Docker) para garantir comportamento em ambiente próximo ao real.
* **CI/CD Automatizado**: configurar pipeline de integração contínua (ex.: GitHub Actions) rodando build, testes e análise de qualidade a cada push.
* **Deploy em Nuvem**: preparar a aplicação para ser deployada em **Heroku, AWS, Azure ou Render**, com banco em nuvem (RDS, Neon, Supabase).
