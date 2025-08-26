# Markdown Notes-Taking App

Desafio baseado em [Markdown Notes](https://roadmap.sh/projects/markdown-note-taking-app).  
Este aplicativo permite que os usuários façam upload de arquivos em formato Markdown, verifiquem sua gramática, salvem as anotações e as renderizem em HTML. O objetivo é oferecer uma ferramenta simples para gerenciar e visualizar notas em formato Markdown e, ao mesmo tempo, aprender a lidar com o upload de arquivos em uma API RESTful.

## **Tecnologias Utilizadas**

### **Backend**
- **Spring Boot 3.4.3** - Framework para desenvolvimento da API REST.
- **Java 21** - Linguagem de programação utilizada no backend.

### **Processamento de Markdown**
- **Flexmark (com.vladsch.flexmark 0.64.8)** - Biblioteca para conversão de Markdown em HTML.

### **Manipulação de Arquivos**
- **Apache Commons IO (commons-io 2.18.0)** - Facilita operações de leitura e escrita em arquivos.

### **Verificação Gramatical**
- **LanguageTool (org.languagetool.languagetool-core 6.5)** - Biblioteca para análise gramatical.
- **LanguageTool Portuguese (org.languagetool.language-pt 6.5)** - Suporte ao idioma português na verificação gramatical.

### **Ferramentas de Desenvolvimento**
- **Spring Boot DevTools** - Ferramenta para agilizar o desenvolvimento, permitindo reinicialização automática da aplicação.
- **Spring Boot Starter Test** - Biblioteca para execução de testes unitários e de integração.

### **Build e Gerenciamento de Dependências**
- **Maven** - Ferramenta utilizada para gerenciamento de dependências e build do projeto.

## **Funcionalidades**

A aplicação inclui as seguintes funcionalidades:

1. **Verificação gramatical** - Endpoint para verificar a gramática de uma nota em Markdown.
2. **Salvar notas** - Endpoint para armazenar uma nota escrita em Markdown.
3. **Listar notas** - Endpoint para listar as notas salvas (arquivos Markdown enviados).
4. **Renderizar notas** - Endpoint para converter uma nota em Markdown para HTML, permitindo sua visualização.

## **Endpoints**

### **1. Verificar gramática**
- **Descrição**: Analisa o texto em Markdown para identificar erros gramaticais.
- **Método**: `POST`
- **Rota**: `/notes/check-grammar`
- **Parâmetro**: `text` (conteúdo da nota em Markdown).

### **2. Salvar nota**
- **Descrição**: Armazena uma nota escrita em Markdown no sistema.
- **Método**: `POST`
- **Rota**: `/notes/save`
- **Parâmetro**: `text` (conteúdo da nota em Markdown).

### **3. Listar notas**
- **Descrição**: Retorna uma lista com as notas salvas.
- **Método**: `GET`
- **Rota**: `/notes`

### **4. Renderizar nota**
- **Descrição**: Converte uma nota em Markdown para HTML para visualização.
- **Método**: `GET`
- **Rota**: `/notes/{id}`
- **Parâmetro**: `{id}` (identificador da nota salva).

## **Executando o Projeto**

### **Requisitos**
Antes de executar o projeto, certifique-se de ter instalado:
- **Java 21**
- **Maven**

### **Passos para Execução**
1. Clone o repositório:
   ```bash  
   git clone https://github.com/aguedamaiara/MarkdownNote.git 
   ```  
2. Acesse a pasta do projeto:
   ```bash  
   cd MarkdownNote  
   ```  
3. Compile e execute a aplicação com Maven:
   ```bash  
   mvn spring-boot:run  
   ```  
4. A aplicação estará disponível em `http://localhost:8080`.

