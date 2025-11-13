package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Pokemon;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad
 * {@link Pokemon}. Permite buscar Pokémon por su identificador o nombre.
 * 
 * @author PokéLab
 * @version 1.0
 */
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

	/**
	 * Busca un Pokémon por su identificador.
	 *
	 * @param id identificador del Pokémon
	 * @return un {@link Optional} que contiene el Pokémon si existe
	 */
	public Optional<Pokemon> findById(Long id);


	/**
	 * Busca un Pokémon por su nombre.
	 *
	 * @param nombre nombre del Pokémon
	 * @return un {@link Optional} que contiene el Pokémon si existe
	 */
	public Optional<Pokemon> findByNombre(String nombre);
}
