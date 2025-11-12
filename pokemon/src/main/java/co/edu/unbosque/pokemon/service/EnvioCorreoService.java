package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnvioCorreoService {
	@Autowired
	private JavaMailSender mailSender;

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