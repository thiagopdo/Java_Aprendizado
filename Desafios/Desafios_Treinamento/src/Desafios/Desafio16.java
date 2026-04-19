package Desafios.Desafios_Treinamento.src.Desafios;

public class Desafio16 {
	private final String marca;
	private final String modelo;
	private final String cor;
	private final int ano;
	private final double valor;


	public Desafio16(String marca, String modelo, String cor, int ano, double valor) {
		this.marca = marca;
		this.modelo = modelo;
		this.cor = cor;
		this.ano = ano;
		this.valor = valor;
	}

	public static void main(String[] args) {
		Desafio16 carro = new Desafio16("Fiat", "Uno", "Branco", 2020, 100000);
		Desafio16 carro1 = new Desafio16("Toyota", "Corolla", "Branco", 2020, 100000);
		Desafio16 carro2 = new Desafio16("Ford", "Mustang", "Verde", 1967, 100000);
		carro.exibirInfo();
		carro1.exibirInfo();
		carro2.exibirInfo();
	}

	public void exibirInfo() {
		System.out.println("Marca: " + marca);
		System.out.println("Modelo: " + modelo);
		System.out.println("Cor: " + cor);
		System.out.println("Ano: " + ano);
		System.out.printf("Valor: R$ %.2f\n ", valor);


		System.out.println("ID do objeto na memoria: " + System.identityHashCode(this));

		System.out.println("--------------------------");
	}

}
