package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;


public class Desafio9 {


	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite uma palavra para saber seu tamanho: ");
		String palavra1 = scanner.nextLine();
		System.out.println("Digite a palavra 2: ");
		String palavra2 = scanner.nextLine();

		System.out.println("O tamanho da palavra '" + palavra1 + "' é: " + palavra1.length());
		System.out.println("O tamanho da palavra '" + palavra2 + "' é: " + palavra2.length());

		if(palavra1.length() == palavra2.length()) {
			System.out.println("As palavras '" + palavra1 + "' e '" + palavra2 + "' têm o mesmo tamanho.");
		} else {
			System.out.println("As palavras '" + palavra1 + "' e '" + palavra2 + "' têm tamanhos diferentes.");
		}
		if(palavra1.equalsIgnoreCase(palavra2)) {
			System.out.println("As palavras '" + palavra1 + "' e '" + palavra2 + "' são iguais (ignorando maiúsculas e minúsculas).");
		} else {
			System.out.println("As palavras '" + palavra1 + "' e '" + palavra2 + "' são diferentes (ignorando maiúsculas e minúsculas).");
		}
		scanner.close();
	}
}

