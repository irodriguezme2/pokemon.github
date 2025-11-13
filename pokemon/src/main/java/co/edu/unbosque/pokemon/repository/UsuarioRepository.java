package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Usuario;


/**
 * Repositorio para gestionar las operaciones CRUD de la entidad
 * {@link Usuario}. Permite realizar búsquedas y eliminaciones basadas en
 * diferentes atributos del usuario.
 * 
 * @author PokéLab
 * @version 1.0
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Busca un usuario por su identificador.
	 *
	 * @param id identificador del usuario
	 * @return un {@link Optional} que contiene el usuario si existe
	 */
	public Optional<Usuario> findById(Long id);

	/**
	 * Busca un usuario por su nombre de usuario.
	 *
	 * @param nombreUsuario nombre de usuario a buscar
	 * @return un {@link Optional} que contiene el usuario si existe
	 */
	public Optional<Usuario> findByNombreUsuario(String nombreUsuario);


	/**
	 * Busca un usuario por su correo electrónico.
	 *
	 * @param correo correo del usuario
	 * @return un {@link Optional} que contiene el usuario si existe
	 */
	public Optional<Usuario> findByCorreo(String correo);

	/**
	 * Busca un usuario por su token de autenticación.
	 *
	 * @param token token asociado al usuario
	 * @return un {@link Optional} que contiene el usuario si existe
	 */
	public Optional<Usuario> findByToken(int token);


	/**
	 * Elimina un usuario basado en su correo electrónico.
	 *
	 * @param username correo del usuario a eliminar
	 */
	public void deleteByCorreo(String username);

}
