package co.edu.unbosque.pokemon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.pokemon.entity.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

	public Optional<Equipo> findById(Long id);

	public Optional<Equipo> findByNombre(String nombre);
}
