package co.edu.unbosque.pokemon.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movimiento implements Serializable {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	private String nombre;
	private int danio;
	private String tipo;
	private int puntosPoder;

	public Movimiento() {
		// TODO Auto-generated constructor stub
	}

	public Movimiento(String nombre, int danio, String tipo, int puntosPoder) {
		super();

		this.nombre = nombre;
		this.danio = danio;
		this.tipo = tipo;
		this.puntosPoder = puntosPoder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(danio, nombre, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movimiento other = (Movimiento) obj;
		return danio == other.danio && Objects.equals(nombre, other.nombre) && Objects.equals(tipo, other.tipo);
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
	 * @return the danio
	 */
	public int getDanio() {
		return danio;
	}

	/**
	 * @param danio the danio to set
	 */
	public void setDanio(int danio) {
		this.danio = danio;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	 * @return the puntosPoder
	 */
	public int getPuntosPoder() {
		return puntosPoder;
	}

	/**
	 * @param puntosPoder the puntosPoder to set
	 */
	public void setPuntosPoder(int puntosPoder) {
		this.puntosPoder = puntosPoder;
	}

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", nombre=" + nombre + ", danio=" + danio + ", tipo=" + tipo + ", puntosPoder="
				+ puntosPoder + "]";
	}

}
