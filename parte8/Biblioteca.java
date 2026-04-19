package parte8;

public class Biblioteca {
	private String titulo;
	private String autor;
	private boolean disponivel = true;

	public Biblioteca(String titulo, String autor, boolean disponivel) {
		this.titulo = titulo;
		this.autor = autor;
		this.disponivel = disponivel;
	}

	public boolean estaDisponivel() {
		return disponivel;
	}

	public void pegarEmprestado() {
		if(disponivel) {
			disponivel = false;
			System.out.println("Você pegou o livro: " + titulo + " do autor: " + autor);
		} else {
			System.out.println("O livro: " + titulo + ", não está disponivel");
		}
	}

	public void devolver() {
		if(!disponivel) {
			disponivel = true;
			System.out.println("Você devolveu o livro: " + titulo + " do autor: " + autor);
		} else {
			System.out.println("O livro: " + titulo + ", já está disponivel");
		}
	}


	public void exibirInfo() {
		System.out.println("Livro: " + titulo + ", Autor: " + autor + ", Disponivel: " + disponivel);
	}
}
