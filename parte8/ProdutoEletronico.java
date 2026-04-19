package parte8;


public class ProdutoEletronico {
	private String nome;
	private double preco;
	private int garantia;

	public ProdutoEletronico(String nome, double preco, int garantia) {
		this.nome = nome;
		this.preco = preco;
		this.garantia = garantia;
	}

	public void aplicarDesconto(double porcentagem) {
		if (porcentagem > 0 && porcentagem <= 100) {
			double desconto = calcularDesconto(porcentagem);
			this.preco -= desconto;
			System.out.println("Desconto de " + porcentagem + "% aplicado!");
		} else {
			System.out.println("Porcentagem inválida.");
		}
	}

	public double calcularDesconto(double porcentagem) {
		return (this.preco * porcentagem) / 100;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome != null && !nome.isEmpty()) {
			this.nome = nome;
		} else {
			System.out.println("Nome inválido.");
		}
	}

	public void exibirInfo() {
		System.out.println("O produto: " + nome + ", tem o valor de: R$" + preco + ", e tem garantia de: " + garantia + " meses.");
	}

}
