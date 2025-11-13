package co.edu.unbosque.pokemon.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

/**
 * Representa un equipo Pokémon dentro del sistema PokéLab.
 * <p>
 * Cada equipo pertenece a un usuario específico y contiene una lista de Pokémon
 * seleccionados para participar en batallas.
 * </p>
 *
 * <p>
 * <b>Ejemplo:</b> Un equipo llamado "Elite de Kanto" perteneciente al usuario
 * con ID 102, compuesto por seis Pokémon distintos listos para competir en una
 * batalla.
 * </p>
 *
 * @author PokéLab
 * @version 1.0
 */
@Entity
public class Equipo {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	private String nombre;
	private long idUsuario;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "equipo_id")
	private List<Pokemon> listaPokemon = new ArrayList<>();

	public Equipo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor que permite crear un equipo con sus atributos principales.
	 *
	 * @param nombre       Nombre del equipo.
	 * @param idUsuario    Identificador del usuario propietario.
	 * @param listaPokemon Lista de Pokémon que conforman el equipo.
	 */
	public Equipo(String nombre, long idUsuario, ArrayList<Pokemon> listaPokemon) {
		super();
		this.nombre = nombre;
		this.idUsuario = idUsuario;
		this.listaPokemon = listaPokemon;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idUsuario, listaPokemon, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipo other = (Equipo) obj;
		return id == other.id && idUsuario == other.idUsuario && Objects.equals(listaPokemon, other.listaPokemon)
				&& Objects.equals(nombre, other.nombre);
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
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the listaPokemon
	 */
	public ArrayList<Pokemon> getListaPokemon() {
		return (ArrayList<Pokemon>) listaPokemon;
	}

	/**
	 * @param pokemones the listaPokemon to set
	 */
	public void setListaPokemon(List<Pokemon> pokemones) {
		this.listaPokemon = pokemones;
	}

	@Override
	public String toString() {
		return "Equipo [id=" + id + ", nombre=" + nombre + ", idUsuario=" + idUsuario + ", listaPokemon=" + listaPokemon
				+ "]";
	}

}
