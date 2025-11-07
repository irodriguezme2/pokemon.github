package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.MovimientoJsonDTO;
import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.dto.PokemonJsonDTO;
import co.edu.unbosque.pokemon.dto.AbilityDTO;
import co.edu.unbosque.pokemon.dto.EstadisticaDTO;
import co.edu.unbosque.pokemon.dto.EstadisticaJsonDTO;
import co.edu.unbosque.pokemon.dto.MoveDTO;
import co.edu.unbosque.pokemon.dto.MovimientoDTO;
import co.edu.unbosque.pokemon.entity.Estadistica;
import co.edu.unbosque.pokemon.entity.Movimiento;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.entity.Pokemon.Estado;
import co.edu.unbosque.pokemon.repository.MovimientoRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.util.TypeEffectiveness;

@Service
public class PokemonService implements CRUDOperation<PokemonDTO> {

	@Autowired
	private PokemonRepository pokeRepo;
	@Autowired
	private MovimientoRepository movimientoRepo;

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
		ArrayList<String> tipos = new ArrayList<>();
		if (pokeRepo.findByNombre(poke.getName()).isPresent() || poke.getMoves().isEmpty()) {
			return 1;
		}
		ArrayList<Movimiento> movimientos = new ArrayList<>();
		ArrayList<MoveDTO> moves = poke.getMoves();
		Collections.shuffle(moves);
		int limite = Math.min(3, moves.size());

		for (int i = 0; i < limite; i++) {
			MovimientoJsonDTO movi = ClienteHTTP.doGetMovimientoJsonDTO(moves.get(i).getMove().getUrl());
			Movimiento movimientoEntidad = movimientoRepo.findByNombre(movi.getName()).orElseGet(() -> {
				Movimiento m = new Movimiento(movi.getName(), movi.getPower(), movi.getType().getName(), movi.getPp());
				movimientoRepo.save(m);
				return m;
			});

			movimientos.add(movimientoEntidad);
		}
		ArrayList<Estadistica> estadisticas = new ArrayList<>();
		int hpBase = 0;
		for (EstadisticaJsonDTO statDto : poke.getStats()) {
			if (statDto.getStat() == null)
				continue;

			String nombre = statDto.getStat().getName();
			int valor = statDto.getBase_stat();
			if ("hp".equals(nombre))
				hpBase = valor;

			if ("hp".equals(nombre) || "attack".equals(nombre) || "speed".equals(nombre) || "defense".equals(nombre)) {
				estadisticas.add(new Estadistica(nombre, valor));
			}
		}

		for (int i = 0; i < poke.getTypes().size(); i++) {
			tipos.add(poke.getTypes().get(i).getType().getName());
		}
		Pokemon pokeEntidad = new Pokemon(poke.getName(), tipos, movimientos, estadisticas);

		if (!tipos.isEmpty()) {
			String tipoPrincipal = tipos.get(0);
			pokeEntidad.setAbility(TypeEffectiveness.getAbilityByType(tipoPrincipal));
		} else {
			pokeEntidad.setAbility(TypeEffectiveness.getAbilityByType("normal"));
		}

		pokeEntidad.setEstado(Estado.NORMAL);
		pokeEntidad.setHpActual(hpBase);
		pokeRepo.save(pokeEntidad);

		return 0;
	}

	@Override
	public List<PokemonDTO> getAll() {
		List<Pokemon> entityList = pokeRepo.findAll();
		List<PokemonDTO> dtoList = new ArrayList<>();

		for (Pokemon entity : entityList) {
			PokemonDTO dto = modelMapper.map(entity, PokemonDTO.class);

			// Mapear estadísticas
			List<EstadisticaDTO> statsDto = entity.getEstadistica().stream()
					.map(e -> new EstadisticaDTO(e.getNombre(), e.getValor())).collect(Collectors.toList());
			dto.setEstadistica(statsDto);

			// Mapear habilidad
			if (entity.getAbility() != null) {
				dto.setAbility(new AbilityDTO(entity.getAbility().getNombre(), entity.getAbility().getDescripcion()));
			}

			// Mapear movimientos
			List<MovimientoDTO> movimientosDto = entity.getMovimiento().stream()
					.map(m -> new MovimientoDTO(m.getNombre(), m.getDanio(), m.getTipo(), m.getPuntosPoder()))
					.collect(Collectors.toList());
			dto.setMovimiento(movimientosDto);

			dtoList.add(dto);
		}

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
