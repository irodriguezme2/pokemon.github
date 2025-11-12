package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.EquipoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/equipo")
@CrossOrigin(origins = { "http://localhost:8082" })
@Transactional
@Tag(name = "Gestión de Equipos", description = "Endpoints para administrar Equipos")
@SecurityRequirement(name = "bearerAuth")
public class EquipoController {

	@Autowired
	private EquipoService equiService;

	@PostMapping("/crear")
	public ResponseEntity<String> crearEquipo(@RequestParam String nombre, @RequestParam(required = false) Long id,
			@RequestParam String[] pokemones) {

		int status = equiService.create(nombre, id, pokemones);

		switch (status) {
		case 1:
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body("El equipo debe tener exactamente 6 integrantes");
		case 2:
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Uno o más Pokémon ingresados no existen");
		default:
			return ResponseEntity.status(HttpStatus.CREATED).body("Equipo creado exitosamente");
		}
	}

	@DeleteMapping("/deletebyname")
	ResponseEntity<String> deleteByName(
			@Parameter(description = "Nombre del equipo a eliminar", required = true, example = "equipo1") @RequestParam String name) {
		int status = equiService.deleteByUsername(name);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error al eliminar", HttpStatus.NOT_FOUND);
		}
	}

}
