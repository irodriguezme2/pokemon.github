package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class TypeDTO {
	
	private int slot; 
	private TypePokemonDTO type; 
	
	public TypeDTO() {
		// TODO Auto-generated constructor stub
	}

	public TypeDTO(int slot, TypePokemonDTO type) {
		super();
		this.slot = slot;
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(slot, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeDTO other = (TypeDTO) obj;
		return slot == other.slot && Objects.equals(type, other.type);
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}

	/**
	 * @return the type
	 */
	public TypePokemonDTO getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypePokemonDTO type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TypeDTO [slot=" + slot + ", type=" + type + "]";
	}
	
	
	

}
