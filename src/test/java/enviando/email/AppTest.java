package enviando.email;

public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception {

		StringBuilder stringBuilderTextoEmail = new StringBuilder();

		stringBuilderTextoEmail.append("Olá! <br/><br/>");
		stringBuilderTextoEmail.append("Você está recebendo o acesso ao curso de Java.<br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso clique no botão abaixo.<br/><br/>");
		
		stringBuilderTextoEmail.append("<b>Login:</b>Natan.Dd<br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b>Meucurso<br/><br/>");

		stringBuilderTextoEmail.append(
				"<a target=\"_blank\" href=\"https://www.projetojavaweb.com/certificado-aluno/login\" style=\"color:#2525a7; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block; border-radius: 30px; font-size: 20px; font-family: courrier; border: 3px solid green; background-color: #99DA39;\">Acessar Portal do Aluno</a><br/><br/>");

		stringBuilderTextoEmail.append("<span style=\"font-size: 8px\">Ass. Natan Dadalt</span>");

		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("natan.db@gmail.com", "Natan Dadalt",
				"Teste e-mail com Java", stringBuilderTextoEmail.toString());

		enviaEmail.enviarEmailAnexo(true);

	}

}
