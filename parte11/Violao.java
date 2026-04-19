package parte11;

public class Violao extends InstrumentoMusical {
	public Violao(String nome) {
		super(nome);
	}

	@Override
	public void tocar() {
		System.out.println("Tocando os acordes no: " + nome);
	}
}
