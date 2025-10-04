package co.edu.unbosque.pokemon.entity;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Pokemon {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	@Column(unique = true, name = "nombreUsuario")
	private String nombre;
	@ElementCollection
	private List<String> tipo;
	@Min(value = 0, message = "El valor mínimo es 0")
	@Max(value = 100, message = "El valor mínimo es 100")
	private int puntosSalud = 100;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pokemon_id")
	private List<Movimiento> movimiento;

	public Pokemon() {
		// TODO Auto-generated constructor stub
	}

	public Pokemon(String nombre, List<String> tipo,
			@Min(value = 0, message = "El valor mínimo es 0") @Max(value = 100, message = "El valor mínimo es 100") int puntosSalud,
			List<Movimiento> movimiento) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.puntosSalud = puntosSalud;
		this.movimiento = movimiento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, movimiento, nombre, puntosSalud, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		return id == other.id && Objects.equals(movimiento, other.movimiento) && Objects.equals(nombre, other.nombre)
				&& puntosSalud == other.puntosSalud && Objects.equals(tipo, other.tipo);
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
	public List<Movimiento> getMovimiento() {
		return movimiento;
	}

	/**
	 * @param movimiento the movimiento to set
	 */
	public void setMovimiento(List<Movimiento> movimiento) {
		this.movimiento = movimiento;
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", puntosSalud=" + puntosSalud
				+ ", movimiento=" + movimiento + "]";
	}

}
