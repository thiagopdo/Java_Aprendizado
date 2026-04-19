package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio6 {
	public static void main(String[] args) {
		int[] vetor = new int[20];
		int[] par = {};
		int[] impar = {};

		Scanner scanner = new Scanner(System.in);

		for(int i = 0; i < 20; i++) {
			while(true) {
				System.out.println("Digite o numero " + (i + 1) + " dos 20, para o vetor: ");

				if(scanner.hasNextInt()) {
					vetor[i] = scanner.nextInt();
					if(vetor[i] % 2 == 0) {
						par = java.util.Arrays.copyOf(par, par.length + 1);
						par[par.length - 1] = vetor[i];
						System.out.println("Numero par adicionado: " + vetor[i]);
						System.out.println("Vetor par atualizado: " + java.util.Arrays.toString(par));
					} else {
						impar = java.util.Arrays.copyOf(impar, impar.length + 1);
						impar[impar.length - 1] = vetor[i];
						System.out.println("Vetor impar atualizado: " + java.util.Arrays.toString(impar));
					}
					break;  // Adicionado: sai do loop while após processar entrada válida
				} else {
					System.out.println("Entrada inválida. Digite um numero inteiro.");
					scanner.next();  // Adicionado: consome a entrada inválida para evitar loop infinito
				}
			}
		}
		// Opcional: adicionar prints finais para mostrar os vetores completos, se necessário
		System.out.println("Vetor completo: " + java.util.Arrays.toString(vetor));
		System.out.println("Numeros pares: " + java.util.Arrays.toString(par));
		System.out.println("Numeros impares: " + java.util.Arrays.toString(impar));

		scanner.close();
	}
}
