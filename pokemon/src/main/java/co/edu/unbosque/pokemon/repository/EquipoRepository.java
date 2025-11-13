package co.edu.unbosque.pokemon.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.pokemon.entity.Equipo;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad {@link Equipo}.
 * Incluye métodos personalizados para buscar equipos por ID o nombre.
 * 
 * @author PokéLab
 * @version 1.0
 */
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

	/**
	 * Busca un equipo por su identificador único.
	 *
	 * @param id identificador del equipo
	 * @return un {@link Optional} que contiene el equipo si existe
	 */
	public Optional<Equipo> findById(Long id);

	/**
	 * Busca un equipo por su nombre.
	 *
	 * @param nombre nombre del equipo
	 * @return un {@link Optional} que contiene el equipo si existe
	 */
	public Optional<Equipo> findByNombre(String nombre);
}
