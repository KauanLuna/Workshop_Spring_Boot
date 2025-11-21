package workshop.spring.apirest.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import workshop.spring.apirest.entity.Aventureiro;
import workshop.spring.apirest.entity.ClasseRPG;

import java.util.List;
import java.util.Optional;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @NonNull
    @Query("SELECT a FROM Aventureiro a")
    List<Aventureiro> findAll();

    @NonNull
    @Query("SELECT a FROM Aventureiro a WHERE a.id = :id")
    Optional<Aventureiro> findById(@Param("id")  Long id);

    @Query("SELECT a FROM Aventureiro a WHERE a.nome = :nome")
    Aventureiro findByNome(@Param("nome") String nome);

    @Query("SELECT a FROM Aventureiro a WHERE a.classe = :classe")
    List<Aventureiro> findByClasse(@Param("classe") ClasseRPG classe);

    @Query("SELECT a FROM Aventureiro a WHERE a.nivel = :nivel")
    List<Aventureiro> findByNivel(@Param("nivel") Integer nivel);

    @Query("SELECT a FROM Aventureiro a WHERE a.xp = :xp")
    List<Aventureiro> findByXp(@Param("xp") Integer xp);
}
