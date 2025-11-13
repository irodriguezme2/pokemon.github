package co.edu.unbosque.pokemon.service;

import java.util.List;

public interface CRUDOperation<D> {

	/**
	 * Crea un nuevo registro.
	 * 
	 * @param newData datos del objeto a crear
	 * @return código de resultado de la operación
	 */
	public int create(D newData);

	/**
	 * Obtiene todos los registros existentes.
	 * 
	 * @return lista con todos los objetos almacenados
	 */
	public List<D> getAll();

	/**
	 * Elimina un registro según su ID.
	 * 
	 * @param id identificador único del objeto
	 * @return código de resultado de la operación
	 */
	public int deleteByID(Long id);


	/**
	 * Actualiza un registro según su ID.
	 * 
	 * @param id      identificador único del objeto
	 * @param newData nuevos datos para reemplazar el registro actual
	 * @return código de resultado de la operación
	 */
	public int updateByID(Long id, D newData);

}
