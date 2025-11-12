package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class TypePokemonDTO {
	
	private String name; 
	
	public TypePokemonDTO() {
		// TODO Auto-generated constructor stub
	}

	public TypePokemonDTO(String name) {
		super();
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypePokemonDTO other = (TypePokemonDTO) obj;
		return Objects.equals(name, other.name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TypePokemonDTO [name=" + name + "]";
	}
	
	

}
