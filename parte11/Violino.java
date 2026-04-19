package parte11;

public class Violino extends InstrumentoMusical {
	public Violino(String nome) {
		super(nome);
	}

	@Override
	public void tocar() {
		System.out.println("Agora tocando violino: " + nome);

	}

}
