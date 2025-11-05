package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.EquipoDTO;
import co.edu.unbosque.pokemon.entity.Equipo;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.EquipoRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

@Service
public class EquipoService implements CRUDOperation<EquipoDTO> {

	@Autowired
	private EquipoRepository equiRepo;

	@Autowired
	private PokemonRepository pokeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public int create(EquipoDTO newData) {
		Equipo entity = modelMapper.map(newData, Equipo.class);
		if (equiRepo.findByNombre(newData.getNombre()).isPresent()) {
			return 1;
		} else {
			equiRepo.save(entity);
			return 0;
		}
	}

	public int create(String nombre, long id, String... pokemones) {
		if (pokemones.length != 6) {
			return 1;
		} else {
			ArrayList<Pokemon> listaPokemones = new ArrayList<>();
			for (String nombrePokemon : pokemones) {
				Optional<Pokemon> p = pokeRepo.findByNombre(nombrePokemon.toLowerCase());
				if (p.isEmpty()) {
					return 2;
				}
				listaPokemones.add(p.get());
			}
			Equipo equi = new Equipo(nombre, id, listaPokemones);
			equiRepo.save(equi);
			return 0;
		}

	}

	@Override
	public List<EquipoDTO> getAll() {
		List<Equipo> entityList = equiRepo.findAll();
		List<EquipoDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {

			EquipoDTO dto = modelMapper.map(entity, EquipoDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteByID(Long id) {
		if (equiRepo.findById(id).isPresent()) {
			equiRepo.deleteById(id);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByID(Long id, EquipoDTO newData) {
		Optional<Equipo> found = equiRepo.findById(id);
		Optional<Equipo> newFound = equiRepo.findByNombre(newData.getNombre());
		if (found.isPresent() && !newFound.isPresent()) {
			Equipo temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setIdUsuario(newData.getIdUsuario());
			ArrayList<Pokemon> listaPokemones = new ArrayList<>();
			for (int i = 0; i < 6; i++) {
				Pokemon poke = modelMapper.map(newData.getListaPokemon().get(i), Pokemon.class);
				listaPokemones.add(poke);
			}
			temp.setListaPokemon(listaPokemones);
			equiRepo.save(temp);
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
