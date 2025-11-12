package co.edu.unbosque.pokemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Ability;
import java.util.List;
import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
	public Optional<Ability> findByNombre(String nombre);

}
