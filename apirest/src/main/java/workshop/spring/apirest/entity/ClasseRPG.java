package workshop.spring.apirest.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClasseRPG {
    MAGO("MAGO"),
    GUERREIRO("GUERREIRO"),
    LADINO("LADINO"),
    BRUXO("BRUXO"),
    CLERIGO("CLERIGO"),
    BARDO("BARDO"),
    ARQUEIRO("ARQUEIRO");

    private final String nome;

    ClasseRPG(String nome) {
        this.nome = nome;
    }

    @JsonValue
    public String getNome() {
        return nome;
    }

    public static ClasseRPG fromNome(String nome) {
        for (ClasseRPG classe : values()) {
            if (classe.nome.equalsIgnoreCase(nome)) {
                return classe;
            }
        }
        throw new IllegalArgumentException("Classe inválida: " + nome);
    }
}
