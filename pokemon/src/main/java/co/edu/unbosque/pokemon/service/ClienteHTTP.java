package co.edu.unbosque.pokemon.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.GsonBuilder;

import co.edu.unbosque.pokemon.dto.EstadisticaJsonDTO;
import co.edu.unbosque.pokemon.dto.MovimientoJsonDTO;
import co.edu.unbosque.pokemon.dto.PokemonJsonDTO;


/**
 * Clase encargada de realizar solicitudes HTTP hacia APIs externas (como la
 * PokeAPI) y convertir las respuestas JSON en objetos DTO.
 * 
 * <p>
 * Utiliza {@link HttpClient} de Java 11+ con soporte HTTP/2.
 * </p>
 * 
 * @author PokéLab
 * @version 1.0
 */
public class ClienteHTTP {

//Quien hace la solicitud. Creacion del cliente
	private static final HttpClient CLIENTE = HttpClient.newBuilder().version(Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	/**
	 * Realiza una solicitud GET genérica a una URL y devuelve el código de estado
	 * junto al cuerpo.
	 *
	 * @param url dirección completa del recurso a consultar.
	 * @return una cadena con el código de estado HTTP y el cuerpo de la respuesta
	 *         separados por tabulación.
	 */
	public static String doGet(String url) {
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();
		HttpResponse<String> respuesta = null;

		try {
			respuesta = CLIENTE.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("Error al solicitar");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error de interrupción de la comunicación");
			e.printStackTrace();
		}

		return respuesta.statusCode() + "\t" + respuesta.body();
	}

	/**
	 * Realiza una solicitud GET y convierte el resultado JSON en un objeto
	 * {@link PokemonJsonDTO}.
	 *
	 * @param url dirección de la API del Pokémon (por ejemplo:
	 *            "https://pokeapi.co/api/v2/pokemon/1/").
	 * @return objeto {@link PokemonJsonDTO} con los datos del Pokémon.
	 */
	public static PokemonJsonDTO doGetPokemon(String url) {
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();
		HttpResponse<String> respuesta = null;

		try {
			respuesta = CLIENTE.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("Error al solicitar");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error de interrupción de la comunicación");
			e.printStackTrace();
		}
		System.out.println("Código de respuesta " + respuesta.statusCode());
		String json = respuesta.body();
		// Convierte el json en un objeto
		PokemonJsonDTO pokemonJsonDTO = new GsonBuilder().create().fromJson(json, PokemonJsonDTO.class);
		return pokemonJsonDTO;
	}

	/**
	 * Realiza una solicitud GET y convierte el resultado JSON en un objeto
	 * {@link MovimientoJsonDTO}.
	 *
	 * @param url dirección de la API del movimiento (por ejemplo:
	 *            "https://pokeapi.co/api/v2/move/52/").
	 * @return objeto {@link MovimientoJsonDTO} con los datos del movimiento.
	 */
	public static MovimientoJsonDTO doGetMovimientoJsonDTO(String url) {
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();
		HttpResponse<String> respuesta = null;

		try {
			respuesta = CLIENTE.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("Error al solicitar");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error de interrupción de la comunicación");
			e.printStackTrace();
		}
		System.out.println("Código de respuesta " + respuesta.statusCode());
		String json = respuesta.body();
		// Convierte el json en un objeto
		MovimientoJsonDTO movimientoJsonDTO = new GsonBuilder().create().fromJson(json, MovimientoJsonDTO.class);
		return movimientoJsonDTO;
	}

	/**
	 * Realiza una solicitud GET y convierte el resultado JSON en un objeto
	 * {@link EstadisticaJsonDTO}.
	 *
	 * @param url dirección de la API de la estadística (por ejemplo:
	 *            "https://pokeapi.co/api/v2/stat/1/").
	 * @return objeto {@link EstadisticaJsonDTO} con la información de la
	 *         estadística.
	 */
	public static EstadisticaJsonDTO doGetEstadisticaJsonDTO(String url) {
		HttpRequest solicitud = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();
		HttpResponse<String> respuesta = null;

		try {
			respuesta = CLIENTE.send(solicitud, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			System.out.println("Error al solicitar");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Error de interrupción de la comunicación");
			e.printStackTrace();
		}
		System.out.println("Código de respuesta " + respuesta.statusCode());
		String json = respuesta.body();
		// Convierte el json en un objeto
		EstadisticaJsonDTO estadisticaJsonDTO = new GsonBuilder().create().fromJson(json, EstadisticaJsonDTO.class);
		return estadisticaJsonDTO;
	}


}
