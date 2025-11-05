package co.edu.unbosque.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperation<UsuarioDTO> {

	@Autowired
	private UsuarioRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(UsuarioDTO newData) {
		Usuario user = modelMapper.map(newData, Usuario.class);
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		if (userRepo.findByCorreo(newData.getCorreo()).isPresent()) {
			Optional<Usuario> adminiss = userRepo.findByCorreo(newData.getCorreo());
			System.out.println(adminiss.get().getCorreo());
			return 1;
		} else if (user.getCorreo().matches(regex) == false) {
			return 2; 
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

	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		Optional<Usuario> found = userRepo.findByNombreUsuario(newUser.getUsername());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public int validateCredentials(String username, String password) {
		// Buscar usuario por nombre de usuario
		Optional<Usuario> userOpt = userRepo.findByNombreUsuario(username);

		// Verificar si el usuario existe y la contraseña coincide
		if (userOpt.isPresent()) {
			Usuario user = userOpt.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 0; // Éxito
			}
		}

		return 1; // Credenciales inválidas
	}

	public boolean findUsernameAlreadyTaken(String username) {
		Optional<Usuario> found = userRepo.findByNombreUsuario(username);
		return found.isPresent();
	}

}
