package co.edu.unbosque.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.service.PokemonService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/pokemon" })
public class PokemonController {

	@Autowired
	private PokemonService pokemonSer;

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String url) {
		int status = pokemonSer.create(url);
		if (status == 0) {
			return new ResponseEntity<>("Pokemon creado exitosamente en BD", HttpStatus.CREATED);
		} else if (status == 1) {
			return new ResponseEntity<String>("Pokemon ya creado en BD", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity<String>("Error al crear el Pokemon", HttpStatus.NOT_ACCEPTABLE);

		}
	}
	

	@GetMapping("/getall")
	ResponseEntity<List<PokemonDTO>> getAll() {
		List<PokemonDTO> admins = pokemonSer.getAll();
		if (admins.isEmpty()) {
			return new ResponseEntity<>(admins, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(admins, HttpStatus.ACCEPTED);
		}
	}

}
