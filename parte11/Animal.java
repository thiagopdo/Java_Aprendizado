package parte11;

public class Animal {

	protected String nome;

	public Animal(String nome) {
		this.nome = nome;
	}

	public void emitirSom() {
		System.out.println("Som de animal: " + nome);
	}
}
