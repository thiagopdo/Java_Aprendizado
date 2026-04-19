package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio12 {
	public static String formatarNomeAmericano(String nome, String sobrenome) {
		return sobrenome + ", " + nome;
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite o nome:");
		String nome = sc.nextLine();
		System.out.println("Digite o sobrenome:");
		String sobrenome = sc.nextLine();
		String nomeFormatado = formatarNomeAmericano(nome, sobrenome);
		System.out.println("Nome formatado: " + nomeFormatado);
		sc.close();
	}
}
