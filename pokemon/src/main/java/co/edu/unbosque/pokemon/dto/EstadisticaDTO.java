package co.edu.unbosque.pokemon.dto;

public class EstadisticaDTO {

	private long id;
	private String nombre;
	private int valor;

	public EstadisticaDTO() {
	}

	public EstadisticaDTO(String nombre, int valor) {
		this.nombre = nombre;
		this.valor = valor;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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
	 * @return the valor
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Estadistica [id=" + id + ", nombre=" + nombre + ", valor=" + valor + "]";
	}

}