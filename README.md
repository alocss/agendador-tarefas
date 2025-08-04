# 🗓️ Agendador de Tarefas

Servidor backend em **Java 17** com **Spring Boot** que compõe um dos microserviços principais de um sistema de agendamento de tarefas. Focado em operações CRUD, agendamentos e integração com os microsserviços de usuário, notificação e BFF.

---

## 📌 Escopo

Este microserviço é responsável por:

- Cadastros e gerenciamento de tarefas agendadas
- Operações CRUD completas via API
- Consulta de tarefas por parâmetros como data, e-mail e status
- Comunicação com outros microsserviços (Usuário, Notificação, BFF)
- Agendamento de tarefas periódicas (cron para notificações)

*(Desenvolvimento baseado no repositório `alocss/agendador-tarefas` com 15 commits até agora)* :contentReference[oaicite:1]{index=1}

---

## 🧰 Tecnologias & Ferramentas

- **Java 17** com **Spring Boot**
- Spring Web + Spring Data (JPA ou MongoDB, conforme configuração)
- **Gradle** como ferramenta de build (`build.gradle`, `gradlew`)
- **JUnit 5** e **Mockito** para testes unitários
- **GitHub Actions** configurado em `.github/workflows/` para CI/CD
- Em breve: integração com cron e serviço de e-mail via microsserviço de notificação :contentReference[oaicite:2]{index=2}

---

## 📁 Estrutura do Projeto

agendador-tarefas/
### ├── src/
### │ ├── main/java/... # lógica backend (Controller, Service, Repository, DTOs)
### │ ├── test/java/... # testes unitários com JUnit e Mockito
### │ └── resources/ # configurações Spring Boot
### ├── build.gradle
### ├── gradlew / gradlew.bat
### ├── settings.gradle
### └── .github/workflows/ # pipelines CI, testes automatizados, build
---

## 🚀 Como Executar Localmente

bash
git clone https://github.com/alocss/agendador-tarefas.git
cd agendador-tarefas
./gradlew build
./gradlew bootRun

A API estará disponível em http://localhost:8080. Configurações como porta, dados de conexão e tipo de banco devem ser ajustadas em application.properties ou application.yml.
🧪 Executando Testes

./gradlew test

Os testes estão organizados em src/test/java/, com foco em cobertura da lógica de backend e fluxo de dados usando JUnit e Mockito.
📐 Endpoints REST (Exemplos)
Método	Endpoint	Descrição
### POST	/tasks	Criar uma nova tarefa
### GET	/tasks	Listar todas as tarefas
### GET	/tasks?date={date}	Filtrar tarefas por data
### GET	/tasks?email={email}	Filtrar tarefas por usuário (e-mail)
### PUT	/tasks/{id}	Atualizar dados da tarefa
### PATCH	/tasks/{id}/status	Atualizar status da tarefa (ex. pendente → feito)
### DELETE	/tasks/{id}	Remover tarefa

Verifique o código-fonte da camada Controller para detalhes das rotas reais.

🔗 Integração com Microsserviços

Este serviço interage com os seguintes componentes:

    Usuário: valida o e-mail/identidade do proprietário da tarefa

    Notificação: método programado para envio de e-mails como lembretes

    BFF (Backend for Frontend): agrega dados de tarefas e usuários para consumo do frontend
  
