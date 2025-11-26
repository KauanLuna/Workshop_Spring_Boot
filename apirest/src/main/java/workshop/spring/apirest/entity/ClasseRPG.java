package workshop.spring.apirest.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeração que representa as classes de personagens disponíveis no sistema.
 * Cada constante representa uma classe de aventureiro com suas características únicas.
 * 
 * A anotação @JsonValue garante que, ao serializar para JSON, o valor retornado
 * será o nome da constante em letras maiúsculas.
 */
public enum ClasseRPG {
    /** Mago - Especialista em magias arcanas e controle de elementos */
    MAGO("MAGO"),
    
    /** Guerreiro - Especialista em combate corpo a corpo e defesa */
    GUERREIRO("GUERREIRO"),
    
    /** Ladino - Especialista em furtividade e ataques precisos */
    LADINO("LADINO"),
    
    /** Bruxo - Usuário de magias sombrias e pactos */
    BRUXO("BRUXO"),
    
    /** Clérigo - Usuário de magias divinas e suporte */
    CLERIGO("CLERIGO"),
    
    /** Bardo - Usuário de magias através da música e performance */
    BARDO("BARDO"),
    
    /** Arqueiro - Especialista em combate à distância */
    ARQUEIRO("ARQUEIRO");

    /**
     * Nome da classe em formato de string, usado para serialização JSON.
     */
    private final String nome;

    /**
     * Construtor do enum.
     * 
     * @param nome Nome da classe em formato de string
     */
    ClasseRPG(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o nome da classe em formato de string.
     * A anotação @JsonValue faz com que este seja o valor usado ao serializar
     * a enumeração para JSON.
     * 
     * @return Nome da classe em letras maiúsculas
     */
    @JsonValue
    public String getNome() {
        return nome;
    }

    /**
     * Converte uma string para o enum ClasseRPG correspondente.
     * A comparação é case-insensitive.
     * 
     * @param nome Nome da classe para conversão
     * @return A constante do enum correspondente
     * @throws IllegalArgumentException se o nome não corresponder a nenhuma classe
     */
    public static ClasseRPG fromNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("O nome da classe não pode ser nulo");
        }
        
        for (ClasseRPG classe : values()) {
            if (classe.nome.equalsIgnoreCase(nome)) {
                return classe;
            }
        }
        throw new IllegalArgumentException("Classe inválida: " + nome);
    }
}
