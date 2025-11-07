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

@Service
public class BatallaService {

	private final Random random = new Random();

	/**
	 * Inicia la batalla: inicializa HP actuales, estados y turnos si hace falta.
	 */
	public Batalla iniciarBatalla(Batalla batalla) {
		if (batalla == null)
			throw new IllegalArgumentException("Batalla nula");
		if (batalla.getEquipo1() == null)
			throw new IllegalArgumentException("Equipo 1 no puede ser nulo");
		// Si cpu y no viene, el controller puede haber generado el equipo2. Aquí
		// asumimos que ya está.
		if (batalla.getEquipo2() == null)
			throw new IllegalArgumentException("Equipo 2 no puede ser nulo");

		// inicializar historial, turno y banderas
		if (batalla.getHistorialTurnos() == null)
			batalla.setHistorialTurnos(new ArrayList<>());
		batalla.getHistorialTurnos().add("🔥 Comienza la batalla entre " + batalla.getEquipo1().getNombre() + " y "
				+ batalla.getEquipo2().getNombre());

		// inicializar pokemones (hpActual y estado)
		inicializarEquipo(batalla.getEquipo1());
		inicializarEquipo(batalla.getEquipo2());

		// turno por defecto equipo1
		if (batalla.getTurnoActual() == null || batalla.getTurnoActual().isBlank())
			batalla.setTurnoActual("equipo1");

		batalla.setBatallaActiva(true);
		batalla.setEstado("EN_PROGRESO");

		return batalla;
	}

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
	 * Ejecuta un solo turno de la batalla. - movimientoSeleccionado: nombre del
	 * movimiento (si usarHabilidad = false) - usarHabilidad: true si se presionó el
	 * botón "habilidad"
	 *
	 * Este método muta el objeto Batalla recibido (historial, hp, estados,
	 * turnoActual, etc.)
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

		// Antes de actuar: aplicar efectos del estado del atacante (si impide actuar)
		boolean puedeActuar = aplicarEfectosAntesTurno(atacante, batalla.getHistorialTurnos());
		if (!puedeActuar) {
			// aplicar efectos por turno (veneno/quemadura) también al atacante y defensor
			aplicarEfectosFinTurno(atacante, batalla.getHistorialTurnos());
			aplicarEfectosFinTurno(defensor, batalla.getHistorialTurnos());
			// cambiar turno
			cambiarTurno(batalla);
			return batalla;
		}

		// Si el jugador usó la habilidad
		if (usarHabilidad) {
			aplicarHabilidadActiva(atacante, defensor, batalla.getHistorialTurnos());
		} else {
			// Buscar movimiento por nombre; si no se encuentra, tomar aleatorio
			Movimiento movimiento = elegirMovimientoPorNombre(atacante, movimientoSeleccionado);
			if (movimiento == null) {
				batalla.getHistorialTurnos().add("❌ " + atacante.getNombre() + " no tiene movimientos válidos.");
			} else {
				realizarAtaque(atacante, defensor, movimiento, batalla.getHistorialTurnos());
			}
		}

		// Aplicar efectos resultantes del golpe (habilidades del defensor que
		// reaccionan)
		aplicarEfectosPostGolpe(atacante, defensor, batalla.getHistorialTurnos());

		// Verificar si defensor fue derrotado
		if (defensor.getHpActual() <= 0) {
			defensor.setHpActual(0);
			defensor.setEstado(Estado.NORMAL); // opcional: limpiar estado
			batalla.getHistorialTurnos().add("💀 " + defensor.getNombre() + " ha sido derrotado!");
			// verificar si quedan pokemones vivos en el equipo defensor
			if (!hayPokemonesVivos(defensorEquipo)) {
				batalla.getHistorialTurnos().add("🏆 El equipo ganador es: " + atacanteEquipo.getNombre());
				batalla.setEquipoGanador(atacanteEquipo);
				batalla.setEquipoPerdedor(defensorEquipo);
				batalla.setBatallaActiva(false);
				batalla.setEstado("FINALIZADA");
				return batalla;
			}
		}

		// Aplicar daños por estados al final del turno a ambos
		aplicarEfectosFinTurno(atacante, batalla.getHistorialTurnos());
		aplicarEfectosFinTurno(defensor, batalla.getHistorialTurnos());

		// Si alguno murió por efectos de fin de turno
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

		// Cambiar turno
		cambiarTurno(batalla);

		// Si modo CPU y ahora es turno de la CPU, ejecutar acción CPU automáticamente
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
				// comprobar victoria CPU
				if (!hayPokemonesVivos(batalla.getEquipo1())) {
					batalla.getHistorialTurnos().add("🏆 CPU gana la batalla!");
					batalla.setEquipoGanador(batalla.getEquipo2());
					batalla.setEquipoPerdedor(batalla.getEquipo1());
					batalla.setBatallaActiva(false);
					batalla.setEstado("FINALIZADA");
				} else {
					// volver a turno jugador 1
					batalla.setTurnoActual("equipo1");
				}
			}
		}

		return batalla;
	}

	/* ----------------------------- helpers ----------------------------- */

	private Pokemon obtenerPokemonActivo(Equipo equipo) {
		if (equipo == null || equipo.getListaPokemon() == null)
			return null;
		return equipo.getListaPokemon().stream().filter(p -> p.getHpActual() > 0).findFirst().orElse(null);
	}

	private boolean hayPokemonesVivos(Equipo equipo) {
		if (equipo == null || equipo.getListaPokemon() == null)
			return false;
		return equipo.getListaPokemon().stream().anyMatch(p -> p.getHpActual() > 0);
	}

	private void cambiarTurno(Batalla batalla) {
		batalla.setTurnoActual(batalla.getTurnoActual().equals("equipo1") ? "equipo2" : "equipo1");
	}

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

	private Movimiento seleccionarMovimientoAleatorio(Pokemon p) {
		List<Movimiento> movs = p.getMovimiento();
		if (movs == null || movs.isEmpty())
			return null;
		return movs.get(random.nextInt(movs.size()));
	}

	/* ------------------- ataque y cálculo de daño ------------------- */

	private void realizarAtaque(Pokemon atacante, Pokemon defensor, Movimiento mov, List<String> historial) {
		if (atacante == null || defensor == null || mov == null)
			return;

		// Verificar si el movimiento tiene PP si manejas pp (no implementado aquí)
		// Aplicar STAB y efectividad
		String tipoMov = mov.getTipo() != null ? mov.getTipo().toLowerCase() : null;

		// Si atacante tiene Pixilate y mov es "normal", conviértelo a "fairy"
		if (atacante.getAbility() != null && "Pixilate".equalsIgnoreCase(atacante.getAbility().getNombre())
				&& tipoMov != null && tipoMov.equals("normal")) {
			tipoMov = "fairy";
		}

		double multTipo = TypeEffectiveness.getMultiplier(tipoMov, defensor.getTipo());

		// STAB: si el tipo del movimiento coincide con alguno de los tipos del atacante
		double stab = 1.0;
		if (tipoMov != null && atacante.getTipo() != null) {
			for (String t : atacante.getTipo()) {
				if (t != null && t.equalsIgnoreCase(tipoMov)) {
					stab = 1.5;
					break;
				}
			}
		}

		// Poder/daño base: usamos mov.getDanio() (tu entidad usa danio)
		double poder = mov.getDanio();

		// Stats
		int atkStat = Math.max(1, atacante.getStatValue("attack")); // seguridad: >=1
		int defStat = Math.max(1, defensor.getStatValue("defense"));

		// Aplicar modificadores por habilidades pasivas antes de calcular
		double modHabilidadAtk = obtenerModificadorHabilidadAtacante(atacante, mov);
		double modHabilidadDef = obtenerModificadorHabilidadDefensor(defensor, mov);

		// Fórmula simplificada: (ataque / defensa) * poder * multipliers
		double base = ((double) atkStat / (double) defStat) * poder;
		double variacion = 0.85 + (random.nextDouble() * 0.15); // 0.85 - 1.0
		double danoDouble = base * multTipo * stab * modHabilidadAtk * modHabilidadDef * variacion;

		int dano = Math.max(1, (int) Math.round(danoDouble));

		// Aplicar daño
		int nuevoHp = Math.max(0, defensor.getHpActual() - dano);
		defensor.setHpActual(nuevoHp);

		// Registrar en historial
		String efectividadMsg = multTipo > 1 ? " (¡Es muy efectivo!)"
				: multTipo < 1 && multTipo > 0 ? " (No es muy efectivo...)" : multTipo == 0 ? " (No tuvo efecto)" : "";

		historial.add("👉 " + atacante.getNombre() + " usa " + mov.getNombre() + " y causa " + dano + " de daño"
				+ efectividadMsg);
	}

	/**
	 * Devuelve multiplicador extra por habilidad del atacante
	 * (Blaze/Torrent/Overgrow/Guts/...). - Blaze/Torrent/Overgrow/Swarm: aumentan
	 * daño de su tipo si hpActual < 1/3. - Guts: aumenta ataque si tiene estado
	 * alterado.
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

		// Pixilate ya está manejado cambiando tipo del movimiento
		return mod;
	}

	/**
	 * Devuelve modificador por habilidad del defensor (por ejemplo, Light Metal
	 * reduce daño físico). Podrías ampliar para Sturdy, Multiscale, etc.
	 */
	private double obtenerModificadorHabilidadDefensor(Pokemon defensor, Movimiento mov) {
		if (defensor.getAbility() == null)
			return 1.0;
		String hab = defensor.getAbility().getNombre();

		// Light Metal: reduce daño físico (aquí asumimos todos los movimientos 'danio'
		// son físicos)
		if ("Light Metal".equalsIgnoreCase(hab))
			return 0.85;

		// Sturdy: evita KO si está en full HP y daño >= HP -> queda con 1 HP
		if ("Sturdy".equalsIgnoreCase(hab)) {
			if (defensor.getHpActual() == defensor.getStatValue("hp")) {
				// si un golpe debería dejarlo en 0, se evita poniéndolo en 1
				// implementación parcial: la comprobación debe hacerse en realizarAtaque,
				// pero aquí devolvemos 1.0 y manejamos Sturdy post-cálculo en
				// aplicarEfectosPostGolpe
				return 1.0;
			}
		}

		return 1.0;
	}

	/*
	 * ------------------ habilidades activas y efectos post golpe
	 * ------------------
	 */

	/**
	 * Aplica la habilidad cuando el jugador pulsa el botón 'habilidad' (acción
	 * activa). Efectos ejemplo: - Blaze/Torrent/Overgrow: boost temporal (añadido
	 * como mensaje, y funcionaría si se recalcula) - Static / Poison Point:
	 * intentan aplicar PARALYSIS / POISON al defensor - Run Away: (no aplicable en
	 * VS) - Pixilate: se aplica en ataque cambian movimientos normales a fairy
	 */
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

	/**
	 * Efectos que reaccionan tras recibir un golpe (habilidades pasivas del
	 * defensor). - Static/Poison Point del defensor pueden aplicar estado al
	 * atacante por contacto. - Cursed Body puede "deshabilitar" (no implementado
	 * PP). - Synchronize: si atacante recibe estado, se transfiere al defensor
	 * (simplificado).
	 */
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

		// Si defensor tiene Synchronize y atacante quedó con estado, defender lo recibe
		// (simetría simple)
		if (defensor.getAbility() != null && "Synchronize".equalsIgnoreCase(defensor.getAbility().getNombre())) {
			if (atacante.getEstado() != Estado.NORMAL && defensor.getEstado() == Estado.NORMAL) {
				defensor.setEstado(atacante.getEstado());
				historial.add("🔁 " + defensor.getNombre() + " refleja el estado " + atacante.getEstado()
						+ " por Synchronize!");
			}
		}

		// Sturdy: si defensor hubiera caído de un solo golpe desde full HP, lo deja con
		// 1 HP (manejo simplificado)
		if (defensor.getAbility() != null && "Sturdy".equalsIgnoreCase(defensor.getAbility().getNombre())) {
			// si defensor estaba en full hp antes del último golpe y ahora hp == 0 -> dejar
			// 1
			// Nota: para detectar 'antes', necesitarías el hp anterior; simplificamos
			// comprobando stat base
			if (defensor.getHpActual() == 0 && defensor.getStatValue("hp") > 0) {
				defensor.setHpActual(1);
				historial.add("🪨 " + defensor.getNombre() + " resistió el golpe gracias a Sturdy y queda con 1 HP.");
			}
		}
	}

	/* ------------------ efectos de estado al final del turno ------------------ */

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

	/*
	 * ------------------ aplicar efectos antes de turno (impedir atacar)
	 * ------------------
	 */

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
			// dormido 1-3 turnos: aquí se simula con probabilidad de despertar
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
