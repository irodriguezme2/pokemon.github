package co.edu.unbosque.pokemon.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa a un usuario dentro del sistema PokéLab y se integra con Spring
 * Security mediante la implementación de {@link UserDetails}.
 * <p>
 * Contiene credenciales, información personal, estado de la cuenta y rol del
 * usuario.
 * </p>
 *
 * <p>
 * <b>Ejemplo:</b> Un usuario con correo "ash@pokelab.com", nombreUsuario
 * "ashKetchum", rol USER y cuenta habilitada para iniciar sesión en la
 * aplicación.
 * </p>
 *
 * @author PokéLab
 * @version 1.0
 */
@Entity
@Table(name = "usuarioPokemon")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	private String correo;
	@Column(unique = true, name = "nombreUsuario")
	private String nombreUsuario;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String contrasenia;
	private Date fechaNacimiento;
	private boolean esHombre;
	private boolean verificado;
	private int token;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	/**
	 * Constructor por defecto.
	 * <p>
	 * Inicializa los flags de la cuenta en valores por defecto y asigna
	 * {@code Role.USER} como rol predeterminado.
	 * </p>
	 */
	public Usuario() {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.verificado = false;
		this.enabled = true;
		this.role = Role.USER;
	}

	/**
	 * Enum que define los roles posibles para un usuario.
	 */
	public enum Role {
		USER, ADMIN,
	}

	/**
	 * Constructor completo del usuario.
	 *
	 * @param correo          correo electrónico.
	 * @param nombreUsuario   nombre de usuario único.
	 * @param contrasenia     contraseña del usuario.
	 * @param rol             rol asignado.
	 * @param fechaNacimiento fecha de nacimiento.
	 * @param esHombre        indicador de género.
	 */
	public Usuario(String correo, String nombreUsuario, String contrasenia, Role rol, Date fechaNacimiento,
			boolean esHombre) {
		this();
		this.correo = correo;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
		this.role = rol;
		this.fechaNacimiento = fechaNacimiento;
		this.esHombre = esHombre;
	}

	/**
	 * Constructor con correo y contraseña.
	 *
	 * @param correo      correo electrónico.
	 * @param contrasenia contraseña del usuario.
	 */
	public Usuario(String correo, String contrasenia) {
		this();
		this.correo = correo;
		this.contrasenia = contrasenia;
	}

	/**
	 * Constructor con nombre de usuario, rol y contraseña.
	 *
	 * @param nombreUsuario nombre de usuario.
	 * @param role          rol asignado.
	 * @param contrasenia   contraseña del usuario.
	 */
	public Usuario(String nombreUsuario, Role role, String contrasenia) {
		this();
		this.nombreUsuario = nombreUsuario;
		this.role = role;
		this.contrasenia = contrasenia;
	}

	/**
	 * Calcula el código hash del usuario a partir de sus atributos principales.
	 *
	 * @return valor hash único.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, esHombre, fechaNacimiento, id, nombreUsuario);
	}

	/**
	 * Compara dos usuarios según su información básica y credenciales.
	 *
	 * @param obj objeto a comparar.
	 * @return {@code true} si ambos usuarios son equivalentes.
	 */
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return contrasenia;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nombreUsuario;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
