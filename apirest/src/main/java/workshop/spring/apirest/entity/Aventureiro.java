package workshop.spring.apirest.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aventureiro")
public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false)
    private ClasseRPG classe;

    @Column(name = "nivel", nullable = false)
    private Integer nivel = 1;

    @Column(name = "xp", nullable = false)
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
