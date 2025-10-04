package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.MovimientoJsonDTO;
import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.dto.PokemonJsonDTO;
import co.edu.unbosque.pokemon.entity.Movimiento;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

@Service
public class PokemonService implements CRUDOperation<PokemonDTO> {

	@Autowired
	private PokemonRepository pokeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(PokemonDTO newData) {
		Pokemon entity = modelMapper.map(newData, Pokemon.class);
		if (pokeRepo.findByNombre(newData.getNombre()).isPresent()) {
			return 1;
		} else {

			pokeRepo.save(entity);
			return 0;
		}
	}

	public int create(String url) {
		PokemonJsonDTO poke = ClienteHTTP.doGetPokemon(url);
		ArrayList<Movimiento> movimientos = new ArrayList<>();
		ArrayList<String> tipos = new ArrayList<>();
		if (poke.getMoves().isEmpty() || pokeRepo.findByNombre(poke.getName()).isPresent()) {
			return 1;
		} else {
			int limite = Math.min(4, poke.getMoves().size());
			for (int i = 0; i < limite; i++) {
				MovimientoJsonDTO movi = ClienteHTTP.doGetMovimientoJsonDTO(poke.getMoves().get(i).getMove().getUrl());
				Movimiento movimientoEntidad = new Movimiento(movi.getName(), movi.getPower(),
						movi.getType().getName());
				movimientos.add(movimientoEntidad);
			}

			for (int i = 0; i < poke.getTypes().size(); i++) {
				tipos.add(poke.getTypes().get(i).getType().getName());
			}

			Pokemon pokeEntidad = new Pokemon(poke.getName(), tipos, 100, movimientos);
			pokeRepo.save(pokeEntidad);
			return 0;
		}
	}

	@Override
	public List<PokemonDTO> getAll() {
		List<Pokemon> entityList = pokeRepo.findAll();
		List<PokemonDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {

			PokemonDTO dto = modelMapper.map(entity, PokemonDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		Optional<Pokemon> found = pokeRepo.findById(id);
		if (found.isPresent()) {
			pokeRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByID(Long id, PokemonDTO newData) {
		Optional<Pokemon> found = pokeRepo.findById(id);
		Optional<Pokemon> newFound = pokeRepo.findByNombre(newData.getNombre());

		if (found.isPresent() && !newFound.isPresent()) {
			Pokemon temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setTipo(newData.getTipo());
			pokeRepo.save(temp);
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

}
