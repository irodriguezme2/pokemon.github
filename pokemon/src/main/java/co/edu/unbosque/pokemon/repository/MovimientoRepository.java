package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	public Optional<Movimiento> findByNombre(String nombre);
}
