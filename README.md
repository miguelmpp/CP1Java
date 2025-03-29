# 🍽️ Módulo de Gestão de Pedidos - Restaurante

## 👥 Contribuidores
 
- Matheus Farias de Lima - RM554254  
- Miguel Mauricio Parrado Patarroyo - RM554007  
- Larissa Estela Gonçalves - RM552695  
- Vitor Pinheiro Nascimento - RM553693  
- Pedro Henrique Chaves - RM553988

---
 
Este repositório contém o código-fonte e a documentação do módulo **Gestão de Pedidos**, desenvolvido para o domínio **[www.restaurante.com.br](http://www.restaurante.com.br)**, seguindo os princípios de **Domain-Driven Design (DDD)**.  
O sistema permite **registrar, consultar e listar pedidos**, integrando **validações de negócio** e **persistência em banco de dados Oracle**.
 
---
 
## 🛠 Tecnologias Utilizadas
 
- **Java 17+**
- **JDBC** (Java Database Connectivity)
- **Oracle Database**
- **SQL** para operações CRUD
- **DDD** (Domain-Driven Design)
 
---
 
## 📋 Pré-requisitos
 
- JDK 17 ou superior
- Maven ou Gradle (para gerenciamento de dependências)
- Banco de dados Oracle configurado
- Driver JDBC Oracle (`ojdbc17.jar`)
 
---
 
## 🔧 Configuração
 
### 1. Driver JDBC Oracle (`ojdbc17.jar`)
 
Para executar o projeto, é **OBRIGATÓRIO** adicionar o arquivo `ojdbc17.jar` ao *Java Build Path*.
 
- **Download do Driver:**  
  [Oracle JDBC Driver Downloads](https://www.oracle.com/br/database/technologies/appdev/jdbc-downloads.html)
 
- **Detalhes do Driver:**
  - Implementa **JDBC 4.3**, compatível com JDK17, JDK19 e JDK21
  - Suporta APIs Jakarta
  - Tamanho: 7.313.474 bytes
  - SHA1: `d762f4e2d24e30e271993afcac927614be8c267a`
 
- **Como Adicionar ao Projeto:**
  - **Eclipse:**  
    `Botão direito no projeto → Build Path → Configure Build Path → Libraries → Add External JARs`
  - **IntelliJ:**  
    `File → Project Structure → Libraries → + → Java → Selecione o arquivo JAR`
 
### 2. Configuração do Banco de Dados
 
Edite as credenciais no arquivo `PedidoDAO.java`:
 
```java
private static final String URL = "jdbc:oracle:thin:@SEU_HOST:PORTA/SERVICO";
private static final String USER = "SEU_USUARIO";
private static final String PASS = "SUA_SENHA";
```
 
### 3. Criação das Tabelas
 
Execute os scripts SQL disponíveis na pasta `sql/` do repositório para criar as tabelas `pedidos` e `itens_pedido`.
 
---
 
## 🚀 Como Executar
 
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```
2. Importe o projeto em sua IDE (Eclipse, IntelliJ, etc.)
3. Adicione o `ojdbc17.jar` ao Build Path (veja a seção Configuração)
4. Atualize as credenciais do banco de dados no arquivo `PedidoDAO.java`
5. Execute a classe `Main.java` para testar as funcionalidades
 
---
 
## 📂 Estrutura do Projeto
 
```
src/main/java
├── com.restaurante.pedido.application
│   ├── Main.java
│   └── PedidoService.java
├── com.restaurante.pedido.domain.exceptions
│   └── PedidoNaoEncontradoException.java
├── com.restaurante.pedido.domain.model
│   ├── ItemPedido.java
│   ├── Pedido.java
│   └── StatusPedido.java
├── com.restaurante.pedido.domain.repository
│   └── PedidoRepository.java
└── com.restaurante.pedido.infrastructure.jdbc
    └── PedidoDAO.java
```
 
