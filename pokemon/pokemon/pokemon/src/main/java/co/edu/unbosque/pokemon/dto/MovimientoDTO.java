package co.edu.unbosque.pokemon.dto;

import java.io.Serializable;
import java.util.Objects;

public class MovimientoDTO implements Serializable {

	private long id;
	private String nombre;
	private int danio;
	private String tipo;

	public MovimientoDTO() {
		// TODO Auto-generated constructor stub
	}

	public MovimientoDTO(String nombre, int danio, String tipo) {
		super();
		this.nombre = nombre;
		this.danio = danio;
		this.tipo = tipo;
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
		MovimientoDTO other = (MovimientoDTO) obj;
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

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", nombre=" + nombre + ", danio=" + danio + ", tipo=" + tipo + "]";
	}

}
