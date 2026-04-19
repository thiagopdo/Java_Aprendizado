package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio7 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double[] medias = new double[4];
		int alunosAprovados = 0;

		for(int i = 0; i < 4; i++) {
			System.out.println("Digite as 4 notas do aluno " + (i + 1) + ":");
			double somaNotas = 0;
			for(int j = 0; j < 4; j++) {
				while(true) {
					System.out.println("Digite a " + (j + 1) + " nota do aluno " + (i + 1) + ":");
					if(scanner.hasNextDouble()) {
						double nota = scanner.nextDouble();

						if(nota >= 0 && nota <= 10) {
							somaNotas += nota;
							break;
						} else {
							System.out.println("Nota inválida. Digite um valor entre 0 e 10.");
							scanner.next();
						}
					}
				}

				double mediaAluno = somaNotas / 4.0;

				medias[i] = mediaAluno;
				if(mediaAluno >= 7.0) {
					alunosAprovados++;
				}

				System.out.println("Media do aluno " + (i + 1) + ": " + mediaAluno);
				System.out.println("------------------------------");
			}

			System.out.println("\n==============================");
			System.out.println("Quantidade de alunos com média maior ou igual a 7.0: " + alunosAprovados);
			System.out.println("==============================");
		}

//		Scanner scanner = new Scanner(System.in);
//
//		double[] media = new double[4];
//		int alunosAprovados = 0;
//		String[] alunos = new String[4];
//		alunos[0] = "João";
//		alunos[1] = "Maria";
//		alunos[2] = "Pedro";
//		alunos[3] = "Ana";
//
//		for(int i = 0; i < 4; i++) {
//			double somaNotas = 0;
//			System.out.println("Digite a " + (i + 1) + " nota do aluno " + alunos[i] + ":");
//
//			for(int j = 0; j < 4; j++) {
//				System.out.println("Digite a " + (j + 1) + " nota do aluno " + alunos[i] + ":");
//				somaNotas += scanner.nextDouble();
//			}
//			double mediaAluno = somaNotas / 4;
//			media[i] = mediaAluno;
//
//			if(mediaAluno >= 7) {
//				alunosAprovados++;
//			}
//		}
//
//		System.out.println("\n==============================");
//		System.out.println("Quantidade de alunos com média maior ou igual a 7.0: " + alunosAprovados);
//		System.out.println("==============================");
//
//		scanner.close();
	}
}
