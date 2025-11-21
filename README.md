# 🛡️ Workshop Spring Boot: API da Guilda de Aventureiros

Bem-vindo à API da Guilda de Aventureiros! Esta é uma aplicação Spring Boot que gerencia um sistema de aventureiros de RPG, permitindo criar, buscar, atualizar e remover aventureiros, além de gerenciar suas missões e progressão de níveis.

## 📋 Estrutura do Projeto

```
src/main/java/workshop/spring/apirest/
├── ApiRestApplication.java     # Classe principal da aplicação
├── controller/                # Controladores REST
│   └── AventureiroController.java
├── entity/                    # Entidades JPA
│   ├── Aventureiro.java
│   └── ClasseRPG.java
├── repository/                # Repositórios de dados
│   └── AventureiroRepository.java
└── service/                   # Lógica de negócios
    └── AventureiroService.java
```

## 🚀 Como Executar

1. **Pré-requisitos**
   - Java 17 ou superior
   - Maven
   - MySQL (ou outro banco de dados compatível com JPA)

2. **Configuração do Banco de Dados**
   Crie um banco de dados MySQL e configure o arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/guilda_sptech?createDatabaseIfNotExist=true&serverTimezone=UTC
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Executando a Aplicação**
    ```bash
   Run no Intellij
   ```
   OU
   ```bash
   mvn spring-boot:run
   ```

   A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

### Aventureiros

#### Listar todos os aventureiros
```
GET /aventureiros/listar
```

#### Buscar aventureiro por ID
```
GET /aventureiros/buscar/{id}
```

#### Buscar aventureiro por nome
```
GET /aventureiros/buscar/{nome}
```

#### Buscar aventureiros por classe
```
GET /aventureiros/buscar/{classe}
```

#### Buscar aventureiros por nível
```
GET /aventureiros/buscar/{nivel}
```

#### Criar novo aventureiro
```
POST /aventureiros/criar
```
**Corpo da requisição:**
```json
{
    "nome": "Nome do Aventureiro",
    "classe": "GUERREIRO"
}
```

#### Atualizar aventureiro
```
PUT /aventureiros/atualizar
```
**Corpo da requisição:**
```json
{
    "id": 1,
    "nome": "Novo Nome",
    "classe": "MAGO",
    "nivel": 1,
    "xp": 0
}
```

#### Deletar aventureiro
```
DELETE /aventureiros/deletar/{id}
```

#### Realizar missão
```
PUT /aventureiros/missao/{id}
```
Realiza uma missão com o aventureiro, concedendo XP e subindo de nível quando necessário.

## 🎮 Classes de Aventureiros

A API suporta as seguintes classes de aventureiros:
- `GUERREIRO`
- `MAGO`
- `ARQUEIRO`
- `LADINO`

## 📊 Modelo de Dados

### Aventureiro
| Campo  | Tipo      | Descrição                          |
|--------|-----------|-----------------------------------|
| id     | Long      | Identificador único               |
| nome   | String    | Nome do aventureiro               |
| classe | ClasseRPG | Classe do aventureiro (enum)      |
| nivel  | Integer   | Nível atual (inicia em 1)         |
| xp     | Integer   | Pontos de experiência (inicia em 0) |
2.  Calcular o ganho de XP aleatório.
3.  Verificar se o herói subiu de nível.

### Criando o Serviço

Crie um pacote chamado `service` e o arquivo `AventureiroService.java`.

**Arquivo:** `src/main/java/com/exemplo/guilda/service/AventureiroService.java`

```java
package com.exemplo.guilda.service;

import com.exemplo.guilda.model.Aventureiro;
import com.exemplo.guilda.repository.AventureiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Transforma essa classe em um componente gerenciado pelo Spring
public class AventureiroService {

    @Autowired // Injeção de Dependência: O Spring traz o Repository pronto pra gente
    private AventureiroRepository repository;

    // --- LISTAR ---
    public List<Aventureiro> listarTodos() {
        return repository.findAll();
    }

    // --- CRIAR ---
    public Aventureiro criar(Aventureiro aventureiro) {
        // Regra de Negócio: Ninguém nasce forte.
        aventureiro.setNivel(1);
        aventureiro.setXp(0);
        return repository.save(aventureiro);
    }

    // --- MISSÃO (A Lógica do Jogo) ---
    public Aventureiro realizarMissao(Long id) {
        // 1. Busca o aventureiro (ou lança erro se não achar)
        Aventureiro heroi = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aventureiro não encontrado ID: " + id));

        // 2. Calcula XP ganho (Aleatório entre 10 e 20)
        // Math.random() gera 0.0 a 0.99. Multiplica por 10 e soma 10.
        int xpGanho = (int) (Math.random() * 10) + 10;
        
        heroi.setXp(heroi.getXp() + xpGanho);

        // 3. Verifica Level Up (A cada 100 XP)
        if (heroi.getXp() >= 100) {
            heroi.setNivel(heroi.getNivel() + 1);
            heroi.setXp(0); // Reseta a barra de XP (ou heroi.getXp() - 100 para acumular)
        }

        // 4. Salva a atualização no banco
        return repository.save(heroi);
    }
}
```

## 🌐 Passo 5: Expondo a API (Controller)

Até agora, nosso código só funciona dentro do Java. Para que o Postman, o navegador ou um Front-end consigam conversar com a nossa aplicação, precisamos criar os **Endpoints**.

No Spring Boot, usamos o **Controller** para definir as rotas (URLs) e os verbos HTTP (GET, POST, PUT, DELETE).

### Criando o Controller

Crie um pacote chamado `controller` e o arquivo `AventureiroController.java`.

**Arquivo:** `src/main/java/com/exemplo/guilda/controller/AventureiroController.java`

```java
package com.exemplo.guilda.controller;

import com.exemplo.guilda.model.Aventureiro;
import com.exemplo.guilda.service.AventureiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Diz ao Spring: "Esta classe responde a requisições WEB retornando JSON"
@RequestMapping("/aventureiros") // Define o prefixo da URL: http://localhost:8080/aventureiros
public class AventureiroController {

    @Autowired
    private AventureiroService service;

    // 1. LISTAR (GET)
    // Rota: GET /aventureiros
    @GetMapping
    public List<Aventureiro> listar() {
        return service.listarTodos();
    }

    // 2. CRIAR (POST)
    // Rota: POST /aventureiros
    // @RequestBody: Pega o JSON que enviamos e transforma no objeto Aventureiro
    @PostMapping
    public Aventureiro criar(@RequestBody Aventureiro aventureiro) {
        return service.criar(aventureiro);
    }

    // 3. REALIZAR MISSÃO (PUT)
    // Rota: PUT /aventureiros/{id}/missao
    // @PathVariable: Pega o número da URL ({id}) e joga na variável Long id
    @PutMapping("/{id}/missao")
    public Aventureiro missao(@PathVariable Long id) {
        return service.realizarMissao(id);
    }
}
```

## 🧪 Passo 6: Testando (Hora da Verdade)

Agora que temos o Controller, o Service, o Repository e a Entidade conectados, vamos colocar a nossa API à prova.

Você pode usar ferramentas visuais como **Postman** ou **Insomnia**, ou se preferir ser "hardcore", o terminal com **cURL**.

Certifique-se de que a sua aplicação Spring Boot está a rodar (Run no IntelliJ ou `mvn spring-boot:run`).

---

### 1. Recrutar um Aventureiro (POST)
Primeiro, precisamos popular o nosso banco de dados. Vamos criar um herói.

* **Método:** `POST`
* **URL:** `http://localhost:8080/aventureiros/adicionar`
* **Body (JSON):**

```json
{
    "nome": "Geralt",
    "classe": "GUERREIRO"
}
```
### 2. Ver Guilda (GET)
Agora vamos ver os membros da guilda

* **Método:** `GET`
* **URL:** `http://localhost:8080/aventureiros/guilda`
* **Body (JSON):**

```json
{
    "nome": "Geralt",
    "classe": "GUERREIRO",
    "nivel": 1,
    "xp": 0
}
```

### 3. Vamos para a missão
Agora vamos enviar nosso guerreiro para uma missão

* **Método:** `PUT`
* **URL:** `http://localhost:8080/aventureiros/1/missao`
* **Body (JSON):**

```json
{
    "id": 1,
    "nome": "Geralt",
    "classe": "GUERREIRO",
    "nivel": 1,
    "xp": 15
}
```
