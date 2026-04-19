package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Random;
import java.util.Scanner;

public class Desafio13 {
	public static String gerarSenha(int tamanho) {
		String letrasMaiuscula = "ABCDEFGHIJKLMNOPQRSTUVXZ";
		String letrasMinuscula = letrasMaiuscula.toLowerCase();
		String numeros = "0123456789";
		String caracteresEspeciais = "!@#$%&*()_-+=?";
		String todosCaracteres = letrasMaiuscula + letrasMinuscula + numeros + caracteresEspeciais;

		Random random = new Random();

		StringBuilder senha = new StringBuilder();

		for(int i = 0; i < tamanho; i++) {
			int indice = random.nextInt(todosCaracteres.length());
			senha.append(todosCaracteres.charAt(indice));
		}
		return senha.toString();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		try {
			System.out.println("Digite o tamanho da senha desejada (entre 8 e 16 caracteres): ");
			int tamanho = scanner.nextInt();

			if(tamanho < 8 || tamanho > 16) {
				System.out.println("O tamanho da senha deve estar entre 8 e 16 caracteres.");
			} else {
				String senhaGerada = gerarSenha(tamanho);

				System.out.println("Senha gerada: " + senhaGerada);
			}

		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida. Por favor, insira um número inteiro para o tamanho da senha.");
		}

		scanner.close();
	}
}
