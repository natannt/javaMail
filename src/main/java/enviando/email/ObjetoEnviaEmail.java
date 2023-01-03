package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "natantestejava@gmail.com";
	private String senha = "hsjjnzfehepgdtkc";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String asssuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.asssuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;

	}

	public void enviarEmail(boolean envioHtml) throws Exception {
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

		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		Transport.send(message);
	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
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

		/* Parte 1 do e-mail, texto e descrição do e-mail */
		MimeBodyPart corporEmail = new MimeBodyPart();

		if (envioHtml) {
			corporEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corporEmail.setText(textoEmail);
		}

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());
		arquivos.add(simuladorDePDF());

		Multipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(corporEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {

			/* Parte 2 do e-mail, anexos em PDF */
			MimeBodyPart anexoEmail = new MimeBodyPart();

			/*
			 * Onde é passado o simuladorDePDF, pode-se passar arquivos gravados no banco de
			 * dados
			 */
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail" + index+ ".pdf");

			multiPart.addBodyPart(anexoEmail);
			
			index++;

		}
		
		message.setContent(multiPart);

		Transport.send(message);
	}

	/**
	 * 
	 * Esse método simula o PDF ou qualquer arquivo que possa ser enviado por anexo
	 * no email. Pode-se pegar o arquivo no banco de dados, seja ele, base64,
	 * byte[], Stream de arquivo. Pode estar em um banco de dados ou em uma pasta.
	 * 
	 * Retorna um PDF em branco com o texto do paragrafo de exemplo.
	 */
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo doo PDF anexo com Java Mail, esse texto é do pdf"));
		document.close();

		return new FileInputStream(file);
	}

}