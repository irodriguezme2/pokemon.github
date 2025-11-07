package co.edu.unbosque.pokemon.dto;

import java.util.List;

public class BatallaDTO {
	private long id;
	private EquipoDTO equipo1;
	private EquipoDTO equipo2;
	private String modo;
	private EquipoDTO equipoGanador;
	private EquipoDTO equipoPerdedor;
	private List<String> historialTurnos;

	public BatallaDTO() {
		// TODO Auto-generated constructor stub
	}

	public BatallaDTO(long id, EquipoDTO equipo1, EquipoDTO equipo2, String modo, EquipoDTO equipoGanador,
			EquipoDTO equipoPerdedor, List<String> historialTurnos) {
		super();
		this.id = id;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.modo = modo;
		this.equipoGanador = equipoGanador;
		this.equipoPerdedor = equipoPerdedor;
		this.historialTurnos = historialTurnos;
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
	 * @return the equipo1
	 */
	public EquipoDTO getEquipo1() {
		return equipo1;
	}

	/**
	 * @param equipo1 the equipo1 to set
	 */
	public void setEquipo1(EquipoDTO equipo1) {
		this.equipo1 = equipo1;
	}

	/**
	 * @return the equipo2
	 */
	public EquipoDTO getEquipo2() {
		return equipo2;
	}

	/**
	 * @param equipo2 the equipo2 to set
	 */
	public void setEquipo2(EquipoDTO equipo2) {
		this.equipo2 = equipo2;
	}

	/**
	 * @return the modo
	 */
	public String getModo() {
		return modo;
	}

	/**
	 * @param modo the modo to set
	 */
	public void setModo(String modo) {
		this.modo = modo;
	}

	/**
	 * @return the equipoGanador
	 */
	public EquipoDTO getEquipoGanador() {
		return equipoGanador;
	}

	/**
	 * @param equipoGanador the equipoGanador to set
	 */
	public void setEquipoGanador(EquipoDTO equipoGanador) {
		this.equipoGanador = equipoGanador;
	}

	/**
	 * @return the equipoPerdedor
	 */
	public EquipoDTO getEquipoPerdedor() {
		return equipoPerdedor;
	}

	/**
	 * @param equipoPerdedor the equipoPerdedor to set
	 */
	public void setEquipoPerdedor(EquipoDTO equipoPerdedor) {
		this.equipoPerdedor = equipoPerdedor;
	}

	/**
	 * @return the historialTurnos
	 */
	public List<String> getHistorialTurnos() {
		return historialTurnos;
	}

	/**
	 * @param historialTurnos the historialTurnos to set
	 */
	public void setHistorialTurnos(List<String> historialTurnos) {
		this.historialTurnos = historialTurnos;
	}

	@Override
	public String toString() {
		return "BatallaDTO [id=" + id + ", equipo1=" + equipo1 + ", equipo2=" + equipo2 + ", modo=" + modo
				+ ", equipoGanador=" + equipoGanador + ", equipoPerdedor=" + equipoPerdedor + ", historialTurnos="
				+ historialTurnos + "]";
	}

}