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
