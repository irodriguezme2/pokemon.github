package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

	public Optional<Pokemon> findById(Long id);

	public Optional<Pokemon> findByNombre(String nombre);
}
