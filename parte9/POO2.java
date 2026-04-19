package parte9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class POO2 {

	//NIVEIS DE ACESSO

	public static void main(String[] args) {
		Funcionario funcionario1 = new Funcionario("Thiago", 1000.0, "1234");

		funcionario1.exibirInfo();
		funcionario1.nome = "teste";
		funcionario1.salario = 3000;
		//funcionario1.senha = "12313123";

		funcionario1.aumentarSalario(10);
		funcionario1.exibirInfo();
		if(funcionario1.autenticar("1234")) {
			System.out.println("Autenticado");
		}

		//CLASSE IMUTAVEL
		PessoaImutavel joaquim = new PessoaImutavel("Joaquim", 21);
		System.out.println(joaquim.getNome());
		System.out.println(joaquim.getIdade());

		//ENCAPSULAMENTO DE ARRAYS
		String[] meusAlunos = {"MAtheus", "Joao", "MAria"};
		Turma novaTurma = new Turma(meusAlunos);

		System.out.println(Arrays.toString(novaTurma.getAlunos()));
		novaTurma.setAlunos(new String[]{"Pedro", "Maria"});

		System.out.println(Arrays.toString(novaTurma.getAlunos()));

		//EXERCICIO LOTERIA
		Scanner scanner = new Scanner(System.in);
		ArrayList<Bilhete> bilhetes = new ArrayList<>();

		//solicitante bilhete usuário
		while(true) {
			System.out.println("Digite seus 6 numeros(1 a 60): ");

			int[] numerosEscolhidos = new int[6];

			//ler os numeros
			for(int i = 0; i < numerosEscolhidos.length; i++) {
				System.out.println("Digite o numero " + (i + 1) + ": ");
				numerosEscolhidos[i] = scanner.nextInt();
			}

			//criar um novo bilhete
			Bilhete novoBilhete = new Bilhete(numerosEscolhidos);
			bilhetes.add(novoBilhete);

			//perguntar se deseja registrar outro bilhete
			System.out.println("Deseja registrar outro bilhete? (s/n)");
			String resposta = scanner.next();


			if(resposta.equalsIgnoreCase("n")) {
				break;
			}
		}

		//realizar sorteiro para cada bilhete
		System.out.println("Realizando sorteio!");

		for(Bilhete bilhete : bilhetes) {
			bilhete.realizarSorteio();
			bilhete.exibirResultado();
		}

		scanner.close();

	}
}
