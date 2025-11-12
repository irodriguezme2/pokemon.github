package co.edu.unbosque.pokemon.dto;

public class AbilityDTO {
	private long id;
	private String nombre;
	private String descripcion;

	// Constructor vacío
	public AbilityDTO() {
	}

	// Constructor con parámetros
	public AbilityDTO(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
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

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Método opcional para mostrar la habilidad
	@Override
	public String toString() {
		return nombre + " (" + descripcion + ")";
	}
}
