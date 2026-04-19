package parte14;

//T NESTE CONTEXTO É QUALQUER COISA
public class Caixa<T> {
	private T conteudo;

	public void adicionar(T conteudo) {
		this.conteudo = conteudo;
	}

	public T getConteudo() {
		return conteudo;
	}
}
