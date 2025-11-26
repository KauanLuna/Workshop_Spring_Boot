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

 [Clique aqui para visitar a documentação oficial do Spring Boot](https://docs.spring.io/spring-boot/documentation.html)

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
- `BRUXO`
- `CLERIGO`
- `BARDO`

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

### Criando a Repository

Crie um pacote chamado `repository` e o arquivo `AventureiroRepository.java`

**Arquivo:** ``

```java
package workshop.spring.apirest.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade Aventureiro.
 * Estende JpaRepository para herdar operações CRUD básicas.
 *
 * As consultas personalizadas são definidas usando a anotação @Query com JPQL.
 * O Spring Data JPA implementa automaticamente os métodos definidos aqui.
 */
@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    /**
     * Retorna todos os aventureiros cadastrados no sistema.
     * Sobrescreve o método padrão para adicionar a anotação @NonNull.
     *
     * @return Lista de todos os aventureiros (pode ser vazia, mas nunca nula)
     */
    @NonNull
    @Query("SELECT a FROM Aventureiro a")
    List<Aventureiro> findAll();

    /**
     * Busca um aventureiro pelo seu ID.
     * Sobrescreve o método padrão para adicionar a anotação @NonNull e usar JPQL.
     *
     * @param id ID do aventureiro a ser buscado
     * @return Um Optional contendo o aventureiro, se encontrado
     */
    @NonNull
    @Override
    @Query("SELECT a FROM Aventureiro a WHERE a.id = :id")
    Optional<Aventureiro> findById(@Param("id") Long id);

    /**
     * Busca um aventureiro pelo nome exato.
     *
     * @param nome Nome exato do aventureiro a ser buscado
     * @return O aventureiro encontrado ou null se não existir
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.nome = :nome")
    Aventureiro findByNome(@Param("nome") String nome);

    /**
     * Busca todos os aventureiros de uma determinada classe.
     *
     * @param classe A classe dos aventureiros a serem buscados
     * @return Lista de aventureiros da classe especificada (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.classe = :classe")
    List<Aventureiro> findByClasse(@Param("classe") ClasseRPG classe);

    /**
     * Busca todos os aventureiros de um determinado nível.
     *
     * @param nivel Nível dos aventureiros a serem buscados
     * @return Lista de aventureiros do nível especificado (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.nivel = :nivel")
    List<Aventureiro> findByNivel(@Param("nivel") Integer nivel);

    /**
     * Busca todos os aventureiros com uma determinada quantidade de XP.
     *
     * @param xp Quantidade exata de XP para busca
     * @return Lista de aventureiros com a quantidade de XP especificada (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.xp = :xp")
    List<Aventureiro> findByXp(@Param("xp") Integer xp);
}
```

### Criando a Service

Crie um pacote chamado `service` e o arquivo `AventureiroService.java`.

**Arquivo:** `src/main/java/com/exemplo/guilda/service/AventureiroService.java`

```java
package workshop.spring.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;
import workshop.spring.apirest.repository.AventureiroRepository;

import java.util.List;
import java.util.Optional;

/**
 * 🏦 SERVIÇO: GERENCIADOR DE REGRAS DE NEGÓCIO
 *
 * Esta classe é o coração da nossa aplicação, contendo toda a lógica de negócios
 * relacionada aos aventureiros. Ela atua como intermediária entre o controlador
 * (que lida com requisições HTTP) e o repositório (que acessa o banco de dados).
 *
 * Responsabilidades principais:
 * - Validar dados de entrada
 * - Aplicar regras de negócio
 * - Orquestrar operações entre diferentes componentes
 * - Tratar exceções e erros
 */
@Service
public class AventureiroService {

    /**
     * 🔌 REPOSITÓRIO
     *
     * O repositório é responsável por todas as operações de banco de dados.
     * É declarado como 'final' para garantir imutabilidade após a inicialização.
     */
    private final AventureiroRepository aventureiroRepository;

    /**
     * Construtor que recebe a dependência do repositório.
     *
     * @param aventureiroRepository O repositório que será injetado automaticamente pelo Spring
     *
     * Boas práticas:
     * 1. Injeção por construtor é preferível a @Autowired em campos
     * 2. Facilita testes unitários
     * 3. Torna as dependências explícitas
     */
    @Autowired
    public AventureiroService(AventureiroRepository aventureiroRepository) {
        this.aventureiroRepository = aventureiroRepository;
    }

    /**
     * Cria um novo aventureiro no sistema.
     *
     * @param aventureiro O aventureiro a ser criado
     * @return O aventureiro salvo com o ID gerado
     * @throws IllegalArgumentException se o aventureiro for nulo ou já possuir um ID
     */
    public Aventureiro create(Aventureiro aventureiro) {
        if (aventureiro == null) {
            throw new IllegalArgumentException("O aventureiro não pode ser nulo");
        }
        if (aventureiro.getId() != null) {
            throw new IllegalArgumentException("Um novo aventureiro não pode ter um ID definido");
        }
        return aventureiroRepository.save(aventureiro);
    }

    /**
     * Atualiza um aventureiro existente.
     *
     * @param aventureiro O aventureiro com as atualizações
     * @return O aventureiro atualizado
     * @throws IllegalArgumentException se o aventureiro for nulo ou não tiver um ID
     * @throws RuntimeException se o aventureiro não for encontrado
     */
    public Aventureiro update(Aventureiro aventureiro) {
        if (aventureiro == null) {
            throw new IllegalArgumentException("O aventureiro não pode ser nulo");
        }
        if (aventureiro.getId() == null) {
            throw new IllegalArgumentException("ID do aventureiro é obrigatório para atualização");
        }

        // Verifica se o aventureiro existe antes de tentar atualizar
        if (!aventureiroRepository.existsById(aventureiro.getId())) {
            throw new RuntimeException("Aventureiro não encontrado com o ID: " + aventureiro.getId());
        }

        return aventureiroRepository.save(aventureiro);
    }

    /**
     * Remove um aventureiro pelo seu ID.
     *
     * @param id ID do aventureiro a ser removido
     * @throws IllegalArgumentException se o ID for nulo
     */
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        aventureiroRepository.deleteById(id);
    }

    /**
     * Retorna todos os aventureiros cadastrados no sistema.
     *
     * @return Lista de todos os aventureiros (pode ser vazia, mas nunca nula)
     */
    public List<Aventureiro> findAll() {
        return aventureiroRepository.findAll();
    }

    /**
     * Busca um aventureiro pelo seu ID.
     *
     * @param id ID do aventureiro a ser buscado
     * @return Um Optional contendo o aventureiro, se encontrado
     * @throws IllegalArgumentException se o ID for nulo
     */
    public Optional<Aventureiro> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return aventureiroRepository.findById(id);
    }

    /**
     * Busca um aventureiro pelo seu nome exato.
     *
     * @param nome Nome do aventureiro a ser buscado
     * @return O aventureiro encontrado ou null se não existir
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public Aventureiro findByNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        return aventureiroRepository.findByNome(nome);
    }

    /**
     * Busca todos os aventureiros de uma determinada classe.
     *
     * @param classe Classe dos aventureiros a serem buscados
     * @return Lista de aventureiros da classe especificada (pode ser vazia)
     * @throws IllegalArgumentException se a classe for nula
     */
    public List<Aventureiro> findByClasse(ClasseRPG classe) {
        if (classe == null) {
            throw new IllegalArgumentException("Classe não pode ser nula");
        }
        return aventureiroRepository.findByClasse(classe);
    }

    /**
     * Busca todos os aventureiros de um determinado nível.
     *
     * @param nivel Nível dos aventureiros a serem buscados
     * @return Lista de aventureiros do nível especificado (pode ser vazia)
     * @throws IllegalArgumentException se o nível for nulo ou menor que 1
     */
    public List<Aventureiro> findByNivel(Integer nivel) {
        if (nivel == null || nivel < 1) {
            throw new IllegalArgumentException("Nível deve ser maior ou igual a 1");
        }
        return aventureiroRepository.findByNivel(nivel);
    }

    /**
     * Busca todos os aventureiros com uma determinada quantidade de XP.
     *
     * @param xp Quantidade exata de XP para busca
     * @return Lista de aventureiros com a quantidade de XP especificada (pode ser vazia)
     * @throws IllegalArgumentException se o XP for nulo ou negativo
     */
    public List<Aventureiro> findByXp(Integer xp) {
        if (xp == null || xp < 0) {
            throw new IllegalArgumentException("XP não pode ser negativo");
        }
        return aventureiroRepository.findByXp(xp);
    }

    /**
     * 🎯 REALIZAR MISSÃO (CORAÇÃO DO RPG!)
     *
     * Esta é a funcionalidade mais importante do sistema! Permite que um aventureiro
     * participe de uma missão, ganhe experiência e suba de nível.
     *
     * Funcionamento:
     * 1. Busca o aventureiro pelo ID
     * 2. Gera XP aleatório (10-20 pontos)
     * 3. Atualiza o XP do aventureiro
     * 4. Se XP >= 100, sobe de nível e zera o XP
     *
     * @param id ID do aventureiro que irá para a missão
     * @return O aventureiro atualizado com novo XP/nível
     * @throws IllegalArgumentException Se o ID for inválido
     * @throws RuntimeException Se o aventureiro não for encontrado
     *
     * Exemplo de fluxo:
     * - Aventureiro nível 1, XP 0 → Missão → +15 XP → Nível 1, XP 15
     * - Aventureiro nível 1, XP 95 → Missão → +10 XP → Nível 2, XP 5
     */
    public Aventureiro realizarMissao(Long id) {
        // 1. Validação de entrada
        if (id == null) {
            throw new IllegalArgumentException("🚨 ID do aventureiro é obrigatório!");
        }

        // 2. Busca o aventureiro no banco de dados
        Aventureiro heroi = aventureiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Aventureiro não encontrado com ID: " + id));

        // 3. Gera XP aleatório (10-20)
        int xpGanho = (int) (Math.random() * 10) + 10;
        System.out.println("✨ " + heroi.getNome() + " ganhou " + xpGanho + " XP na missão!");

        // 4. Atualiza o XP do herói
        int novoXp = heroi.getXp() + xpGanho;
        heroi.setXp(novoXp);

        // 5. Verifica subida de nível
        if (novoXp >= 100) {
            int nivelAnterior = heroi.getNivel();
            heroi.setNivel(nivelAnterior + 1);
            heroi.setXp(0); // Reseta o XP
            System.out.println("🎉 " + heroi.getNome() + " subiu para o nível " + heroi.getNivel() + "!");
        }

        // 6. Salva e retorna o herói atualizado
        return aventureiroRepository.save(heroi);
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
package workshop.spring.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;
import workshop.spring.apirest.service.AventureiroService;

import java.util.List;
import java.util.Optional;

/**
 * 🎮 CONTROLLER: GERENCIADOR DE AVENTUREIROS
 *
 * Este é o controlador REST que gerencia todas as operações relacionadas aos aventureiros.
 * Ele é responsável por receber as requisições HTTP e direcioná-las para o serviço apropriado.
 *
 * Todas as rotas começam com '/aventureiros'.
 * Exemplo: http://localhost:8080/aventureiros/listar
 *
 * Este controlador segue o padrão RESTful e inclui operações CRUD completas.
 */
@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    /**
     * 🔌 INJEÇÃO DE DEPENDÊNCIA
     *
     * O serviço que contém toda a lógica de negócios para gerenciar aventureiros.
     * Injetado via construtor para melhor testabilidade e manutenção.
     */
    private final AventureiroService aventureiroService;

    /**
     * Construtor que recebe a dependência do serviço.
     *
     * @param aventureiroService O serviço que será injetado automaticamente pelo Spring
     *
     * Como usar:
     * 1. O Spring detecta automaticamente esta classe como um controlador
     * 2. Cria uma instância do AventureiroService
     * 3. Injeta a instância no construtor
     */
    @Autowired
    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    /**
     * 📋 LISTAR TODOS OS AVENTUREIROS
     *
     * Rota: GET /aventureiros/listar
     *
     * Retorna uma lista com todos os aventureiros cadastrados na guilda.
     *
     * Exemplo de uso:
     * ```
     * GET http://localhost:8080/aventureiros/listar
     * ```
     *
     * @return Lista de aventureiros com status 200 (OK)
     * @apiNote Não requer parâmetros
     */
    @GetMapping("/listar")
    public ResponseEntity<List<Aventureiro>> listarAventureiros() {
        // 1. Busca todos os aventureiros no banco de dados
        List<Aventureiro> aventureiros = aventureiroService.findAll();

        // 2. Retorna a lista com status HTTP 200 (OK)
        return new ResponseEntity<>(aventureiros, HttpStatus.OK);
    }

    /**
     * ✨ CRIAR NOVO AVENTUREIRO
     *
     * Rota: POST /aventureiros/criar
     *
     * Adiciona um novo aventureiro à guilda. O aventureiro começa no nível 1 com 0 de XP.
     *
     * Exemplo de requisição:
     * ```json
     * POST http://localhost:8080/aventureiros/criar
     * {
     *     "nome": "Geralt",
     *     "classe": "GUERREIRO"
     * }
     * ```
     *
     * @param aventureiro Objeto JSON com os dados do aventureiro
     * @return O aventureiro criado com status 201 (Created)
     * @throws IllegalArgumentException Se os dados forem inválidos
     *
     * Classes disponíveis: GUERREIRO, MAGO, ARQUEIRO, LADINO, BRUXO, CLERIGO, BARDO
     */
    @PostMapping("/criar")
    public ResponseEntity<Aventureiro> criarAventureiro(@RequestBody Aventureiro aventureiro) {
        // 1. Valida e salva o novo aventureiro
        Aventureiro novoAventureiro = aventureiroService.create(aventureiro);

        // 2. Retorna o aventureiro criado com status 201 (Created)
        return new ResponseEntity<>(novoAventureiro, HttpStatus.CREATED);
    }

    /**
     * Atualiza os dados de um aventureiro existente.
     *
     * @param aventureiro Dados atualizados do aventureiro (no corpo da requisição)
     * @return O aventureiro atualizado e status HTTP 200 (OK)
     * @apiNote PUT /aventureiros/atualizar
     * @throws IllegalArgumentException se o ID for nulo ou os dados forem inválidos
     * @throws RuntimeException se o aventureiro não for encontrado
     */
    @PutMapping("/atualizar")
    public ResponseEntity<Aventureiro> atualizarAventureiro(@RequestBody Aventureiro aventureiro) {
        Aventureiro aventureiroAtualizado = aventureiroService.update(aventureiro);
        return new ResponseEntity<>(aventureiroAtualizado, HttpStatus.OK);
    }

    /**
     * Remove um aventureiro pelo seu ID.
     *
     * @param id ID do aventureiro a ser removido
     * @return Resposta vazia com status HTTP 204 (No Content)
     * @apiNote DELETE /aventureiros/deletar/{id}
     * @throws IllegalArgumentException se o ID for nulo
     */
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarAventureiro(@PathVariable Long id) {
        aventureiroService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 🔍 BUSCAR AVENTUREIRO POR ID
     *
     * Rota: GET /aventureiros/buscar/{id}
     *
     * Busca um aventureiro específico pelo seu ID único.
     *
     * Exemplo de uso:
     * ```
     * GET http://localhost:8080/aventureiros/buscar/1
     * ```
     *
     * @param id ID do aventureiro (não pode ser nulo)
     * @return O aventureiro encontrado (status 200) ou 404 se não encontrado
     *
     * Dica: Use o ID retornado ao criar um aventureiro
     */
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<Aventureiro> buscarAventureiroPorId(@PathVariable Long id) {
        return aventureiroService.findById(id)
                .map(aventureiro -> new ResponseEntity<>(aventureiro, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Busca um aventureiro pelo seu nome exato.
     *
     * @param nome Nome do aventureiro a ser buscado
     * @return O aventureiro encontrado e status HTTP 200 (OK) ou 404 (Not Found)
     * @apiNote GET /aventureiros/buscar/nome/{nome}
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<Aventureiro> buscarAventureiroPorNome(@PathVariable String nome) {
        try {
            Aventureiro aventureiro = aventureiroService.findByNome(nome);
            return new ResponseEntity<>(aventureiro, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Busca aventureiros por classe.
     *
     * @param classe Classe dos aventureiros a serem buscados
     * @return Lista de aventureiros da classe especificada e status HTTP 200 (OK)
     * @apiNote GET /aventureiros/buscar/classe/{classe}
     * @throws IllegalArgumentException se a classe for inválida
     */
    @GetMapping("/buscar/classe/{classe}")
    public ResponseEntity<List<Aventureiro>> buscarAventureirosPorClasse(@PathVariable ClasseRPG classe) {
        try {
            List<Aventureiro> aventureiros = aventureiroService.findByClasse(classe);
            return new ResponseEntity<>(aventureiros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Busca aventureiros por nível.
     *
     * @param nivel Nível dos aventureiros a serem buscados
     * @return Lista de aventureiros do nível especificado e status HTTP 200 (OK)
     * @apiNote GET /aventureiros/buscar/nivel/{nivel}
     * @throws IllegalArgumentException se o nível for inválido
     */
    @GetMapping("/buscar/nivel/{nivel}")
    public ResponseEntity<List<Aventureiro>> buscarAventureiroPorNivel(@PathVariable Integer nivel) {
        try {
            List<Aventureiro> aventureiros = aventureiroService.findByNivel(nivel);
            return new ResponseEntity<>(aventureiros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Busca aventureiros por quantidade de XP.
     *
     * @param xp Quantidade exata de XP para busca
     * @return Lista de aventureiros com a quantidade de XP especificada e status HTTP 200 (OK)
     * @apiNote GET /aventureiros/buscar/xp/{xp}
     * @throws IllegalArgumentException se o XP for inválido
     */
    @GetMapping("/buscar/xp/{xp}")
    public ResponseEntity<List<Aventureiro>> buscarAventureiroPorXp(@PathVariable Integer xp) {
        try {
            List<Aventureiro> aventureiros = aventureiroService.findByXp(xp);
            return new ResponseEntity<>(aventureiros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 🎯 REALIZAR MISSÃO
     *
     * Rota: PUT /aventureiros/missao/{id}
     *
     * Envia um aventureiro em uma missão perigosa! A cada missão:
     * - O aventureiro ganha XP aleatório (10-20)
     - Ao atingir 100 XP, sobe de nível e o XP é zerado
     - O nível é incrementado em 1

     * Exemplo de uso:
     * ```
     * PUT http://localhost:8080/aventureiros/missao/1
     * ```
     *
     * @param id ID do aventureiro que irá para a missão
     * @return O aventureiro atualizado após a missão
     *
     * Exemplo de resposta (nível 1, XP 15):
     * {
     *     "id": 1,
     *     "nome": "Geralt",
     *     "classe": "GUERREIRO",
     *     "nivel": 1,
     *     "xp": 15
     * }
     */
    @PutMapping("/missao/{id}")
    public ResponseEntity<Aventureiro> realizarMissao(@PathVariable Long id) {
        try {
            // 1. Executa a missão e atualiza o aventureiro
            Aventureiro aventureiro = aventureiroService.realizarMissao(id);

            // 2. Retorna o aventureiro atualizado
            return new ResponseEntity<>(aventureiro, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // ID inválido
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            // Aventureiro não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
