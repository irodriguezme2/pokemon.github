package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class MoveDTO {

	private MovePokeDTO move;

	public MoveDTO() {
		// TODO Auto-generated constructor stub
	}

	public MoveDTO(MovePokeDTO move) {
		super();
		this.move = move;
	}

	@Override
	public int hashCode() {
		return Objects.hash(move);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoveDTO other = (MoveDTO) obj;
		return Objects.equals(move, other.move);
	}

	/**
	 * @return the move
	 */
	public MovePokeDTO getMove() {
		return move;
	}

	/**
	 * @param move the move to set
	 */
	public void setMove(MovePokeDTO move) {
		this.move = move;
	}

	@Override
	public String toString() {
		return "MoveDTO [move=" + move + "]";
	}

}
