package co.edu.unbosque.pokemon.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearEquipoDTO {

	@NotNull(message = "idUsuario no puede ser nulo")
	private Long idUsuario;

	@NotEmpty(message = "nombre no puede estar vacío")
	private String nombre;

	@NotNull(message = "pokemones no puede ser nulo")
	@Size(min = 6, max = 6, message = "El equipo debe contener exactamente 6 pokemones")
	private List<@NotEmpty(message = "nombre de pokemon no puede estar vacío") String> pokemones;

	public CrearEquipoDTO() {
		// TODO Auto-generated constructor stub
	}

	public CrearEquipoDTO(@NotNull(message = "idUsuario no puede ser nulo") Long idUsuario,
			@NotEmpty(message = "nombre no puede estar vacío") String nombre,
			@NotNull(message = "pokemones no puede ser nulo") @Size(min = 6, max = 6, message = "El equipo debe contener exactamente 6 pokemones") List<@NotEmpty(message = "nombre de pokemon no puede estar vacío") String> pokemones) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.pokemones = pokemones;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getPokemones() {
		return pokemones;
	}

	public void setPokemones(List<String> pokemones) {
		this.pokemones = pokemones;
	}

	@Override
	public String toString() {
		return "CrearEquipoRequest [idUsuario=" + idUsuario + ", nombre=" + nombre + ", pokemones=" + pokemones + "]";
	}
}
