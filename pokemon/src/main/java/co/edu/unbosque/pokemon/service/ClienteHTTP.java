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

public class ClienteHTTP {

//Quien hace la solicitud. Creacion del cliente
	private static final HttpClient CLIENTE = HttpClient.newBuilder().version(Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10)).build();

	// Consumir la api. Solicitud
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

	// Consumir la api. Solicitud
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

	// Consumir la api. Solicitud
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

	public static void main(String[] args) {
		/*
		 * System.out.println(doGet(
		 * "https://ipgeolocation.abstractapi.com/v1/?api_key=11b6b958b53043d5b88a20ba752283f4&ip_address=190.131.251.193"
		 * ));
		 * System.out.println(doGet("https://countriesnow.space/api/v0.1/countries"));
		 */
		System.out.println(doGetMovimientoJsonDTO("https://pokeapi.co/api/v2/move/52/").toString());
	}

}
