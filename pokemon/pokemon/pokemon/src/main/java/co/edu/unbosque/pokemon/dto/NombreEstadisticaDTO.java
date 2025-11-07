package co.edu.unbosque.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NombreEstadisticaDTO {

	private String name;

	public NombreEstadisticaDTO() {
	}

	public NombreEstadisticaDTO(String name) {
		this.name = name;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StatNameDTO [name=" + name;
	}

}