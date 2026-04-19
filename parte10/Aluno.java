package parte10;

import java.util.Scanner;

public class Aluno {
	private String nome;
	private String matricula;
	private double[] notas;

	//constructor
	public Aluno(String nome, String matricula, int numeroDeNotas) {
		this.nome = nome;
		this.matricula = matricula;
		this.notas = new double[numeroDeNotas];
	}

	public String getNome() {
		return nome;
	}

	//adicioanr notas ao arrays
	public void adicionarNotas(Scanner scanner) {
		System.out.println("Digite as notas para o aluno: " + nome + ":");

		for(int i = 0; i < notas.length; i++) {
			System.out.println("Digite a nota " + (i + 1) + ": ");
			notas[i] = scanner.nextDouble();

		}
	}

	//calcular media
	public double calcularMedia() {
		double soma = 0;
		for(double nota : notas) {
			soma += nota;
		}
		return soma / notas.length;
	}

	//exivir resultado
	public void exibirResultado() {
		double media = calcularMedia();

		System.out.println("Nome: " + nome + ", Matricula: " + matricula + ", Media: " + media);

		if(media >= 6.0) {
			System.out.println("Aluno Aprovado");
		} else {
			System.out.println("Aluno Reprovado");
		}
	}

}
