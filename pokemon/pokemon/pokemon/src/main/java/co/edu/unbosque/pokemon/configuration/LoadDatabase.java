package co.edu.unbosque.pokemon.configuration;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;

/**
 * Clase de configuración para cargar datos iniciales en la base de datos. Crea
 * usuarios predeterminados (administrador y usuario normal) al iniciar la
 * aplicación si estos no existen previamente.
 */
@Configuration
public class LoadDatabase {
	/** Logger para registrar mensajes durante la carga de datos. */
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	/**
	 * Inicializa la base de datos con usuarios predeterminados. Crea un usuario
	 * administrador y un usuario normal si no existen.
	 *
	 * @param userRepo        Repositorio de usuarios para acceder a la base de
	 *                        datos
	 * @param passwordEncoder Codificador de contraseñas para encriptar las
	 *                        contraseñas de los usuarios
	 * @return Un CommandLineRunner que se ejecuta al iniciar la aplicación
	 */
	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {

		return args -> {
			Optional<Usuario> found = userRepo.findByNombreUsuario("user");
			if (found.isPresent()) {
				log.info("El usuario ya existe, omitiendo la creación del administrador...");
			} else {
				Usuario adminUser = new Usuario("isarm2412@gmail.com", "user", passwordEncoder.encode("1234567890"),
						Usuario.Role.USER, null);
				userRepo.save(adminUser);
				log.info("Precargando usuario");
			}

		};
	}
}
