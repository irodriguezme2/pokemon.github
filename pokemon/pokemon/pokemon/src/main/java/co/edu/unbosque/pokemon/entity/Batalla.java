package co.edu.unbosque.pokemon.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Batalla {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Equipo equipo1;

	@ManyToOne
	private Equipo equipo2;

	@Transient
	private String nombreEquipo1;

	@Transient
	private String nombreEquipo2;

	@ManyToOne
	private Equipo equipoGanador;

	@ManyToOne
	private Equipo equipoPerdedor;

	private String modo;

	@ElementCollection
	private List<String> historialTurnos;

	private String turnoActual;

	private boolean batallaActiva = true;

	private String estado;

	public Batalla() {
	}

	public Batalla(Equipo equipo1, Equipo equipo2, Equipo equipoGanador, Equipo equipoPerdedor, String modo,
			List<String> historialTurnos) {
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.equipoGanador = equipoGanador;
		this.equipoPerdedor = equipoPerdedor;
		this.modo = modo;
		this.historialTurnos = historialTurnos;
		this.turnoActual = "equipo1";
		this.estado = "EN_PROGRESO";
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
	 * @return the equipo1
	 */
	public Equipo getEquipo1() {
		return equipo1;
	}

	/**
	 * @param equipo1 the equipo1 to set
	 */
	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	/**
	 * @return the equipo2
	 */
	public Equipo getEquipo2() {
		return equipo2;
	}

	/**
	 * @param equipo2 the equipo2 to set
	 */
	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	/**
	 * @return the nombreEquipo1
	 */
	public String getNombreEquipo1() {
		return nombreEquipo1;
	}

	/**
	 * @param nombreEquipo1 the nombreEquipo1 to set
	 */
	public void setNombreEquipo1(String nombreEquipo1) {
		this.nombreEquipo1 = nombreEquipo1;
	}

	/**
	 * @return the nombreEquipo2
	 */
	public String getNombreEquipo2() {
		return nombreEquipo2;
	}

	/**
	 * @param nombreEquipo2 the nombreEquipo2 to set
	 */
	public void setNombreEquipo2(String nombreEquipo2) {
		this.nombreEquipo2 = nombreEquipo2;
	}

	/**
	 * @return the equipoGanador
	 */
	public Equipo getEquipoGanador() {
		return equipoGanador;
	}

	/**
	 * @param equipoGanador the equipoGanador to set
	 */
	public void setEquipoGanador(Equipo equipoGanador) {
		this.equipoGanador = equipoGanador;
	}

	/**
	 * @return the equipoPerdedor
	 */
	public Equipo getEquipoPerdedor() {
		return equipoPerdedor;
	}

	/**
	 * @param equipoPerdedor the equipoPerdedor to set
	 */
	public void setEquipoPerdedor(Equipo equipoPerdedor) {
		this.equipoPerdedor = equipoPerdedor;
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

	/**
	 * @return the turnoActual
	 */
	public String getTurnoActual() {
		return turnoActual;
	}

	/**
	 * @param turnoActual the turnoActual to set
	 */
	public void setTurnoActual(String turnoActual) {
		this.turnoActual = turnoActual;
	}

	/**
	 * @return the batallaActiva
	 */
	public boolean isBatallaActiva() {
		return batallaActiva;
	}

	/**
	 * @param batallaActiva the batallaActiva to set
	 */
	public void setBatallaActiva(boolean batallaActiva) {
		this.batallaActiva = batallaActiva;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Batalla [id=" + id + ", equipo1=" + equipo1 + ", equipo2=" + equipo2 + ", equipoGanador="
				+ equipoGanador + ", equipoPerdedor=" + equipoPerdedor + ", modo=" + modo + ", historialTurnos="
				+ historialTurnos + ", turnoActual=" + turnoActual + ", estado=" + estado + "]";
	}
}