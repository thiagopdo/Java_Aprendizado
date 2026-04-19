package parte9;

public class PessoaImutavel {
	private final String nome;
	private final int idade;

	public PessoaImutavel(String nome, int idade) {
		this.nome = nome;
		this.idade = idade;
	}

	public String getNome() {
		return this.nome;
	}

	public int getIdade() {
		return this.idade;
	}
}
