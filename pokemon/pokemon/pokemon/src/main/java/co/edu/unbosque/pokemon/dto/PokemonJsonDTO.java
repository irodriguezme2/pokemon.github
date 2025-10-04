package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList;

public class PokemonJsonDTO {

	private String name;
	private ArrayList<TypeDTO> types;
	private ArrayList<MoveDTO> moves;

	public PokemonJsonDTO() {
		// TODO Auto-generated constructor stub
	}

	public PokemonJsonDTO(String name, ArrayList<TypeDTO> types, ArrayList<MoveDTO> moves) {
		super();
		this.name = name;
		this.types = types;
		this.moves = moves;
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

	/**
	 * @return the types
	 */
	public ArrayList<TypeDTO> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(ArrayList<TypeDTO> types) {
		this.types = types;
	}

	/**
	 * @return the moves
	 */
	public ArrayList<MoveDTO> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set
	 */
	public void setMoves(ArrayList<MoveDTO> moves) {
		this.moves = moves;
	}

	@Override
	public String toString() {
		return "PokemonJsonDTO [name=" + name + ", types=" + types + ", moves=" + moves + "]";
	}

}
