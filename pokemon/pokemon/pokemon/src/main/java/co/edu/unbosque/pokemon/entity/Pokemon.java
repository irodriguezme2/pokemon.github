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

@Entity
public class Pokemon {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	@Column(unique = true, name = "nombreUsuario")
	private String nombre;
	@ElementCollection
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

	public enum Estado {

		NORMAL, BURN, POISON, BADLY_POISONED, PARALYSIS, SLEEP, FREEZE, CONFUSION, FLINCH, CURSED, BOUND, INFATUATION;
	}

	public Pokemon() {
		// TODO Auto-generated constructor stub
	}

	public Pokemon(String nombre, List<String> tipo, List<Movimiento> movimiento, List<Estadistica> estadistica) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.movimiento = movimiento;
		this.estadistica = estadistica;
		this.hpActual = obtenerHPBase();
		this.estado = Estado.NORMAL;
	}

	private int obtenerHPBase() {
		return estadistica.stream().filter(e -> "hp".equalsIgnoreCase(e.getNombre())).mapToInt(Estadistica::getValor)
				.findFirst().orElse(50);
	}

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
