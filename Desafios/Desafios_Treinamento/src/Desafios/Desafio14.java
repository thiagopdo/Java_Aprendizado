package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Arrays;
import java.util.Scanner;

public class Desafio14 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite uma série de nomes separadas por espaço: ");
		String linha = sc.nextLine();
		String[] nomes = linha.split(" ");

		Arrays.sort(nomes);
		System.out.println("Os nomes em ordem alfabética são: ");
		for(String nome : nomes) {
			System.out.println(nome + " ");
		}
		System.out.println();
		sc.close();
	}
}
