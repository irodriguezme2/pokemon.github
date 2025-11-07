package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.entity.Batalla;
import co.edu.unbosque.pokemon.service.BatallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batalla")
@CrossOrigin(origins = "*")
public class BatallaController {

	@Autowired
	private BatallaService batallaService;

	// Guardamos la batalla actual (en este caso en memoria)
	private Batalla batallaActual;

	// ✅ Iniciar una batalla (Jugador vs CPU o Jugador vs Jugador)
	@PostMapping("/iniciar")
	public Batalla iniciarBatalla(@RequestBody Batalla batalla) {
		if (batalla.getEquipo1() == null || batalla.getEquipo2() == null) {
			throw new IllegalArgumentException("Ambos equipos deben estar presentes.");
		}

		batallaActual = batallaService.iniciarBatalla(batalla);
		return batallaActual;
	}

	// ✅ Ejecutar turno: puede ser movimiento o habilidad
	@PostMapping("/turno")
	public Batalla ejecutarTurno(@RequestParam(required = false) String movimientoSeleccionado,
			@RequestParam(defaultValue = "false") boolean usarHabilidad) {

		if (batallaActual == null) {
			throw new IllegalStateException("No hay una batalla activa.");
		}

		batallaActual = batallaService.ejecutarTurno(batallaActual, movimientoSeleccionado, usarHabilidad);
		return batallaActual;
	}

	// ✅ Obtener estado actual de la batalla (para refrescar datos en el front)
	@GetMapping("/estado")
	public Batalla obtenerEstadoBatalla() {
		if (batallaActual == null) {
			throw new IllegalStateException("No hay una batalla activa.");
		}
		return batallaActual;
	}

	// ✅ Reiniciar la batalla (opcional)
	@PostMapping("/reiniciar")
	public Batalla reiniciarBatalla(@RequestBody Batalla nuevaBatalla) {
		batallaActual = batallaService.iniciarBatalla(nuevaBatalla);
		return batallaActual;
	}

	// ✅ Verificar si hay una batalla activa (para el frontend)
	@GetMapping("/activa")
	public boolean hayBatallaActiva() {
		return batallaActual != null;
	}
}
