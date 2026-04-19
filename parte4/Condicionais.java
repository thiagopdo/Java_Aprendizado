package parte4;

import java.util.Scanner;

public class Condicionais {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// System.out.println("Digite o peso em kg: ");
		// double peso = scanner.nextDouble();
		// System.out.println("Digite a altura em metros: ");
		// double altura = scanner.nextDouble();
		//
		// double imc = peso / (altura * altura);
		// System.out.println("Seu imc é: " + imc);
		//
		// if (imc < 18.5) {
		// System.out.println("Abaixo do peso");
		// } else if (imc >= 18.5 && imc <= 24.9) {
		// System.out.println("Peso normal");
		// } else {
		// System.out.println("Acima do peso");
		// }

		String prod = "Arroz";
		String prod2 = "Feijão";
		String prod3 = "Macarrão";
		double preco = 0;

		System.out.println("Digite o nome do produto: ");
		String produto = scanner.nextLine();

		if (produto.equalsIgnoreCase(prod) || produto.equalsIgnoreCase(prod2) || produto.equalsIgnoreCase(prod3)) {
			System.out.println("Produto já existe. Quer atualizar o preço? (S/N)");

			String resposta = scanner.nextLine();

			if (resposta.equalsIgnoreCase("Sim")) {
				System.out.println("Insira o novo preco: ");
				preco = scanner.nextDouble();
			} else {
				System.out.println("Produto não atualizado.");
				scanner.close();
				return;
			}

		} else {
			System.out.println("Insira o novo preco: ");
			preco = scanner.nextDouble();
		}

		if (preco < 50) {
			System.out.println("Produto barato");
		} else if (preco >= 50 && preco <= 100) {
			System.out.println("Produto moderado");
		} else {
			System.out.println("Produto caro");
		}

		System.out.println("Produto: " + produto + " \nPreço: R$" + preco);

		scanner.close();
	}
}
