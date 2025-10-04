package co.edu.unbosque.pokemon.dto;

import java.util.List;

public class PokemonDTO {

	private long id;
	private String nombre;
	private List<String> tipo;
	private int puntosSalud = 100;
	private List<MovimientoDTO> movimiento;

	public PokemonDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonDTO(String nombre, List<String> tipo, int puntosSalud, List<MovimientoDTO> movimiento) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.puntosSalud = puntosSalud;
		this.movimiento = movimiento;
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
	 * @return the puntosSalud
	 */
	public int getPuntosSalud() {
		return puntosSalud;
	}

	/**
	 * @param puntosSalud the puntosSalud to set
	 */
	public void setPuntosSalud(int puntosSalud) {
		this.puntosSalud = puntosSalud;
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

	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", puntosSalud=" + puntosSalud
				+ ", movimiento=" + movimiento + "]";
	}

	


}
