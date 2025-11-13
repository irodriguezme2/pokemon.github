package co.edu.unbosque.pokemon;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * Clase inicializadora del servlet para el despliegue de la aplicación Pokémon.
 * 
 * <p>
 * Esta clase extiende {@link SpringBootServletInitializer} y permite configurar
 * y ejecutar la aplicación Spring Boot en un contenedor de servlets externo,
 * como Apache Tomcat, WildFly o GlassFish.
 * </p>
 * 
 * <p>
 * Su función principal es registrar la clase principal
 * {@link PokemonApplication} como punto de entrada cuando la aplicación se
 * despliega como un archivo <b>.war</b> en lugar de ejecutarse como una
 * aplicación autónoma <b>.jar</b>.
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación al momento de inicializar el servlet.
	 * 
	 * <p>
	 * Registra la clase {@link PokemonApplication} como la fuente principal de
	 * configuración para la aplicación Spring Boot.
	 * </p>
	 *
	 * @param application el constructor de la aplicación
	 * @return una instancia de {@link SpringApplicationBuilder} configurada con la
	 *         clase principal
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PokemonApplication.class);
	}

}
