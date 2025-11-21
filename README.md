# 🛡️ Workshop Spring Boot: API da Guilda de Aventureiros

Bem-vindos à Guilda! Hoje vamos construir uma API REST completa usando Java e Spring Boot. O objetivo é gerenciar um cadastro de aventureiros de RPG e criar uma lógica para que eles subam de nível.

---

## ✅ Passo 0: Pré-requisitos

Certifique-se de que seu projeto (criado no [start.spring.io](https://start.spring.io)) tem as seguintes dependências no `pom.xml`:
* **Spring Web**
* **Spring Data JPA**
* **MySQL Driver**

---

## ⚙️ Passo 1: Configuração do Banco de Dados

O Spring precisa saber como conectar no seu MySQL. Vamos configurar isso e pedir para ele criar o banco automaticamente.

**Arquivo:** `src/main/resources/application.properties`

```properties
spring.application.name=guilda-api

# --- Conexão MySQL ---
# 'createDatabaseIfNotExist=true' cria o banco sozinho se ele não existir
spring.datasource.url=jdbc:mysql://localhost:3306/guilda_db?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI

# --- Configuração JPA/Hibernate ---
# Mostra o SQL no console (para a gente ver a mágica acontecendo)
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Atualiza a estrutura das tabelas automaticamente baseada no código Java
spring.jpa.hibernate.ddl-auto=update

## 📦 Passo 2: Criando o Domínio (Model)

Vamos definir o que é um Aventureiro e quais Classes de RPG existem. É aqui que transformamos o conceito do jogo em código Java que o banco de dados entende.

### 1. O Enum de Classes
Primeiro, vamos restringir os tipos de heróis. Crie um pacote chamado `model` e dentro dele o arquivo `ClasseRPG.java`.

**Arquivo:** `src/main/java/com/exemplo/guilda/model/ClasseRPG.java`

```java
package com.exemplo.guilda.model;

public enum ClasseRPG {
    GUERREIRO,
    MAGO,
    ARQUEIRO,
    LADINO
}

## 💾 Passo 3: Acesso a Dados (Repository)

Agora que temos a tabela definida (Entidade), precisamos de um componente para "conversar" com o banco de dados (Salvar, Deletar, Buscar). No Spring Boot, quem faz isso é o **Repository**.

A "mágica" aqui é que **não precisamos escrever SQL**. O Spring cria os comandos sozinho baseados nos métodos que chamamos.

### Criando o Repositório

Crie um pacote chamado `repository` e dentro dele a **interface** `AventureiroRepository.java`.

> **Atenção:** Note que isto é uma `interface`, e não uma `class`.

**Arquivo:** `src/main/java/com/exemplo/guilda/repository/AventureiroRepository.java`

```java
package com.exemplo.guilda.repository;

import com.exemplo.guilda.model.Aventureiro;
import com.exemplo.guilda.model.ClasseRPG; // Não esqueça de importar o Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    // ✨ Mágica do Spring Data (Query Methods):
    // Apenas declarando este método, o Spring monta o SQL:
    // SELECT * FROM tb_aventureiros WHERE classe = ?
    List<Aventureiro> findByClasse(ClasseRPG classe);

}

## 🧠 Passo 4: Regra de Negócio (Service)

O **Service** é o cérebro da nossa aplicação. O Controller recebe o pedido, mas é o Service quem decide *o que fazer* com ele.

Aqui vamos implementar a lógica de RPG:
1.  Garantir que todo mundo comece no Nível 1.
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

## 🧪 Passo 6: Testando (Hora da Verdade)

Agora que temos o Controller, o Service, o Repository e a Entidade conectados, vamos colocar a nossa API à prova.

Você pode usar ferramentas visuais como **Postman** ou **Insomnia**, ou se preferir ser "hardcore", o terminal com **cURL**.

Certifique-se de que a sua aplicação Spring Boot está a rodar (Run no IntelliJ/Eclipse ou `mvn spring-boot:run`).

---

### 1. Recrutar um Aventureiro (POST)
Primeiro, precisamos popular o nosso banco de dados. Vamos criar um herói.

* **Método:** `POST`
* **URL:** `http://localhost:8080/aventureiros`
* **Body (JSON):**

```json
{
