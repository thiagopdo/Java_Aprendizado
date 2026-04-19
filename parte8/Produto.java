package parte8;

public class Produto {
	private String nome;
	private double preco;

	public String getNome() {
		return nome.toUpperCase();
	}

	public void setNome(String nome) {
		if (nome != null && !nome.isEmpty() && nome.length() > 3) {
			this.nome = nome;
		} else {
			System.out.println("Nome inválido. Deve conter mais de 3 caracteres.");
		}
	}

	public String getPreco() {
		return String.format("R$%.2f", this.preco);
	}

	public void setPreco(double preco) {
		if (preco > 0) {
			this.preco = preco;
		} else {
			System.out.println("Preço inválido. Deve ser maior que zero.");
		}
	}

	public String getProdutoInfo() {
		return "Nome: " + this.getNome() + ", Preço: " + this.getPreco();
	}

	public void aplicarDesconto(double porcentagem) {
		if (porcentagem > 0 && porcentagem <= 100) {
			double desconto = calcularDesconto(porcentagem);

			this.preco -= desconto;

			System.out.println("Desconte de " + porcentagem + "% aplicado!");
			System.out.println(this.getProdutoInfo());

		} else {
			System.out.println("Porcetagem inválida");
		}
	}

	private double calcularDesconto(double porcentagem) {
		return (this.preco * porcentagem) / 100;
	}
}
