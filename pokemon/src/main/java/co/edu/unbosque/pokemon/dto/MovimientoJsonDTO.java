package co.edu.unbosque.pokemon.dto;

public class MovimientoJsonDTO {

	private String name;
	private int power;
	private TypePokemonDTO type;
	private int accuracy;
	private int pp;
	private String damageClass;

	public MovimientoJsonDTO() {
		// TODO Auto-generated constructor stub
	}

	public MovimientoJsonDTO(String name, int power, TypePokemonDTO type, int accuracy, int pp, String damageClass) {
		super();
		this.name = name;
		this.power = power;
		this.type = type;
		this.accuracy = accuracy;
		this.pp = pp;
		this.damageClass = damageClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public TypePokemonDTO getType() {
		return type;
	}

	public void setType(TypePokemonDTO type) {
		this.type = type;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		this.pp = pp;
	}

	public String getDamageClass() {
		return damageClass;
	}

	public void setDamageClass(String damageClass) {
		this.damageClass = damageClass;
	}

	@Override
	public String toString() {
		return "MovimientoJsonDTO [name=" + name + ", power=" + power + ", type=" + type + ", accuracy=" + accuracy
				+ ", pp=" + pp + ", damageClass=" + damageClass + "]";
	}

}
