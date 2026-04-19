package parte11;

public class Cachorro extends Animal {
	//é obrigado a usar as propriedades da classe pai = superclasse

	public Cachorro(String nome) {
		super(nome);
	}

	public void latir() {
		System.out.println(nome + " fez Au au");
	}
}
