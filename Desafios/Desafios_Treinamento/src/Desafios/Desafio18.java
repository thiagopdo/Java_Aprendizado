package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio18 {

	public static int[] contarMaiusMinusc(String frase) {
		int maiusculas = 0;
		int minusculas = 0;
		for(int i = 0; i < frase.length(); i++) {
			char caracter = frase.charAt(i);
			if(Character.isLetter(caracter)) {
				if(Character.isUpperCase(caracter)) {
					maiusculas++;
				} else {
					minusculas++;
				}
			}
		}

		return new int[]{maiusculas, minusculas};
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite uma frase:");
		String frase = scanner.nextLine();
		int[] contagem = contarMaiusMinusc(frase);

		System.out.println("Quantidade de letras maiúsculas: " + contagem[0]);
		System.out.println("Quantidade de letras minúsculas: " + contagem[1]);

		scanner.close();
	}
}
