package enviando.email;

public class AppTest {

	@org.junit.Test
	public void testeEmail() throws Exception{
		
	 ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("natan.db@gmail.com", 
			 											"Natan Dadalt", 
			 											"Teste e-mail com Java", 
			 											"Texto de descrição do email");
	 
	 enviaEmail.enviarEmail();
	
		
	}

}
