package co.edu.unbosque.pokemon.dto;

import java.util.Date;
import java.util.Objects;

public class UsuarioDTO {

	private Long id;
	private String correo;
	private String nombreUsuario;
	private String contrasenia;
	private Date fechaNacimiento;

	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public UsuarioDTO(String correo, String nombreUsuario, String contrasenia, Date fechaNacimiento) {

		this.correo = correo;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, fechaNacimiento, id, nombreUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && Objects.equals(id, other.id)
				&& Objects.equals(nombreUsuario, other.nombreUsuario);
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", correo=" + correo + ", nombreUsuario=" + nombreUsuario + ", contrasenia="
				+ contrasenia + ", fechaNacimiento=" + fechaNacimiento + "]";
	}

}
