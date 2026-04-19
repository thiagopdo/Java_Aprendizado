package parte8;

public class Livro {
	private String titulo;
	private String autor;
	private double preco;

//	public Livro() {
//		this.titulo = "Titulo teste";
//		this.autor = "Autor";
//		this.preco = 10.0;
//	}

	public Livro(String titulo, String autor, double preco) {
		this.titulo = titulo;
		this.autor = autor;
		this.preco = preco;
	}

	public void exibirInfo() {
		System.out.println("Titulo: " + titulo + ", Autor: " + autor + ", Preço: " + preco);
	}
}
