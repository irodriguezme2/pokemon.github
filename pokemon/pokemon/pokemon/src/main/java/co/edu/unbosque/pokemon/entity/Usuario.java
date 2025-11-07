package co.edu.unbosque.pokemon.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	private String correo;
	@Column(unique = true, name = "nombreUsuario")
	private String nombreUsuario;
	private String contrasenia;
	private Date fechaNacimiento;
	private boolean esHombre;
	private boolean verificado;
	private int token;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Usuario(long id, String correo, String nombreUsuario, String contrasenia, Date fechaNacimiento,
			boolean esHombre) {
		super();
		this.id = id;
		this.correo = correo;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
		this.esHombre = esHombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, esHombre, fechaNacimiento, id, nombreUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& esHombre == other.esHombre && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& id == other.id && Objects.equals(nombreUsuario, other.nombreUsuario);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isEsHombre() {
		return esHombre;
	}

	public void setEsHombre(boolean esHombre) {
		this.esHombre = esHombre;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", correo=" + correo + ", nombreUsuario=" + nombreUsuario + ", contrasenia="
				+ contrasenia + ", fechaNacimiento=" + fechaNacimiento + ", esHombre=" + esHombre + "]";
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

}
