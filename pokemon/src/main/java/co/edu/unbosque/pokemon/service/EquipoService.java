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
import jakarta.transaction.Transactional;

/**
 * Servicio encargado de gestionar las operaciones CRUD relacionadas con los
 * equipos de Pokémon. Permite la creación, actualización, eliminación y
 * consulta de equipos asociados a los usuarios.
 * 
 * <p>
 * Incluye validaciones de unicidad por nombre y verificación de existencia de
 * los Pokémon que conforman el equipo.
 * </p>
 * 
 * @author PokéLab
 * @version 1.0
 */
@Service
public class EquipoService implements CRUDOperation<EquipoDTO> {
	@Autowired
	private PokemonService pokemonService;

	@Autowired
	private EquipoRepository equiRepo;

	@Autowired
	private PokemonRepository pokeRepo;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Crea un nuevo equipo en el sistema a partir de un {@link EquipoDTO}.
	 * 
	 * @param newData DTO que contiene los datos del nuevo equipo
	 * @return 0 si el equipo fue creado correctamente, 1 si el nombre ya existe
	 */
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

	/**
	 * Crea un equipo a partir de nombres de Pokémon y el identificador del usuario.
	 * 
	 * @param nombre    nombre del equipo
	 * @param id        identificador del usuario propietario
	 * @param pokemones nombres de los seis Pokémon que integran el equipo
	 * @return 0 si el equipo fue creado, 1 si no tiene 6 Pokémon, 2 si alguno no
	 *         existe
	 */
	@Transactional
	public int create(String nombre, Long idUsuario, String... nombresPokemones) {
		if (nombresPokemones.length != 6) {
			return 1; // Error: el equipo debe tener 6 integrantes
		}

		ArrayList<Pokemon> lista = new ArrayList<>();

		for (String nombrePoke : nombresPokemones) {
			try {
				Pokemon p = pokemonService.createByName(nombrePoke.trim());
				lista.add(p);
			} catch (Exception e) {
				return 2; // Error: Pokémon no existe o fallo la API
			}
		}

		Equipo equipo = new Equipo(nombre, idUsuario, lista);
		equiRepo.save(equipo);

		return 0; // Éxito
	}

	/**
	 * Obtiene la lista completa de equipos registrados.
	 * 
	 * @return lista de {@link EquipoDTO} con todos los equipos existentes
	 */
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

	/**
	 * Elimina un equipo del sistema a partir de su identificador.
	 * 
	 * @param id identificador del equipo
	 * @return 0 si se eliminó correctamente, 1 si no existe
	 */
	@Override
	public int deleteByID(Long id) {
		if (equiRepo.findById(id).isPresent()) {
			equiRepo.deleteById(id);
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * Actualiza los datos de un equipo existente por su identificador.
	 * 
	 * @param id      identificador del equipo
	 * @param newData datos actualizados en formato {@link EquipoDTO}
	 * @return 0 si la actualización fue exitosa, 1 si el nuevo nombre ya está en
	 *         uso, 2 si el equipo no existe, 3 para errores no esperados
	 */
	public int deleteByUsername(String username) {
		Optional<Equipo> found = equiRepo.findByNombre(username);
		if (found.isPresent()) {
			equiRepo.delete(found.get());
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
