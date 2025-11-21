package workshop.spring.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;
import workshop.spring.apirest.service.AventureiroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    @Autowired
    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    @GetMapping("/listar")
    public List<Aventureiro> listarAventureiros() {
        return aventureiroService.findAll();
    }

    @PostMapping("/criar")
    public Aventureiro criarAventureiro(@RequestBody Aventureiro aventureiro) {
        return aventureiroService.create(aventureiro);
    }

    @GetMapping("/buscar/{id}")
    public Optional<Aventureiro> buscarAventureiroPorId(@PathVariable Long id) {
        return aventureiroService.findById(id);
    }

    @GetMapping("/buscar/{nome}")
    public Aventureiro buscarAventureiroPorNome(@PathVariable String nome) {
        return aventureiroService.findByNome(nome);
    }

    @GetMapping("/buscar/{classe}")
    public List<Aventureiro> buscarAventureirosPorClasse(@PathVariable ClasseRPG classe) {
        return aventureiroService.findByClasse(classe);
    }

    @GetMapping("/buscar/{nivel}")
    public List<Aventureiro> buscarAventureiroPorNivel(@PathVariable Integer nivel) {
        return aventureiroService.findByNivel(nivel);
    }
}
