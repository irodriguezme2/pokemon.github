package co.edu.unbosque.pokemon.dto;

public class MovimientoJsonDTO {

	private String name;
	private int power;
	private TypePokemonDTO type;

	public MovimientoJsonDTO() {
		// TODO Auto-generated constructor stub
	}

	public MovimientoJsonDTO(String name, int power, TypePokemonDTO type) {
		super();
		this.name = name;
		this.power = power;
		this.type = type;
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
	 * @return the power
	 */
	public int getPower() {
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(int power) {
		this.power = power;
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
		return "MovimientoJsonDTO [name=" + name + ", power=" + power + ", type=" + type + "]";
	}

}
