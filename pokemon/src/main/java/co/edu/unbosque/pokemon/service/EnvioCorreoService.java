package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del envío de correos electrónicos de verificación para
 * nuevos usuarios registrados en el sistema PokéLab.
 * 
 * @author PokéLab
 * @version 1.0
 */
@Service
public class EnvioCorreoService {
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Envía un correo electrónico de verificación al usuario con un enlace único
	 * para activar su cuenta.
	 * 
	 * @param destinatario correo electrónico del usuario
	 * @param token        código de verificación asociado al usuario
	 */
	public void enviarCorreoVerificacion(String destinatario, int token) {
		String asunto = "Verificación de cuenta";
		String linkVerificacion = "http://localhost:8081/usuario/verificar?token=" + token;

		String cuerpo = "Hola,\n\n"
				+ "Gracias por registrarte. Para activar tu cuenta, haz clic en el siguiente enlace:\n"
				+ linkVerificacion + "\n\n" + "Si no te registraste en nuestro sistema, ignora este correo.";

		SimpleMailMessage mensaje = new SimpleMailMessage();
		mensaje.setFrom("pokefightf@gmail.com");
		mensaje.setTo(destinatario);
		mensaje.setSubject(asunto);
		mensaje.setText(cuerpo);

		mailSender.send(mensaje);
	}
}