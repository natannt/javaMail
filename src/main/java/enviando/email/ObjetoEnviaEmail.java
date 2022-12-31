package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ObjetoEnviaEmail {

	private String userName = "natantestejava@gmail.com";
	private String senha = "hsjjnzfehepgdtkc";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String asssuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail ) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.asssuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
		
	}

	public void enviarEmail() throws Exception	{
		Properties properties = new Properties();

		properties.put("mail.smtp.ssl.trust", "*"); /* autenticação SSL */
		properties.put("mail.smtp.auth", "true"); /* Autorização */
		properties.put("mail.smtp.starttls", "true");/* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com");/* Servidor Gmail Google */
		properties.put("mail.smtp.port", "465");/* Porta do servidor */
		properties.put("mail.smtp.socketFactory.port", "465");/* Expecifica a prota a ser conectada pelo Socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");/* Classe Socket de conexão ao SMTP */

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));/* Quem está enviando o e mail */
		message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
		message.setSubject(asssuntoEmail);/* Assunto do email */
		message.setText(textoEmail);/* Corpo do Email */

		Transport.send(message);
	}
}