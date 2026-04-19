package parte10;

import java.util.Scanner;


public class SistemaCadastroAluno {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Quantos alunos deseja cadastrar?");
		int numeroDeAlunos = scanner.nextInt();

		//criar um array de alunos
		Aluno[] alunos = new Aluno[numeroDeAlunos];

		//loop para cadastrar alunos
		for(int i = 0; i < alunos.length; i++) {
			System.out.println("Digite o nome do aluno " + (i + 1) + ": ");

			System.out.println("Nome: ");
			String nome = scanner.next();


			System.out.println("Matricula: ");
			String matricula = scanner.next();


			System.out.println("Quantidade de provas: ");
			int numeroDeNotas = scanner.nextInt();


			Aluno novoAluno = new Aluno(nome, matricula, numeroDeNotas);

			//Adicionar as notas
			novoAluno.adicionarNotas(scanner);


			//armazenar aluno no array
			alunos[i] = novoAluno;

		}

		//exibir os resultados
		System.out.println("Resultados: ");
		for(Aluno aluno : alunos) {
			aluno.exibirResultado();
			System.out.println();
		}

		scanner.close();

	}


}
