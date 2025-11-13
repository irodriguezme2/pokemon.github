package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Movimiento;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad
 * {@link Movimiento}. Permite buscar movimientos por su nombre.
 * 
 * @author PokéLab
 * @version 1.0
 */
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	/**
	 * Busca un movimiento por su nombre.
	 *
	 * @param nombre nombre del movimiento
	 * @return un {@link Optional} que contiene el movimiento si existe
	 */
	public Optional<Movimiento> findByNombre(String nombre);
}
