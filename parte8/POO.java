package parte8;

public class POO {
	public static void main(String[] args) {
		// 1- criar classes
		// 2- instanciar classe

		Carros fusca = new Carros();

		// acessando props e metodos:
		fusca.marca = "Volkswagen";
		fusca.modelo = "Fusca";
		fusca.ano = 1970;
		fusca.aumentarVelocidade(10.0);
		System.out.println(fusca.motorLigado);
		fusca.ligarMotor();
		fusca.aumentarVelocidade(10.0);
		fusca.aumentarVelocidade(10.0);
		fusca.aumentarVelocidade(30.0);

		fusca.acelerar();
		fusca.exibirInfo();

		// Carros corolla = new Carros();
		// corolla.marca = "Toyota";
		// corolla.modelo = "Corolla";
		// corolla.ano = 2020;
		// corolla.acelerar();
		// corolla.exibirInfo();

		// Carros civic = new Carros();
		// civic.exibirInfo();

		// CRIANDO Propriedades
		Pessoa joao = new Pessoa();
		// PROTEGIGO joao.nome = "João";
		joao.setNome("Joao");
		System.out.println("Nome do joao é: " + joao.getNome());
		joao.setIdade(25);
		System.out.println("Idade do joao é: " + joao.getIdade());

		// SETTERS
		ContaBancaria contaDaAna = new ContaBancaria();
		contaDaAna.setTitular("Ana");

		contaDaAna.setSaldo(1000);
		// ontaDaAna.getInfoTitular();
		System.out.println("Titular da conta: " + contaDaAna.getTitular());

		System.out.println("Saldo da conta: " + contaDaAna.getSaldo());

		// GETTERS
		Produto camisa = new Produto();
		camisa.setNome("Camisa regata");
		System.out.println("Nome do produto: " + camisa.getNome());
		camisa.setPreco(29.9999999999);
		System.out.println("Preço do produto: " + camisa.getPreco());


		//METODO DENTRO DE METODO
		System.out.println(camisa.getProdutoInfo());
		camisa.aplicarDesconto(10);
		camisa.aplicarDesconto(24);

		//CONTRUCTORS

		//Livro meuLivro = new Livro("Harry Potter", "JK Rowlling", 19.0);

		//.exibirInfo();
	}
}
