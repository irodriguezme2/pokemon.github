package co.edu.unbosque.pokemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Batalla;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad
 * {@link Batalla}. Extiende {@link JpaRepository} para acceder a métodos
 * predeterminados de persistencia.
 * 
 * @author PokéLab
 * @version 1.0
 */
public interface BatallaRepository extends JpaRepository<Batalla, Long> {

}
