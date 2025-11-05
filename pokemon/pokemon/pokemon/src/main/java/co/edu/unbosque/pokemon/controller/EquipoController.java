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

	@PostMapping("/crearswagger")
	public ResponseEntity<String> crearEquipoSwagger(@RequestParam String nombre, Long id, String pokemon1, String pokemon2,
			String pokemon3, String pokemon4, String pokemon5, String pokemon6) {
		int status = equiService.create(nombre, id, pokemon1, pokemon2, pokemon3, pokemon4, pokemon5, pokemon6);
		if (status == 1) {
			return new ResponseEntity<>("El equipo debe tener 6 integrantes", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<String>("El pokemon ingresado no existe", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Equipo creado exitosamente", HttpStatus.CREATED);
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crearEquipo(@RequestParam String nombre, Long id, String... pokemones) {
		int status = equiService.create(nombre, id, pokemones);
		if (status == 1) {
			return new ResponseEntity<>("El equipo debe tener 6 integrantes", HttpStatus.NOT_ACCEPTABLE);
		} else if (status == 2) {
			return new ResponseEntity<String>("El pokemon ingresado no existe", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<>("Equipo creado exitosamente", HttpStatus.CREATED);
		}
	}

}
