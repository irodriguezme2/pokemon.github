package co.edu.unbosque.pokemon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ability {
	private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) long id;
	private String nombre; // Nombre de la habilidad (ej: Blaze)
	private String descripcion; // Breve descripción del efecto

	// Constructor vacío
	public Ability() {
	}

	// Constructor con parámetros
	public Ability(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Método opcional para mostrar la habilidad
	@Override
	public String toString() {
		return nombre + " (" + descripcion + ")";
	}
}
