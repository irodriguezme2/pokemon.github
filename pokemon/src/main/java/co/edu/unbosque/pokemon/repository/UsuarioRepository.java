package co.edu.unbosque.pokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.pokemon.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findById(Long id);

	public Optional<Usuario> findByNombreUsuario(String nombreUsuario);

	public Optional<Usuario> findByCorreo(String correo);

	public Optional<Usuario> findByToken(int token);

	public void deleteByCorreo(String username);

}
