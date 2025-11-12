package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.EquipoService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/equipo" })
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
}
