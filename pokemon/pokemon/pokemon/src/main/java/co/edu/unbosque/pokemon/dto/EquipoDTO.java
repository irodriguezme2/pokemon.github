package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList;
import java.util.Objects;

import co.edu.unbosque.pokemon.entity.Pokemon;

public class EquipoDTO {

	private long id;
	private String nombre;
	private long idUsuario;
	private ArrayList<Pokemon> listaPokemon;

	public EquipoDTO() {
		// TODO Auto-generated constructor stub
	}

	public EquipoDTO(String nombre, long idUsuario, ArrayList<Pokemon> listaPokemon) {
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
		EquipoDTO other = (EquipoDTO) obj;
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
		return listaPokemon;
	}

	/**
	 * @param listaPokemon the listaPokemon to set
	 */
	public void setListaPokemon(ArrayList<Pokemon> listaPokemon) {
		this.listaPokemon = listaPokemon;
	}

	@Override
	public String toString() {
		return "Equipo [id=" + id + ", nombre=" + nombre + ", idUsuario=" + idUsuario + ", listaPokemon=" + listaPokemon
				+ "]";
	}

}
