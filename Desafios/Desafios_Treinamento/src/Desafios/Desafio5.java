package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio5 {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		double[] vetor = new double[10];

		for(int i = 0; i < 10; i++) {
			while(true) {
				System.out.println("Digite o " + (i + 1) + " numero real: ");

				if(sc.hasNextDouble()) {
					vetor[i] = sc.nextDouble();
					break;
				} else {
					System.out.println("Entrada inválida. Insira um numero real.");
					sc.next();
				}
			}

		}
		System.out.println("\nOs numeros na ordem inversa são: ");
		for(int i = 9; i >= 0; i--) {
			System.out.println(vetor[i]);
		}

		sc.close();
	}
}
