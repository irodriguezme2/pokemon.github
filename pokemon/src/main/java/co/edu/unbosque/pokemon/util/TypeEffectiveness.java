package co.edu.unbosque.pokemon.util;

import java.util.HashMap;
import java.util.Map;

import co.edu.unbosque.pokemon.entity.Ability;

public class TypeEffectiveness {

	// mapa tipo -> habilidad
	private static final Map<String, Ability> ABILITIES_MAP = new HashMap<>();

	static {
		ABILITIES_MAP.put("normal", new Ability("Run Away", "Permite escapar fácilmente del combate salvaje"));
		ABILITIES_MAP.put("fire", new Ability("Blaze", "Aumenta daño de Fuego cuando HP < 1/3"));
		ABILITIES_MAP.put("water", new Ability("Torrent", "Aumenta daño de Agua cuando HP < 1/3"));
		ABILITIES_MAP.put("grass", new Ability("Overgrow", "Aumenta daño de Planta cuando HP < 1/3"));
		ABILITIES_MAP.put("electric", new Ability("Static", "Paraliza al atacante que hace contacto"));
		ABILITIES_MAP.put("ground", new Ability("Sand Force", "Aumenta daño de Tierra"));
		ABILITIES_MAP.put("ice", new Ability("Snow Cloak", "Aumenta evasión en tormenta de nieve"));
		ABILITIES_MAP.put("fighting", new Ability("Guts", "Aumenta ataque cuando tiene un estado alterado"));
		ABILITIES_MAP.put("poison", new Ability("Poison Point", "Puede envenenar al contacto con el atacante"));
		ABILITIES_MAP.put("flying", new Ability("Keen Eye", "Evita que la precisión del Pokémon baje"));
		ABILITIES_MAP.put("psychic", new Ability("Synchronize", "Transfiere estado alterado al oponente"));
		ABILITIES_MAP.put("bug", new Ability("Swarm", "Aumenta daño de movimientos Bicho cuando HP < 1/3"));
		ABILITIES_MAP.put("rock", new Ability("Sturdy", "Evita ser derrotado de un golpe que debería dejar HP en 0"));
		ABILITIES_MAP.put("ghost", new Ability("Cursed Body", "Puede deshabilitar el movimiento que lo golpea"));
		ABILITIES_MAP.put("dragon", new Ability("Pressure", "Drena PP extra al oponente"));
		ABILITIES_MAP.put("dark", new Ability("Inner Focus", "Evita retroceso por movimientos de ataque"));
		ABILITIES_MAP.put("steel", new Ability("Light Metal", "Reduce peso y daño de movimientos de tipo físico"));
		ABILITIES_MAP.put("fairy", new Ability("Pixilate", "Convierte movimientos normales en tipo Hada"));
	}

	// método para obtener la habilidad según tipo
	public static Ability getAbilityByType(String type) {
		if (type == null)
			return null;
		return ABILITIES_MAP.get(type.toLowerCase());
	}

	// mapa: ataqueTipo -> (defensaTipo -> multiplicador)
	private static final Map<String, Map<String, Double>> CHART = new HashMap<>();

	static {
		// inicializar con los tipos más comunes; todos en lowercase
		add("normal", "rock", 0.5);
		add("normal", "ghost", 0.0);
		add("normal", "steel", 0.5);

		add("fire", "grass", 2.0);
		add("fire", "ice", 2.0);
		add("fire", "bug", 2.0);
		add("fire", "steel", 2.0);
		add("fire", "fire", 0.5);
		add("fire", "water", 0.5);
		add("fire", "rock", 0.5);
		add("fire", "dragon", 0.5);

		add("water", "fire", 2.0);
		add("water", "ground", 2.0);
		add("water", "rock", 2.0);
		add("water", "water", 0.5);
		add("water", "grass", 0.5);
		add("water", "dragon", 0.5);

		add("electric", "water", 2.0);
		add("electric", "flying", 2.0);
		add("electric", "electric", 0.5);
		add("electric", "grass", 0.5);
		add("electric", "dragon", 0.5);
		add("electric", "ground", 0.0);

		add("grass", "water", 2.0);
		add("grass", "ground", 2.0);
		add("grass", "rock", 2.0);
		add("grass", "fire", 0.5);
		add("grass", "grass", 0.5);
		add("grass", "poison", 0.5);
		add("grass", "flying", 0.5);
		add("grass", "bug", 0.5);
		add("grass", "dragon", 0.5);
		add("grass", "steel", 0.5);

		add("ice", "grass", 2.0);
		add("ice", "ground", 2.0);
		add("ice", "flying", 2.0);
		add("ice", "dragon", 2.0);
		add("ice", "fire", 0.5);
		add("ice", "water", 0.5);
		add("ice", "ice", 0.5);
		add("ice", "steel", 0.5);

		add("fighting", "normal", 2.0);
		add("fighting", "ice", 2.0);
		add("fighting", "rock", 2.0);
		add("fighting", "dark", 2.0);
		add("fighting", "steel", 2.0);
		add("fighting", "poison", 0.5);
		add("fighting", "flying", 0.5);
		add("fighting", "psychic", 0.5);
		add("fighting", "bug", 0.5);
		add("fighting", "fairy", 0.5);
		add("fighting", "ghost", 0.0);

		add("poison", "grass", 2.0);
		add("poison", "fairy", 2.0);
		add("poison", "poison", 0.5);
		add("poison", "ground", 0.5);
		add("poison", "rock", 0.5);
		add("poison", "ghost", 0.5);
		add("poison", "steel", 0.0);

		add("ground", "fire", 2.0);
		add("ground", "electric", 2.0);
		add("ground", "poison", 2.0);
		add("ground", "rock", 2.0);
		add("ground", "steel", 2.0);
		add("ground", "grass", 0.5);
		add("ground", "bug", 0.5);
		add("ground", "flying", 0.0);

		add("flying", "grass", 2.0);
		add("flying", "fighting", 2.0);
		add("flying", "bug", 2.0);
		add("flying", "electric", 0.5);
		add("flying", "rock", 0.5);
		add("flying", "steel", 0.5);

		add("psychic", "fighting", 2.0);
		add("psychic", "poison", 2.0);
		add("psychic", "psychic", 0.5);
		add("psychic", "steel", 0.5);
		add("psychic", "dark", 0.0);

		add("bug", "grass", 2.0);
		add("bug", "psychic", 2.0);
		add("bug", "dark", 2.0);
		add("bug", "fire", 0.5);
		add("bug", "fighting", 0.5);
		add("bug", "poison", 0.5);
		add("bug", "flying", 0.5);
		add("bug", "ghost", 0.5);
		add("bug", "steel", 0.5);
		add("bug", "fairy", 0.5);

		add("rock", "fire", 2.0);
		add("rock", "ice", 2.0);
		add("rock", "flying", 2.0);
		add("rock", "bug", 2.0);
		add("rock", "fighting", 0.5);
		add("rock", "ground", 0.5);
		add("rock", "steel", 0.5);

		add("ghost", "psychic", 2.0);
		add("ghost", "ghost", 2.0);
		add("ghost", "dark", 0.5);
		add("ghost", "normal", 0.0);

		add("dragon", "dragon", 2.0);
		add("dragon", "steel", 0.5);
		add("dragon", "fairy", 0.0);

		add("dark", "psychic", 2.0);
		add("dark", "ghost", 2.0);
		add("dark", "fighting", 0.5);
		add("dark", "dark", 0.5);
		add("dark", "fairy", 0.5);

		add("steel", "ice", 2.0);
		add("steel", "rock", 2.0);
		add("steel", "fairy", 2.0);
		add("steel", "fire", 0.5);
		add("steel", "water", 0.5);
		add("steel", "electric", 0.5);
		add("steel", "steel", 0.5);

		add("fairy", "fighting", 2.0);
		add("fairy", "dragon", 2.0);
		add("fairy", "dark", 2.0);
		add("fairy", "fire", 0.5);
		add("fairy", "poison", 0.5);
		add("fairy", "steel", 0.5);
	}

	private static void add(String atk, String def, double mult) {
		CHART.computeIfAbsent(atk, k -> new HashMap<>()).put(def, mult);
	}

	/**
	 * Retorna multiplicador total entre tipoMovimiento y tipos del defensor.
	 * 
	 * @param moveType      tipo del movimiento (puede ser null)
	 * @param defenderTypes lista de tipos del pokemon defensor (nombre en
	 *                      lowercase)
	 * @return multiplicador (ej: 0.5, 1.0, 2.0, 0.0)
	 */
	public static double getMultiplier(String moveType, java.util.List<String> defenderTypes) {
		if (moveType == null || moveType.trim().isEmpty())
			return 1.0;
		String atk = moveType.toLowerCase();
		double mult = 1.0;
		Map<String, Double> row = CHART.get(atk);
		for (String def : defenderTypes) {
			if (def == null)
				continue;
			String defLower = def.toLowerCase();
			if (row != null && row.containsKey(defLower)) {
				mult *= row.get(defLower);
			} else {
				mult *= 1.0;
			}
		}
		return mult;
	}
}
