package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio51 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		double[] vetor = new double[10];

		for(int i = 0; i < vetor.length; i++) {
			System.out.println("Digite o número " + (i + 1) + " real:");
			vetor[i] = scanner.nextDouble();
		}

		System.out.println("\nNumeros na ordem inversa: ");

		for(int i = vetor.length - 1; i >= 0; i--) {
			System.out.println(vetor[i]);
		}

		double soma = 0;
		for(int i = 0; i < vetor.length; i++) {
			soma += vetor[i];
		}

		double media = soma / vetor.length;
		System.out.println("\nMedia: " + media);

		int contMaiorMedia = 0;
		for(int i = 0; i < vetor.length; i++) {
			if(vetor[i] > media) {
				contMaiorMedia++;
			}
		}

		System.out.println("\nQuantidade de números maiores que a média: " + contMaiorMedia);

		System.out.println("\nEscolha um numero do vetor para saber sua posiçaão original: ");

		double numeroEscolhido = scanner.nextDouble();
		boolean encontrado = false;
		for(int i = 0; i < vetor.length; i++) {
			if(vetor[i] == numeroEscolhido) {
				System.out.println("O número " + numeroEscolhido + " está na posição original: " + i);
				encontrado = true;
				break;
			}
		}

		if(!encontrado) {
			System.out.println("O número " + numeroEscolhido + " não foi encontrado no vetor.");
		}

		scanner.close();
	}
}
