package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.Batalla;
import co.edu.unbosque.pokemon.entity.Equipo;
import co.edu.unbosque.pokemon.entity.Movimiento;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.entity.Pokemon.Estado;
import co.edu.unbosque.pokemon.util.TypeEffectiveness;

/**
 * Servicio encargado de gestionar la lógica de combate entre equipos Pokémon.
 * Controla los turnos, efectos de estado, habilidades y cálculo de daño durante
 * la batalla.
 * 
 * @author PokéLab
 * @version 1.0
 */
@Service
public class BatallaService {

	private final Random random = new Random();


	/**
	 * Inicia una batalla, configurando equipos, HP y estados iniciales.
	 * 
	 * @param batalla la instancia de {@link Batalla} a iniciar
	 * @return la batalla con valores inicializados
	 * @throws IllegalArgumentException si alguno de los equipos es nulo
	 */
	public Batalla iniciarBatalla(Batalla batalla) {
		if (batalla == null)
			throw new IllegalArgumentException("Batalla nula");
		if (batalla.getEquipo1() == null)
			throw new IllegalArgumentException("Equipo 1 no puede ser nulo");

		if (batalla.getEquipo2() == null)
			throw new IllegalArgumentException("Equipo 2 no puede ser nulo");

		if (batalla.getHistorialTurnos() == null)
			batalla.setHistorialTurnos(new ArrayList<>());
		batalla.getHistorialTurnos().add("🔥 Comienza la batalla entre " + batalla.getEquipo1().getNombre() + " y "
				+ batalla.getEquipo2().getNombre());

		inicializarEquipo(batalla.getEquipo1());
		inicializarEquipo(batalla.getEquipo2());

		if (batalla.getTurnoActual() == null || batalla.getTurnoActual().isBlank())
			batalla.setTurnoActual("equipo1");

		batalla.setBatallaActiva(true);
		batalla.setEstado("EN_PROGRESO");

		return batalla;
	}

	/**
	 * Inicializa los Pokémon de un equipo estableciendo HP y estado base.
	 * 
	 * @param equipo el equipo a inicializar
	 */
	private void inicializarEquipo(Equipo equipo) {
		if (equipo == null || equipo.getListaPokemon() == null)
			return;
		for (Pokemon p : equipo.getListaPokemon()) {
			p.setHpActual(p.getStatValue("hp")); // usa stat "hp"
			if (p.getEstado() == null)
				p.setEstado(Estado.NORMAL);
		}
	}

	/**
	 * Ejecuta un turno de la batalla según el movimiento o habilidad seleccionada.
	 * 
	 * @param batalla                la batalla en curso
	 * @param movimientoSeleccionado nombre del movimiento elegido
	 * @param usarHabilidad          true si se desea usar la habilidad del Pokémon
	 *                               activo
	 * @return la batalla actualizada tras el turno
	 * @throws IllegalStateException si no hay una batalla activa
	 */
	public Batalla ejecutarTurno(Batalla batalla, String movimientoSeleccionado, boolean usarHabilidad) {
		if (batalla == null || !Boolean.TRUE.equals(batalla.isBatallaActiva())) {
			throw new IllegalStateException("No hay una batalla activa.");
		}

		Equipo atacanteEquipo = batalla.getTurnoActual().equals("equipo1") ? batalla.getEquipo1()
				: batalla.getEquipo2();
		Equipo defensorEquipo = batalla.getTurnoActual().equals("equipo1") ? batalla.getEquipo2()
				: batalla.getEquipo1();

		Pokemon atacante = obtenerPokemonActivo(atacanteEquipo);
		Pokemon defensor = obtenerPokemonActivo(defensorEquipo);

		if (atacante == null || defensor == null) {
			finalizarSiCorresponde(batalla, atacanteEquipo, defensorEquipo);
			return batalla;
		}

		boolean puedeActuar = aplicarEfectosAntesTurno(atacante, batalla.getHistorialTurnos());
		if (!puedeActuar) {
			aplicarEfectosFinTurno(atacante, batalla.getHistorialTurnos());
			aplicarEfectosFinTurno(defensor, batalla.getHistorialTurnos());

			cambiarTurno(batalla);
			return batalla;
		}

		if (usarHabilidad) {
			aplicarHabilidadActiva(atacante, defensor, batalla.getHistorialTurnos());
		} else {

			Movimiento movimiento = elegirMovimientoPorNombre(atacante, movimientoSeleccionado);
			if (movimiento == null) {
				batalla.getHistorialTurnos().add("❌ " + atacante.getNombre() + " no tiene movimientos válidos.");
			} else {
				realizarAtaque(atacante, defensor, movimiento, batalla.getHistorialTurnos());
			}
		}

		aplicarEfectosPostGolpe(atacante, defensor, batalla.getHistorialTurnos());

		if (defensor.getHpActual() <= 0) {
			defensor.setHpActual(0);
			defensor.setEstado(Estado.NORMAL);
			batalla.getHistorialTurnos().add("💀 " + defensor.getNombre() + " ha sido derrotado!");

			if (!hayPokemonesVivos(defensorEquipo)) {
				batalla.getHistorialTurnos().add("🏆 El equipo ganador es: " + atacanteEquipo.getNombre());
				batalla.setEquipoGanador(atacanteEquipo);
				batalla.setEquipoPerdedor(defensorEquipo);
				batalla.setBatallaActiva(false);
				batalla.setEstado("FINALIZADA");
				return batalla;
			}
		}

		aplicarEfectosFinTurno(atacante, batalla.getHistorialTurnos());
		aplicarEfectosFinTurno(defensor, batalla.getHistorialTurnos());

		if (atacante.getHpActual() <= 0) {
			atacante.setHpActual(0);
			batalla.getHistorialTurnos().add("💀 " + atacante.getNombre() + " ha sido derrotado por efectos!");
		}
		if (defensor.getHpActual() <= 0) {
			defensor.setHpActual(0);
			batalla.getHistorialTurnos().add("💀 " + defensor.getNombre() + " ha sido derrotado por efectos!");
			if (!hayPokemonesVivos(defensorEquipo)) {
				batalla.getHistorialTurnos().add("🏆 El equipo ganador es: " + atacanteEquipo.getNombre());
				batalla.setEquipoGanador(atacanteEquipo);
				batalla.setEquipoPerdedor(defensorEquipo);
				batalla.setBatallaActiva(false);
				batalla.setEstado("FINALIZADA");
				return batalla;
			}
		}

		cambiarTurno(batalla);

		if ("cpu".equalsIgnoreCase(batalla.getModo()) && batalla.getTurnoActual().equals("equipo2")
				&& batalla.isBatallaActiva()) {
			// simple: CPU usa un movimiento aleatorio del activo del equipo2
			Pokemon cpuAtacante = obtenerPokemonActivo(batalla.getEquipo2());
			Pokemon cpuDefensor = obtenerPokemonActivo(batalla.getEquipo1());
			if (cpuAtacante != null && cpuDefensor != null) {
				Movimiento movCpu = seleccionarMovimientoAleatorio(cpuAtacante);
				batalla.getHistorialTurnos().add("🤖 CPU usa " + movCpu.getNombre());
				realizarAtaque(cpuAtacante, cpuDefensor, movCpu, batalla.getHistorialTurnos());
				aplicarEfectosPostGolpe(cpuAtacante, cpuDefensor, batalla.getHistorialTurnos());
				aplicarEfectosFinTurno(cpuAtacante, batalla.getHistorialTurnos());
				aplicarEfectosFinTurno(cpuDefensor, batalla.getHistorialTurnos());

				if (!hayPokemonesVivos(batalla.getEquipo1())) {
					batalla.getHistorialTurnos().add("🏆 CPU gana la batalla!");
					batalla.setEquipoGanador(batalla.getEquipo2());
					batalla.setEquipoPerdedor(batalla.getEquipo1());
					batalla.setBatallaActiva(false);
					batalla.setEstado("FINALIZADA");
				} else {

					batalla.setTurnoActual("equipo1");
				}
			}
		}

		return batalla;
	}

	private Pokemon obtenerPokemonActivo(Equipo equipo) {
		if (equipo == null || equipo.getListaPokemon() == null)
			return null;
		return equipo.getListaPokemon().stream().filter(p -> p.getHpActual() > 0).findFirst().orElse(null);
	}


	/**
	 * Comprueba si un equipo tiene al menos un Pokémon con HP > 0.
	 *
	 * @param equipo equipo a verificar.
	 * @return {@code true} si existe al menos un Pokémon vivo, {@code false} en
	 *         caso contrario.
	 */
	private boolean hayPokemonesVivos(Equipo equipo) {
		if (equipo == null || equipo.getListaPokemon() == null)
			return false;
		return equipo.getListaPokemon().stream().anyMatch(p -> p.getHpActual() > 0);
	}


	/**
	 * Cambia el turno actual de la batalla entre "equipo1" y "equipo2".
	 *
	 * @param batalla instancia de {@link Batalla} cuyo turno se cambiará.
	 */
	private void cambiarTurno(Batalla batalla) {
		batalla.setTurnoActual(batalla.getTurnoActual().equals("equipo1") ? "equipo2" : "equipo1");
	}

	/**
	 * Finaliza la batalla si alguno de los dos equipos no tiene Pokémon vivos.
	 * <p>
	 * Establece equipo ganador/perdedor, marca la batalla como inactiva y cambia el
	 * estado.
	 * </p>
	 *
	 * @param batalla la batalla a finalizar (si corresponde).
	 * @param e1      primer equipo participante.
	 * @param e2      segundo equipo participante.
	 */
	private void finalizarSiCorresponde(Batalla batalla, Equipo e1, Equipo e2) {
		if (!hayPokemonesVivos(e1)) {
			batalla.setEquipoGanador(e2);
			batalla.setEquipoPerdedor(e1);
			batalla.setBatallaActiva(false);
			batalla.setEstado("FINALIZADA");
		} else if (!hayPokemonesVivos(e2)) {
			batalla.setEquipoGanador(e1);
			batalla.setEquipoPerdedor(e2);
			batalla.setBatallaActiva(false);
			batalla.setEstado("FINALIZADA");
		}
	}

	/**
	 * Busca un movimiento por nombre en la lista de movimientos de un Pokémon.
	 * <p>
	 * Si el nombre es nulo o está en blanco, o no se encuentra el movimiento, se
	 * selecciona un movimiento aleatorio del Pokémon (si existe).
	 * </p>
	 *
	 * @param p      Pokémon cuyos movimientos se consultan.
	 * @param nombre nombre del movimiento a buscar (case-insensitive).
	 * @return el {@link Movimiento} encontrado o uno aleatorio; {@code null} si no
	 *         hay movimientos.
	 */
	private Movimiento elegirMovimientoPorNombre(Pokemon p, String nombre) {
		if (p.getMovimiento() == null || p.getMovimiento().isEmpty())
			return null;
		if (nombre == null || nombre.isBlank())
			return seleccionarMovimientoAleatorio(p);
		for (Movimiento m : p.getMovimiento()) {
			if (m.getNombre().equalsIgnoreCase(nombre))
				return m;
		}
		return seleccionarMovimientoAleatorio(p);
	}

	/**
	 * Selecciona un movimiento aleatorio del Pokémon.
	 *
	 * @param p Pokémon del que se seleccionará el movimiento.
	 * @return un {@link Movimiento} aleatorio, o {@code null} si no hay
	 *         movimientos.
	 */
	private Movimiento seleccionarMovimientoAleatorio(Pokemon p) {
		List<Movimiento> movs = p.getMovimiento();
		if (movs == null || movs.isEmpty())
			return null;
		return movs.get(random.nextInt(movs.size()));
	}

	private void realizarAtaque(Pokemon atacante, Pokemon defensor, Movimiento mov, List<String> historial) {
		if (atacante == null || defensor == null || mov == null)
			return;

		String tipoMov = mov.getTipo() != null ? mov.getTipo().toLowerCase() : null;

		if (atacante.getAbility() != null && "Pixilate".equalsIgnoreCase(atacante.getAbility().getNombre())
				&& tipoMov != null && tipoMov.equals("normal")) {
			tipoMov = "fairy";
		}

		double multTipo = TypeEffectiveness.getMultiplier(tipoMov, defensor.getTipo());

		double stab = 1.0;
		if (tipoMov != null && atacante.getTipo() != null) {
			for (String t : atacante.getTipo()) {
				if (t != null && t.equalsIgnoreCase(tipoMov)) {
					stab = 1.5;
					break;
				}
			}
		}

		double poder = mov.getDanio();

		int atkStat = Math.max(1, atacante.getStatValue("attack"));
		int defStat = Math.max(1, defensor.getStatValue("defense"));

		double modHabilidadAtk = obtenerModificadorHabilidadAtacante(atacante, mov);
		double modHabilidadDef = obtenerModificadorHabilidadDefensor(defensor, mov);

		double base = ((double) atkStat / (double) defStat) * poder;
		double variacion = 0.85 + (random.nextDouble() * 0.15); // 0.85 - 1.0
		double danoDouble = base * multTipo * stab * modHabilidadAtk * modHabilidadDef * variacion;

		int dano = Math.max(1, (int) Math.round(danoDouble));

		int nuevoHp = Math.max(0, defensor.getHpActual() - dano);
		defensor.setHpActual(nuevoHp);

		String efectividadMsg = multTipo > 1 ? " (¡Es muy efectivo!)"
				: multTipo < 1 && multTipo > 0 ? " (No es muy efectivo...)" : multTipo == 0 ? " (No tuvo efecto)" : "";

		historial.add("👉 " + atacante.getNombre() + " usa " + mov.getNombre() + " y causa " + dano + " de daño"
				+ efectividadMsg);
	}

	/**
	 * Calcula un modificador de daño basado en la habilidad pasiva del atacante.
	 * <p>
	 * Ejemplos de comportamientos implementados:
	 * <ul>
	 * <li>Blaze/Torrent/Overgrow/Swarm: aumentan daño de su tipo si HP &lt;
	 * 1/3.</li>
	 * <li>Guts: aumenta el ataque si el atacante tiene un estado alterado.</li>
	 * <li>Pixilate se maneja cambiando el tipo del movimiento antes del
	 * cálculo.</li>
	 * </ul>
	 * </p>
	 *
	 * @param atacante Pokémon atacante.
	 * @param mov      movimiento utilizado (se usa para comprobar el tipo).
	 * @return multiplicador a aplicar al daño (por defecto {@code 1.0}).
	 */
	private double obtenerModificadorHabilidadAtacante(Pokemon atacante, Movimiento mov) {
		if (atacante.getAbility() == null)
			return 1.0;
		String hab = atacante.getAbility().getNombre();
		String tipoMov = mov.getTipo() != null ? mov.getTipo().toLowerCase() : null;
		double mod = 1.0;

		if (("Blaze".equalsIgnoreCase(hab) && tipoMov != null && tipoMov.equals("fire"))
				|| ("Torrent".equalsIgnoreCase(hab) && tipoMov != null && tipoMov.equals("water"))
				|| ("Overgrow".equalsIgnoreCase(hab) && tipoMov != null && tipoMov.equals("grass"))
				|| ("Swarm".equalsIgnoreCase(hab) && tipoMov != null && tipoMov.equals("bug"))) {
			if (atacante.getHpActual() < (atacante.getStatValue("hp") / 3.0)) {
				mod *= 1.5; // boost cuando hp < 1/3
			}
		}

		if ("Guts".equalsIgnoreCase(hab) && atacante.getEstado() != Estado.NORMAL) {
			mod *= 1.5;
		}

		return mod;
	}

	private double obtenerModificadorHabilidadDefensor(Pokemon defensor, Movimiento mov) {
		if (defensor.getAbility() == null)
			return 1.0;
		String hab = defensor.getAbility().getNombre();

		if ("Light Metal".equalsIgnoreCase(hab))
			return 0.85;

		if ("Sturdy".equalsIgnoreCase(hab)) {
			if (defensor.getHpActual() == defensor.getStatValue("hp")) {

				return 1.0;
			}
		}

		return 1.0;
	}

	private void aplicarHabilidadActiva(Pokemon atacante, Pokemon defensor, List<String> historial) {
		if (atacante.getAbility() == null) {
			historial.add("❌ " + atacante.getNombre() + " no tiene habilidad para usar.");
			return;
		}

		String hab = atacante.getAbility().getNombre();
		double prob = random.nextDouble();

		switch (hab) {
		case "Blaze":
			historial.add(atacante.getNombre() + " activa Blaze: potencia ataques de fuego si HP < 1/3.");

			break;
		case "Torrent":
			historial.add(atacante.getNombre() + " activa Torrent: potencia ataques de agua si HP < 1/3.");
			break;
		case "Overgrow":
			historial.add(atacante.getNombre() + " activa Overgrow: potencia ataques de planta si HP < 1/3.");
			break;
		case "Guts":
			historial.add(atacante.getNombre() + " activa Guts: aumenta ataque si tiene estado.");
			break;
		case "Static":
			if (prob < 0.4) {
				defensor.setEstado(Estado.PARALYSIS);
				historial.add("⚡ " + defensor.getNombre() + " fue paralizado por Static!");
			} else {
				historial.add(atacante.getNombre() + " usó Static pero no surtió efecto.");
			}
			break;
		case "Poison Point":
			if (prob < 0.35) {
				defensor.setEstado(Estado.POISON);
				historial.add("☠️ " + defensor.getNombre() + " fue envenenado por Poison Point!");
			} else {
				historial.add(atacante.getNombre() + " usó Poison Point pero no envenenó.");
			}
			break;
		case "Sturdy":
			historial.add(atacante.getNombre() + " tiene Sturdy (previene KO de un golpe si estaba al máximo).");
			break;
		case "Pixilate":
			historial.add(atacante.getNombre() + " activa Pixilate: sus ataques normales se vuelven Hada.");
			break;
		case "Run Away":
			historial.add(atacante.getNombre() + " usa Run Away (no aplicable en duelo).");
			break;
		default:
			historial.add(atacante.getNombre() + " intenta usar " + hab + " pero no hay efecto activo definido.");
			break;
		}
	}

	private void aplicarEfectosPostGolpe(Pokemon atacante, Pokemon defensor, List<String> historial) {
		if (defensor.getAbility() != null) {
			String habDef = defensor.getAbility().getNombre();
			double prob = random.nextDouble();
			if ("Static".equalsIgnoreCase(habDef) && prob < 0.25) {
				atacante.setEstado(Estado.PARALYSIS);
				historial.add("⚡ " + atacante.getNombre() + " fue paralizado por Static de " + defensor.getNombre());
			} else if ("Poison Point".equalsIgnoreCase(habDef) && prob < 0.25) {
				atacante.setEstado(Estado.POISON);
				historial.add(
						"☠️ " + atacante.getNombre() + " fue envenenado por Poison Point de " + defensor.getNombre());
			} else if ("Cursed Body".equalsIgnoreCase(habDef) && prob < 0.15) {
				historial.add("🛡️ " + defensor.getNombre()
						+ " intentó deshabilitar un movimiento del rival (no implementado PP).");
			}
		}

		if (defensor.getAbility() != null && "Synchronize".equalsIgnoreCase(defensor.getAbility().getNombre())) {
			if (atacante.getEstado() != Estado.NORMAL && defensor.getEstado() == Estado.NORMAL) {
				defensor.setEstado(atacante.getEstado());
				historial.add("🔁 " + defensor.getNombre() + " refleja el estado " + atacante.getEstado()
						+ " por Synchronize!");
			}
		}

		if (defensor.getAbility() != null && "Sturdy".equalsIgnoreCase(defensor.getAbility().getNombre())) {

			if (defensor.getHpActual() == 0 && defensor.getStatValue("hp") > 0) {
				defensor.setHpActual(1);
				historial.add("🪨 " + defensor.getNombre() + " resistió el golpe gracias a Sturdy y queda con 1 HP.");
			}
		}
	}

	private void aplicarEfectosFinTurno(Pokemon p, List<String> historial) {
		if (p == null || p.getEstado() == null)
			return;
		switch (p.getEstado()) {
		case BURN:
			int danoBurn = Math.max(1, p.getStatValue("hp") / 8);
			p.setHpActual(Math.max(0, p.getHpActual() - danoBurn));
			historial.add("🔥 " + p.getNombre() + " sufre " + danoBurn + " de daño por quemadura.");
			break;
		case POISON:
			int danoPoison = Math.max(1, p.getStatValue("hp") / 8);
			p.setHpActual(Math.max(0, p.getHpActual() - danoPoison));
			historial.add("☠️ " + p.getNombre() + " sufre " + danoPoison + " de daño por veneno.");
			break;
		case BADLY_POISONED:
			int danoBad = Math.max(1, p.getStatValue("hp") / 6);
			p.setHpActual(Math.max(0, p.getHpActual() - danoBad));
			historial.add("☠️ " + p.getNombre() + " sufre " + danoBad + " de daño por envenenamiento grave.");
			break;
		default:
			break;
		}
	}

	private boolean aplicarEfectosAntesTurno(Pokemon p, List<String> historial) {
		if (p == null || p.getEstado() == null)
			return true;
		switch (p.getEstado()) {
		case PARALYSIS:
			if (random.nextDouble() < 0.25) {
				historial.add("⚡ " + p.getNombre() + " está paralizado y no puede moverse!");
				return false;
			}
			break;
		case SLEEP:

			if (random.nextDouble() < 0.67) {
				historial.add("💤 " + p.getNombre() + " está dormido y no puede actuar.");
				return false;
			} else {
				historial.add("🌞 " + p.getNombre() + " se despertó.");
				p.setEstado(Estado.NORMAL);
			}
			break;
		case FREEZE:
			if (random.nextDouble() < 0.2) {
				historial.add("❄️ " + p.getNombre() + " se descongeló.");
				p.setEstado(Estado.NORMAL);
			} else {
				historial.add("❄️ " + p.getNombre() + " está congelado y no puede actuar.");
				return false;
			}
		default:
			break;
		}
		return true;
	}
}
