package parte11;

public class Exercicio {
	public static void main(String[] args) {
		//1 OBJECT COMPOSITION

		Pessoa pessoa = new Pessoa("THiago", 35, new Endereco("Goiania", 428, "Sao paulo"));

		pessoa.exibirInfo();


	}
}
