package co.edu.unbosque.pokemon.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario.Role;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:8082" })
@Transactional
@Tag(name = "Gestión de Usuarios", description = "Endpoints para administrar usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
	@Autowired
	private UsuarioService userSer;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestParam String correo, String nombre, String contrasenia,
			Date fechaNacimiento, boolean esHombre) {
		UsuarioDTO newUser = new UsuarioDTO(correo, nombre, contrasenia, Role.USER, fechaNacimiento, esHombre);
		int status = userSer.create(newUser);
		if (status == 0) {
			return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<String>("Usuario ya existente, por favor ingrese otro nombre",
					HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 3) {
			return new ResponseEntity<String>("Correo ya existente, inicie sesion",
					HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<String>("Correo incorrecto", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<String>("Error al crear el Usuario", HttpStatus.NOT_ACCEPTABLE);

		}
	}

	
	@GetMapping("/verificar")
	public ResponseEntity<String> verificarCuenta(@RequestParam int token) {
		boolean verificado = userSer.verificarUsuarioPorToken(token);

		if (verificado) {
			return new ResponseEntity<>("Cuenta verificada con éxito. ¡Ya puedes iniciar sesión!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Token inválido o expirado.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/createjson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<String> createNewWithJSON(@RequestBody UsuarioDTO newUser) {
		int status = userSer.create(newUser);

		if (status == 0) {
			return new ResponseEntity<>("Usuario Creado correctamente", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el usuario, correo ya existente", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PostMapping(path = "/checklogin")
	ResponseEntity<String> checkLogIn(@RequestParam String username, @RequestParam String password) {

		int status = userSer.validateCredentials(username, password);

		if (status == 0) {
			return new ResponseEntity<>("Credenciales Correctas", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Correo o contraseña incorrecta", HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/getall")
	ResponseEntity<List<UsuarioDTO>> getAll() {
		List<UsuarioDTO> admins = userSer.getAll();
		if (admins.isEmpty()) {
			return new ResponseEntity<>(admins, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(admins, HttpStatus.ACCEPTED);
		}
	}

	@DeleteMapping("/deletebyid/{id}")
	ResponseEntity<String> deleteById(@PathVariable Long id) {
		int status = userSer.deleteByID(id);

		if (status == 0) {
			return new ResponseEntity<>("Administrador eliminado correctamente", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error al eliminar", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody UsuarioDTO loginRequest) {
		int status = userSer.validateCredentials(loginRequest.getCorreo(), loginRequest.getContrasenia());

		if (status == 0) {
			return new ResponseEntity<>("Inicio de sesión exitoso", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
		}
	}
	
}