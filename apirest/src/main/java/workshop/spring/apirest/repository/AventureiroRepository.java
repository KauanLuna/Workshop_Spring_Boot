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

/**
 * Repositório para a entidade Aventureiro.
 * Estende JpaRepository para herdar operações CRUD básicas.
 * 
 * As consultas personalizadas são definidas usando a anotação @Query com JPQL.
 * O Spring Data JPA implementa automaticamente os métodos definidos aqui.
 */
@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    /**
     * Retorna todos os aventureiros cadastrados no sistema.
     * Sobrescreve o método padrão para adicionar a anotação @NonNull.
     * 
     * @return Lista de todos os aventureiros (pode ser vazia, mas nunca nula)
     */
    @NonNull
    @Query("SELECT a FROM Aventureiro a")
    List<Aventureiro> findAll();

    /**
     * Busca um aventureiro pelo seu ID.
     * Sobrescreve o método padrão para adicionar a anotação @NonNull e usar JPQL.
     * 
     * @param id ID do aventureiro a ser buscado
     * @return Um Optional contendo o aventureiro, se encontrado
     */
    @NonNull
    @Override
    @Query("SELECT a FROM Aventureiro a WHERE a.id = :id")
    Optional<Aventureiro> findById(@Param("id") Long id);

    /**
     * Busca um aventureiro pelo nome exato.
     * 
     * @param nome Nome exato do aventureiro a ser buscado
     * @return O aventureiro encontrado ou null se não existir
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.nome = :nome")
    Aventureiro findByNome(@Param("nome") String nome);

    /**
     * Busca todos os aventureiros de uma determinada classe.
     * 
     * @param classe A classe dos aventureiros a serem buscados
     * @return Lista de aventureiros da classe especificada (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.classe = :classe")
    List<Aventureiro> findByClasse(@Param("classe") ClasseRPG classe);

    /**
     * Busca todos os aventureiros de um determinado nível.
     * 
     * @param nivel Nível dos aventureiros a serem buscados
     * @return Lista de aventureiros do nível especificado (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.nivel = :nivel")
    List<Aventureiro> findByNivel(@Param("nivel") Integer nivel);

    /**
     * Busca todos os aventureiros com uma determinada quantidade de XP.
     * 
     * @param xp Quantidade exata de XP para busca
     * @return Lista de aventureiros com a quantidade de XP especificada (pode ser vazia)
     */
    @Query("SELECT a FROM Aventureiro a WHERE a.xp = :xp")
    List<Aventureiro> findByXp(@Param("xp") Integer xp);
}
