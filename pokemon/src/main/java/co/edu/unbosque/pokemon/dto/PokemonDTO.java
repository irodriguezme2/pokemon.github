package co.edu.unbosque.pokemon.dto;

import java.util.List;

public class PokemonDTO {

	private long id;
	private String nombre;
	private List<String> tipo;
	private List<EstadisticaDTO> estadistica;
	private List<MovimientoDTO> movimiento;
	private AbilityDTO ability;
	private int hpActual;
	private Estado estado;

	public enum Estado {

		NORMAL, PARALIZADO, DORMIDO, QUEMADO, CONGELADO, ENVENENADO
	}

	public PokemonDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonDTO(String nombre, List<String> tipo, List<EstadisticaDTO> estadistica,
			List<MovimientoDTO> movimiento, AbilityDTO ability) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.estadistica = estadistica;
		this.movimiento = movimiento;
		this.ability = ability;
		this.hpActual = getStatValue("hp");
		this.estado = Estado.NORMAL;
	}

	public int getStatValue(String statName) {
		if (estadistica == null)
			return 0;
		for (EstadisticaDTO e : estadistica) {
			if (e.getNombre().equalsIgnoreCase(statName)) {
				return e.getValor();
			}
		}
		return 0;
	}

	/**
	 * @return the ability
	 */
	public AbilityDTO getAbility() {
		return ability;
	}

	/**
	 * @param ability the ability to set
	 */
	public void setAbility(AbilityDTO ability) {
		this.ability = ability;
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
	public List<MovimientoDTO> getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(List<MovimientoDTO> movimiento) {
		this.movimiento = movimiento;
	}

	/**
	 * @return the estadistica
	 */
	public List<EstadisticaDTO> getEstadistica() {
		return estadistica;
	}

	/**
	 * @param estadistica the estadistica to set
	 */
	public void setEstadistica(List<EstadisticaDTO> estadistica) {
		this.estadistica = estadistica;
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
		this.hpActual = hpActual;
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
	public String toString() {
		return "PokemonDTO [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", movimiento=" + movimiento + "]";
	}

}
