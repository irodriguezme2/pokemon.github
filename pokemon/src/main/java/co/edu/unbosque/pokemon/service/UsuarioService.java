package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.entity.Usuario.Role;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;

/**
 * Servicio encargado de gestionar las operaciones relacionadas con los usuarios
 * del sistema Pokémon, incluyendo creación, autenticación, actualización,
 * eliminación y verificación por token.
 * 
 * <p>
 * Este servicio implementa la interfaz {@link CRUDOperation} para ofrecer las
 * operaciones básicas y extiende su funcionalidad con validaciones de
 * credenciales, verificación por correo electrónico y generación de tokens.
 * </p>
 * 
 * <p>
 * Funcionalidades principales:
 * <ul>
 * <li>Registrar nuevos usuarios con verificación por correo.</li>
 * <li>Encriptar contraseñas usando {@link PasswordEncoder}.</li>
 * <li>Verificar existencia y validez de credenciales.</li>
 * <li>Eliminar o actualizar información de usuarios existentes.</li>
 * </ul>
 * </p>
 * 
 * @author PokéLab
 * @version 1.0
 */
@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EnvioCorreoService envioCorreo;


	/**
	 * Constructor vacío
	 */
	public UsuarioService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Crea un nuevo usuario en el sistema. Genera un token de verificación
	 * aleatorio y envía un correo electrónico para confirmar el registro.
	 * 
	 * @param newData DTO con los datos del nuevo usuario
	 * @return 0 si se creó correctamente, 1 si el usuario ya está verificado, 2 si
	 *         ya existía pero no estaba verificado
	 */
	@Override
	public int create(UsuarioDTO newData) {
		Usuario user = modelMapper.map(newData, Usuario.class);
		Random tokenR = new Random();
		int token = 10000 + tokenR.nextInt(90000);

		if (findUsernameAlreadyTaken(user)) {
			Optional<Usuario> users = userRepo.findByCorreo(newData.getCorreo());
			if (users.get().isVerificado()) {
				System.out.println(users.get().getCorreo() + "Usuario ya verificado");
				return 1;
			} else {
				user.setVerificado(false);
				user.setToken(token);
				userRepo.save(user);
				envioCorreo.enviarCorreoVerificacion(user.getCorreo(), token);
				return 2;
			}

		} else if (findCorreoAlreadyTaken(user)) {
			System.out.println("llegue aca");
			return 3;
		} else {
			user.setContrasenia(passwordEncoder.encode(user.getPassword()));
			if (newData.getRol() != null) {
				newData.setRol(newData.getRol());
			} else {
				newData.setRol(Role.USER);
			}
			user.setVerificado(false);
			user.setToken(token);
			userRepo.save(user);
			envioCorreo.enviarCorreoVerificacion(user.getCorreo(), token);
			return 0;
		}
	}

	/**
	 * Obtiene todos los usuarios registrados en el sistema.
	 * 
	 * @return lista de {@link UsuarioDTO} con la información de cada usuario
	 */
	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = userRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {

			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}
	
	/**
	 * Verifica si ya existe un usuario registrado con el correo dado.
	 * 
	 * @param correo correo a verificar
	 * @return {@code true} si el correo ya está registrado, {@code false} en caso
	 *         contrario
	 */
	public boolean existByCorreo(String correo) {
		return userRepo.findByCorreo(correo).isPresent();
	}
	
	/**
	 * Elimina un usuario existente por su identificador.
	 * 
	 * @param id identificador del usuario
	 * @return 0 si se eliminó correctamente, 1 si no se encontró
	 */
	@Override
	public int deleteByID(Long id) {
		if (userRepo.findById(id).isPresent()) {
			userRepo.deleteById(id);
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Actualiza los datos de un usuario existente.
	 * 
	 * @param id      identificador del usuario
	 * @param newData nuevos datos a aplicar en el usuario
	 * @return 0 si se actualizó correctamente, 1 si el nombre de usuario ya está en
	 *         uso, 2 si no se encontró el usuario, 3 para errores no esperados
	 */
	@Override
	public int updateByID(Long id, UsuarioDTO newData) {
		Optional<Usuario> found = userRepo.findById(id);
		Optional<Usuario> newFound = userRepo.findByNombreUsuario(newData.getNombreUsuario());
		if (found.isPresent() && !newFound.isPresent()) {
			Usuario temp = found.get();
			temp.setNombreUsuario(newData.getNombreUsuario());
			temp.setCorreo(newData.getCorreo());
			temp.setContrasenia(newData.getContrasenia());
			temp.setFechaNacimiento(newData.getFechaNacimiento());
			userRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1;
		}
		if (!found.isPresent()) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * Verifica si un nombre de usuario ya está tomado, recibiendo un objeto
	 * {@link Usuario}.
	 * 
	 * @param newUser usuario a verificar
	 * @return {@code true} si ya existe, {@code false} si está disponible
	 */
	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		Optional<Usuario> found = userRepo.findByNombreUsuario(newUser.getUsername());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida las credenciales de un usuario al intentar iniciar sesión.
	 * 
	 * @param username nombre de usuario
	 * @param password contraseña ingresada
	 * @return 0 si las credenciales son correctas, 1 si son inválidas
	 */
	public boolean findCorreoAlreadyTaken(Usuario newUser) {
		Optional<Usuario> found = userRepo.findByCorreo(newUser.getCorreo());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Verifica si un nombre de usuario ya está registrado.
	 * 
	 * @param username nombre de usuario a verificar
	 * @return {@code true} si ya existe, {@code false} en caso contrario
	 */
	public int validateCredentials(String username, String password) {
		// Buscar usuario por nombre de usuario
		Optional<Usuario> userOpt = userRepo.findByNombreUsuario(username);

		// Verificar si el usuario existe y la contraseña coincide
		if (userOpt.isPresent()) {
			Usuario user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0; // Éxito
			}
		}

		return 1; // Credenciales inválidas
	}

	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = userRepo.findByNombreUsuario(username);
		return found.isPresent();
	}


	/**
	 * Obtiene la cantidad total de usuarios registrados en el sistema.
	 * 
	 * @return número total de usuarios
	 */
	public long count() {
		return userRepo.count();
	}

	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	public int deleteById(Long id) {
		Optional<Usuario> found = userRepo.findById(id);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteByUsername(String username) {
		Optional<Usuario> found = userRepo.findByNombreUsuario(username);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public boolean verificarUsuarioPorToken(int token) {
		Optional<Usuario> userOpt = userRepo.findByToken(token);

		if (userOpt.isPresent()) {
			Usuario user = userOpt.get();
			user.setVerificado(true);
			user.setToken(0);
			userRepo.save(user);
			return true;
		}
		return false;
	}
	

    public UsuarioDTO obtenerPorNombre(String nombreUsuario) {
        Usuario usuario = userRepo.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

}
