package co.edu.unbosque.pokemon.entity;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



/**
 * Representa un Pokémon dentro del sistema PokéLab.
 * <p>
 * Cada Pokémon posee un nombre único, uno o más tipos, una lista de
 * movimientos, estadísticas base, una habilidad especial y un estado actual
 * (como "Parálisis" o "Quemadura"). Además, mantiene un valor de puntos de
 * salud actuales (HP).
 * </p>
 *
 * <p>
 * <b>Ejemplo:</b> Un Pokémon como "Charizard" puede tener los tipos
 * <i>Fuego</i> y <i>Volador</i>, la habilidad "Blaze", movimientos como
 * "Lanzallamas" y estadísticas personalizadas.
 * </p>
 *
 * @author PokéLab
 * @version 1.0
 */
@Entity
public class Pokemon {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	@Column(name = "nombre")
	private String nombre;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> tipo;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pokemon_id")
	private List<Movimiento> movimiento;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pokemon_id")
	private List<Estadistica> estadistica;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ability_id")
	private Ability ability;
	private int hpActual;
	@Enumerated(EnumType.STRING)
	private Estado estado;

	/**
	 * Enumeración que representa los posibles estados alterados que puede tener un
	 * Pokémon.
	 */
	public enum Estado {

		NORMAL, BURN, POISON, BADLY_POISONED, PARALYSIS, SLEEP, FREEZE, CONFUSION, FLINCH, CURSED, BOUND, INFATUATION;
	}

	public Pokemon() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Constructor que permite inicializar un Pokémon con sus características
	 * principales.
	 *
	 * @param nombre      Nombre del Pokémon.
	 * @param tipo        Lista de tipos elementales.
	 * @param movimiento  Lista de movimientos disponibles.
	 * @param estadistica Lista de estadísticas base.
	 */
	public Pokemon(String nombre, List<String> tipo, List<Movimiento> movimiento, List<Estadistica> estadistica) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.movimiento = movimiento;
		this.estadistica = estadistica;
		this.hpActual = obtenerHPBase();
		this.estado = Estado.NORMAL;
	}

	/**
	 * Obtiene el valor base de HP desde las estadísticas del Pokémon. Si no existe
	 * la estadística de HP, retorna 50 por defecto.
	 *
	 * @return valor base de HP.
	 */
	private int obtenerHPBase() {
		return estadistica.stream().filter(e -> "hp".equalsIgnoreCase(e.getNombre())).mapToInt(Estadistica::getValor)
				.findFirst().orElse(50);
	}


	/**
	 * Busca el valor de una estadística específica por nombre.
	 *
	 * @param statName nombre de la estadística (por ejemplo, "ataque").
	 * @return valor numérico de la estadística, o 0 si no se encuentra.
	 */
	public int getStatValue(String statName) {
		if (estadistica == null)
			return 0;
		for (Estadistica e : estadistica) {
			if (e.getNombre().equalsIgnoreCase(statName)) {
				return e.getValor();
			}
		}
		return 0;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the tipo
	 */
	public List<String> getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(List<String> tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the movimiento
	 */
	public List<Movimiento> getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(List<Movimiento> movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * @return the estadistica
	 */
	public List<Estadistica> getEstadistica() {
		return estadistica;
	}

	/**
	 * @param estadistica the estadistica to set
	 */
	public void setEstadistica(List<Estadistica> estadistica) {
		this.estadistica = estadistica;
	}

	/**
	 * @return the ability
	 */
	public Ability getAbility() {
		return ability;
	}

	/**
	 * @param ability the ability to set
	 */
	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	/**
	 * @return the hpActual
	 */
	public int getHpActual() {
		return hpActual;
	}

	/**
	 * @param hpActual the hpActual to set
	 */
	public void setHpActual(int hpActual) {
		this.hpActual = Math.max(0, hpActual);
	}

	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void restaurarHP() {
		this.hpActual = getStatValue("hp");
		this.estado = Estado.NORMAL;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estadistica, id, movimiento, nombre, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		return Objects.equals(estadistica, other.estadistica) && id == other.id
				&& Objects.equals(movimiento, other.movimiento) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(tipo, other.tipo);
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", movimiento=" + movimiento
				+ ", estadistica=" + estadistica + "]";
	}

}
