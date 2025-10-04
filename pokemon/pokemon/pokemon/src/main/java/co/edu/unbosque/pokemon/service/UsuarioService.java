package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.util.AESUtil;
import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;

	public UsuarioService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(UsuarioDTO newData) {
		Usuario user = modelMapper.map(newData, Usuario.class);
		if (userRepo.findByCorreo(newData.getCorreo()).isPresent()) {

			Optional<Usuario> adminiss = userRepo.findByCorreo(newData.getCorreo());
			System.out.println(adminiss.get().getCorreo());
			return 1;
		} else {
			userRepo.save(user);
			return 0;
		}
	}

	@Override
	public List<UsuarioDTO> getAll() {
		List<Usuario> entityList = userRepo.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {

			UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	public boolean existByCorreo(String correo) {
		return userRepo.findByCorreo(correo).isPresent();
	}

	@Override
	public int deleteByID(Long id) {
		if (userRepo.findById(id).isPresent()) {
			userRepo.deleteById(id);
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateByID(Long id, UsuarioDTO newData) {
		Optional<Usuario> found = userRepo.findById(id);
		Optional<Usuario> newFound = userRepo.findByNombreUsuario(newData.getNombreUsuario());
		if (found.isPresent() && !newFound.isPresent()) {
			Usuario temp = found.get();
			temp.setNombreUsuario(newData.getNombreUsuario());
			temp.setCorreo(newData.getCorreo());
			temp.setContrasenia(newData.getContrasenia());
			temp.setFechaNacimiento(newData.getFechaNacimiento());
			userRepo.save(temp);
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

	public int validateCredentials(String correo, String password) {
		password = AESUtil.decrypt("keyfrontfirstenc", "iviviviviviviviv", password);
		for (UsuarioDTO u : getAll()) {
			if (u.getCorreo().equals(correo)) {
				if (u.getContrasenia().equals(password)) {
					return 0;
				}
			}
		}
		return 1;
	}

}
