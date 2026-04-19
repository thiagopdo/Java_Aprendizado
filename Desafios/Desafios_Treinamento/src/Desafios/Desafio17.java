package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.*;

public class Desafio17 {


	public static double compararDoisNumeros(double a, double b) {
		return (a > b) ? a : b;
	}

	public static double maiorValor(List<Double> numeros) {
		double maior = numeros.get(0);
		for(int i = 1; i < numeros.size(); i++) {
			maior = compararDoisNumeros(maior, numeros.get(i));
		}

		return maior;
	}

	public static boolean temDupplicatas(List<Double> numeros) {
		Set<Double> conjunto = new HashSet<>(numeros);
		return conjunto.size() != numeros.size();
	}

	public static List<Double> solicitarNumeros(Scanner scanner) {
		while(true) {
			try {

				System.out.println("Digite uma lista de números separados por espaço:");
				String entrada = scanner.nextLine();

				String[] partes = entrada.split("\\s+");

				List<Double> numeros = new ArrayList<>();

				for(String parte : partes) {
					numeros.add(Double.parseDouble(parte));
				}

				if(numeros.isEmpty()) {
					System.out.println("Por favor, insira pelo menos um número.");
				}

				if(temDupplicatas(numeros)) {
					System.out.println("Os numeros digitados possuem duplicatas. Por favor, insira uma lista sem duplicatas.");
				}

				return numeros;

			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, insira apenas números.");
				scanner.nextLine(); // Limpa o buffer do scanner
			}
		}
	}

	public static void exibirDes(List<Double> numeros) {
		List<Double> copia = new ArrayList<>(numeros);
		copia.sort(Collections.reverseOrder());
		System.out.println("Números em ordem decrescente: " + copia);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			List<Double> numeros = solicitarNumeros(scanner);
			double maior = maiorValor(numeros);
			System.out.println("O maior valor é: " + maior);

			exibirDes(numeros);
			System.out.println("Deseja inserir outra lista de números? (s/n)");
			String resposta = scanner.nextLine().trim().toLowerCase();

			if(!resposta.equals("s")) {
				System.out.println("Encerrando o programa. Obrigado por participar!");
				break;
			}
		}

		scanner.close();
	}
}
