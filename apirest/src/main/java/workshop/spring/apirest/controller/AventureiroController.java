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
