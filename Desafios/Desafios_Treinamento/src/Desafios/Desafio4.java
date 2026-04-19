package Desafios;

import java.util.Scanner;

public class Desafio4 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int intervalo_0_25 = 0;
		int intervalo_26_50 = 0;
		int intervalo_51_75 = 0;
		int intervalo_76_100 = 0;

		while(true) {
			System.out.println("Digite um número entre 0 e 100 (ou um número negativo para sair):");

			if(scanner.hasNextInt()) {
				int numero = scanner.nextInt();
				if(numero < 0) {
					break;
				}

				if(numero >= 0 && numero <= 25) {
					intervalo_0_25++;
				} else if(numero > 25 && numero <= 50) {
					intervalo_26_50++;
				} else if(numero > 50 && numero <= 75) {
					intervalo_51_75++;
				} else if(numero > 75 && numero <= 100) {
					intervalo_76_100++;
				} else {
					System.out.println("Número " + numero + " está fora do intervalo. Tente novamente.");
				}
			} else {
				System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
				scanner.next(); // Limpa a entrada inválida

			}

		}

		System.out.println("\nContagem final de numeros de cada intervalo: ");
		System.out.println("Intervalo [0-25]: " + intervalo_0_25);
		System.out.println("Intervalo [26-50]: " + intervalo_26_50);
		System.out.println("Intervalo [51-75]: " + intervalo_51_75);
		System.out.println("Intervalo [76-100]: " + intervalo_76_100);
		scanner.close();
	}

}
