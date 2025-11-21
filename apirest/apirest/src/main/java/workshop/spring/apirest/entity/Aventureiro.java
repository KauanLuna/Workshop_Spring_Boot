package workshop.spring.apirest.entity;

import jakarta.persistence.*;

@Table(name = "aventureiro")
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "classe")
    private ClasseRPG classe;

    @Column(name = "nivel")
    private Integer nivel = 1;

    @Column(name = "xp")
    private Integer xp = 0;

    public Aventureiro() {
    }

    public Aventureiro(String nome, ClasseRPG classe) {
        this.nome = nome;
        this.classe = classe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClasseRPG getClasse() {
        return classe;
    }

    public void setClasse(ClasseRPG classe) {
        this.classe = classe;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }
}
