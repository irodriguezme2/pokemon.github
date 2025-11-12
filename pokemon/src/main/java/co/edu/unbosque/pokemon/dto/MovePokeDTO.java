package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class MovePokeDTO {

	private String name;
	private String url;

	public MovePokeDTO() {
		// TODO Auto-generated constructor stub
	}

	public MovePokeDTO(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovePokeDTO other = (MovePokeDTO) obj;
		return Objects.equals(name, other.name) && Objects.equals(url, other.url);
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "MovePokeDTO [name=" + name + ", url=" + url + "]";
	}

}
