package co.edu.unbosque.pokemon.service;

import java.util.List;

import co.edu.unbosque.pokemon.entity.Movimiento;

public class BatallaPokemon {

	private final long id;
	private final String nombre;
	private final List<String> tipo;
	private final List<Movimiento> movimientos;

	// estado mutable de la simulación
	private int hp;
	private int attack;
	private int speed;
	private String estado; 

	public BatallaPokemon(long id, String nombre, List<String> tipo, List<Movimiento> movimientos, int hp, int attack,
			int speed, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.movimientos = movimientos;
		this.hp = hp;
		this.attack = attack;
		this.speed = speed;
		this.estado = estado;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the tipo
	 */
	public List<String> getTipo() {
		return tipo;
	}

	/**
	 * @return the movimientos
	 */
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	// utilitarios pequeños
	public boolean isFainted() {
		return this.hp <= 0;
	}
}