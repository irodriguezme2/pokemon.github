package co.edu.unbosque.pokemon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora effort y otros campos
public class EstadisticaJsonDTO {

	private int base_stat;
	private NombreEstadisticaDTO stat;

	public EstadisticaJsonDTO() {
	}

	public EstadisticaJsonDTO(int base_stat, NombreEstadisticaDTO stat) {
		super();
		this.base_stat = base_stat;
		this.stat = stat;
	}

	/**
	 * @return the base_stat
	 */
	public int getBase_stat() {
		return base_stat;
	}

	/**
	 * @param base_stat the base_stat to set
	 */
	public void setBase_stat(int base_stat) {
		this.base_stat = base_stat;
	}

	/**
	 * @return the stat
	 */
	public NombreEstadisticaDTO getStat() {
		return stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(NombreEstadisticaDTO stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return "EstadisticaJsonDTO [base_stat=" + base_stat + ", stat=" + stat + "]";
	}

}
