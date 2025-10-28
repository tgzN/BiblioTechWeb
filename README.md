# 📚 BiblioTech Web (Projeto Integrador Senac)

## 💡 Descrição do Projeto

O **BiblioTech Web** é um sistema de gerenciamento de biblioteca desenvolvido como Projeto Integrador. Ele visa digitalizar e otimizar as operações de controle de livros, empréstimos, devoluções e aplicação de multas.

Este projeto representa a transição da aplicação para um ambiente **web**, utilizando o framework Spring Boot para o back-end e integrando um front-end dinâmico.


## 💻 Tecnologias Utilizadas

| Camada | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Back-end** | Java | Linguagem principal do projeto. |
| **Framework** | Spring Boot | Utilizado para criar a aplicação web e API REST. |
| **Persistência** | Spring Data JPA / Hibernate | Mapeamento Objeto-Relacional e persistência de dados. |
| **Banco de Dados**| <MySQL/PostgreSQL/H2> | Armazenamento de dados do sistema. |
| **Front-end** | HTML5, CSS3, JavaScript | Estrutura e interatividade na web. |
| **Template Engine** | Thymeleaf | Utilizado para renderizar as páginas HTML dinamicamente. |
| **Versionamento**| Git e GitHub | Controle de versão e hospedagem do código-fonte. |

## ✨ Funcionalidades

O sistema BiblioTech permite aos usuários realizar as seguintes operações principais:

* **CRUD Completo de Livros:** Cadastrar, consultar, editar e excluir livros do acervo.
* **CRUD de Usuários:** Gerenciamento dos leitores/clientes.
* **Empréstimo:** Registro de retirada de livros por usuários.
* **Devolução:** Processamento de devolução de livros, calculando automaticamente a multa, se houver.
* **Controle de Multas:** Aplicação e registro de multas por atraso na devolução.

## ⚙️ Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter os seguintes itens instalados em sua máquina:

* **Java JDK** (versão 17 ou superior)
* **Maven** (Gerenciador de dependências)
* **IDE** (NetBeans/IntelliJ/VSCode)
* **Banco de Dados** <Seu BD> (Ex: MySQL)

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/tgzN/BiblioTechWeb.git](https://github.com/tgzN/BiblioTechWeb.git)
    ```
2.  **Configurar o Banco de Dados:**
    * Crie um banco de dados vazio chamado `<Nome do seu DB>` (Ex: `bibliotech_db`).
    * Atualize o arquivo `src/main/resources/application.properties` com suas credenciais de acesso ao DB.
3.  **Executar a Aplicação:**
    * Abra o projeto na sua IDE favorita.
    * Execute a classe principal `BiblioTechWebApplication.java`.
    * Acesse a aplicação no seu navegador em: `http://localhost:8080`
