package parte11;


interface Imprimivel {
	void imprimir();
}

interface Salvavel {
	void salvar();

	default void instrucaoSalvar() {
		System.out.println("Voce deve salvar antes de sair");
	}
}

public class Documento implements Imprimivel, Salvavel {

	private String documento;

	public Documento(String documento) {
		this.documento = documento;
	}

	@Override
	public void imprimir() {
		System.out.println("Imprimindo documento: " + documento);
	}

	@Override
	public void salvar() {
		System.out.println("Salvando documento: " + documento);
	}
}
