package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio19 {
	public static boolean validarEnderecoSite(String endereco) {
		return endereco.startsWith("www.") && endereco.endsWith(".com.br");
	}

	public static void solicitarEndSite() {
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.println("Digite o endereço do site: ");

			String endereco = scanner.nextLine();

			if(validarEnderecoSite(endereco)) {
				System.out.println("Endereço valido");
				break;
			} else {
				System.out.println("Endereço invalido");
			}
		}
		scanner.close();

	}

	public static void main(String[] args) {
		solicitarEndSite();
	}

}
