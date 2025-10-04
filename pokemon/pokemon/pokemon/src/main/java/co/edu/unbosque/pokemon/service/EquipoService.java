package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.dto.EquipoDTO;
import co.edu.unbosque.pokemon.entity.Equipo;
import co.edu.unbosque.pokemon.repository.EquipoRepository;

@Service
public class EquipoService implements CRUDOperation<EquipoDTO> {

	@Autowired
	private EquipoRepository equiRepo;

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
			temp.setListaPokemon(newData.getListaPokemon());
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
