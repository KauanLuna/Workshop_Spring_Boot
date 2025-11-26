package workshop.spring.apirest.entity;

import jakarta.persistence.*;

/**
 * Classe que representa um Aventureiro no sistema.
 * Esta é uma entidade JPA que será mapeada para a tabela 'aventureiro' no banco de dados.
 * Cada instância desta classe representa uma linha na tabela de aventureiros.
 */
@Entity
@Table(name = "aventureiro")
public class Aventureiro {
    /**
     * Identificador único do aventureiro no banco de dados.
     * É gerado automaticamente pelo banco de dados usando auto-incremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do aventureiro.
     * Campo obrigatório (não pode ser nulo) e será armazenado na coluna 'nome' da tabela.
     */
    @Column(name = "nome", nullable = false)
    private String nome;

    /**
     * Classe do aventureiro (ex: MAGO, GUERREIRO, etc).
     * Armazenado como uma string no banco de dados (usando @Enumerated(EnumType.STRING)).
     * Campo obrigatório (não pode ser nulo).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false)
    private ClasseRPG classe;

    /**
     * Nível atual do aventureiro.
     * Inicia no nível 1 e aumenta conforme o aventureiro ganha experiência.
     * Valor padrão: 1
     */
    @Column(name = "nivel", nullable = false)
    private Integer nivel = 1;

    /**
     * Pontos de experiência (XP) atuais do aventureiro.
     * Aumenta ao completar missões e é zerado ao subir de nível.
     * Valor padrão: 0
     */
    @Column(name = "xp", nullable = false)
    private Integer xp = 0;

    /**
     * Construtor padrão vazio necessário para o JPA.
     * Não deve ser usado diretamente, apenas pelo framework.
     */
    public Aventureiro() {
    }

    /**
     * Construtor para criar um novo aventureiro.
     * Inicializa o aventureiro com nível 1 e 0 de XP.
     * 
     * @param nome Nome do aventureiro
     * @param classe Classe do aventureiro (enum ClasseRPG)
     */
    public Aventureiro(String nome, ClasseRPG classe) {
        this.nome = nome;
        this.classe = classe;
        // Nível e XP são inicializados com valores padrão
    }

    /**
     * Retorna o ID único do aventureiro.
     * 
     * @return O ID do aventureiro ou null se ainda não foi persistido
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID do aventureiro.
     * Normalmente não é chamado diretamente, pois o JPA gerencia isso automaticamente.
     * 
     * @param id O ID a ser definido
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna o nome do aventureiro.
     * 
     * @return O nome do aventureiro
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do aventureiro.
     * 
     * @param nome O nome a ser definido (não pode ser nulo)
     * @throws IllegalArgumentException se o nome for nulo ou vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do aventureiro não pode ser vazio");
        }
        this.nome = nome.trim();
    }

    /**
     * Retorna a classe do aventureiro.
     * 
     * @return A classe do aventureiro (ex: MAGO, GUERREIRO, etc)
     */
    public ClasseRPG getClasse() {
        return classe;
    }

    /**
     * Define a classe do aventureiro.
     * 
     * @param classe A classe a ser definida (não pode ser nula)
     * @throws IllegalArgumentException se a classe for nula
     */
    public void setClasse(ClasseRPG classe) {
        if (classe == null) {
            throw new IllegalArgumentException("A classe do aventureiro não pode ser nula");
        }
        this.classe = classe;
    }

    /**
     * Retorna o nível atual do aventureiro.
     * O nível inicia em 1 e aumenta conforme o aventureiro ganha experiência.
     * 
     * @return O nível atual do aventureiro (sempre maior ou igual a 1)
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * Define o nível do aventureiro.
     * 
     * @param nivel O nível a ser definido (deve ser maior ou igual a 1)
     * @throws IllegalArgumentException se o nível for menor que 1
     */
    public void setNivel(Integer nivel) {
        if (nivel == null || nivel < 1) {
            throw new IllegalArgumentException("O nível deve ser maior ou igual a 1");
        }
        this.nivel = nivel;
    }

    /**
     * Retorna os pontos de experiência (XP) atuais do aventureiro.
     * 
     * @return A quantidade atual de XP (sempre maior ou igual a 0)
     */
    public Integer getXp() {
        return xp;
    }

    /**
     * Define os pontos de experiência (XP) do aventureiro.
     * 
     * @param xp A quantidade de XP a ser definida (deve ser maior ou igual a 0)
     * @throws IllegalArgumentException se o XP for negativo
     */
    public void setXp(Integer xp) {
        if (xp == null || xp < 0) {
            throw new IllegalArgumentException("O XP não pode ser negativo");
        }
        this.xp = xp;
    }
}
