package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio10 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		double peso;
		double excesso = 0;
		double multa = 0;

		final double LIMITE = 50;
		final double VALOR_MULTA_POR_KILO = 8.0;

		try {

			System.out.print("Digite o peso do peixe em kg: ");
			peso = Double.parseDouble(scanner.nextLine());

			if(peso > LIMITE) {
				excesso = peso - LIMITE;
				multa = excesso * VALOR_MULTA_POR_KILO;
			}

			System.out.printf("\nPeso total de peixes: %.2f kg\n", peso);
			System.out.printf("Excesso de peso de peixes: %.2f kg\n", excesso);
			System.out.printf("Valor da multa: R$ %.2f\n", multa);

		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida. Por favor, insira um número válido para o peso.");
		}

		scanner.close();
	}
}
