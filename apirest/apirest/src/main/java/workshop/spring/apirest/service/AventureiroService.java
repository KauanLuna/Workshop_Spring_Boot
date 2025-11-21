package workshop.spring.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;
import workshop.spring.apirest.repository.AventureiroRepository;

import java.util.List;
import java.util.Optional;

public class AventureiroService {

    @Autowired
    private final AventureiroRepository aventureiroRepository;

    public AventureiroService(AventureiroRepository aventureiroRepository) {
        this.aventureiroRepository = aventureiroRepository;
    }

    public Aventureiro create(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }

    public Aventureiro update(Aventureiro aventureiro) {
        return aventureiroRepository.save(aventureiro);
    }

    public List<Aventureiro> findAll() {
        return aventureiroRepository.findAll();
    }

    public Optional<Aventureiro> findById(Long id) {
        return aventureiroRepository.findById(id);
    }

    public Aventureiro findByNome(String nome) {
        return aventureiroRepository.findByNome(nome);
    }

    public List<Aventureiro> findByClasse(ClasseRPG classe) {
        return aventureiroRepository.findByClasse(classe);
    }

    public List<Aventureiro> findByNivel(Integer nivel) {
        return aventureiroRepository.findByNivel(nivel);
    }

    public List<Aventureiro> findByXp(Integer xp) {
        return aventureiroRepository.findByXp(xp);
    }
}
