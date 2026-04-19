package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class Desafio61 {

	private static int letInteiroPositivo(Scanner scanner, String mensagem) {
		System.out.println(mensagem);

		while(true) {
			String linha = scanner.nextLine().trim();

			try {
				int valor = Integer.parseInt(linha);

				if(valor >= 0) {
					return valor;
				}

				System.out.print("Por favor, informe um inteiro não negativo: ");

			} catch (NumberFormatException e) {
				System.out.println("Valor inválido. Por favor, digite um número inteiro positivo.");
			}
		}
	}

	private static double lerDouble(Scanner scanner, String mensagem) {
		System.out.println(mensagem);

		while(true) {
			String linha = scanner.nextLine().trim();

			try {
				return Double.parseDouble(linha);

			} catch (NumberFormatException e) {
				System.out.print("Valor inválido. Por favor, numero de ponto flutuante. (use ponto).");
			}
		}
	}

	public static List<Object> criarlista(String tipoDado, Scanner scanner) {
		List<Object> lista = new ArrayList<>();

		int n = letInteiroPositivo(scanner, "Digite o tamanho da lista: ");

		for(int i = 0; i < n; i++) {
			switch(tipoDado) {
				case "1" -> {
					int elemento = letInteiroPositivo(scanner, "Digite o elemento [" + (i + 1) + ": " + n +
									"]: ");
					lista.add(elemento);
				}
				case "2" -> {
					double elemento = lerDouble(scanner,
									"Digite o elemento de ponto flutuante [" + (i + 1) + ": " + n +
													"]: ");
					lista.add(elemento);
				}
				case "3" -> {
					System.out.print("Digite uma string [" + (i + 1) + "]: ");
					String elemento = scanner.nextLine().trim();
					lista.add(elemento);
				}
				default ->
								System.out.println("Tipo de dado inválido. Por favor, digite 1(int), 2(double) ou 3(string).");
			}
		}

		return lista;
	}

	private static List<Object> removerDuplicatasPreservandoOrdem(List<Object> lista) {

		LinkedHashSet<Object> conjuntoSemDuplicatas = new LinkedHashSet<>(lista);

		return new ArrayList<>(conjuntoSemDuplicatas);
	}

	public static List<Object> unirListas(List<Object> lista1, List<Object> lista2, boolean removerDuplicatasInternas) {
		if(removerDuplicatasInternas) {
			lista1 = removerDuplicatasPreservandoOrdem(lista1);
			lista2 = removerDuplicatasPreservandoOrdem(lista2);
		}

		List<Object> listaUnidad = new ArrayList<>(lista1);

		for(Object item : lista2) {
			if(!listaUnidad.contains(item)) {
				listaUnidad.add(item);
			}
		}

		try {
			listaUnidad.sort((a, b) -> {
				if(a instanceof Comparable && b instanceof Comparable) {
					@SuppressWarnings({"rawtypes", "unchecked"})
					int resultado = ((Comparable) a).compareTo(b);

					return resultado;
				}

				return 0;
			});
		} catch (Exception e) {
			System.out.println("A lista contem elementos não ordenáveis. Exibindo sem ordenação");
		}

		return listaUnidad;
	}

	private static boolean perguntarSimNao(Scanner scanner, String pergunta) {
		System.out.print(pergunta + "(s/n): ");

		while(true) {
			String resposta = scanner.nextLine().trim().toLowerCase();

			if(resposta.equals("s")) return true;
			if(resposta.equals("n")) return false;

			System.out.print("Resposta inválida. Por favor, digite 's' para sim ou 'n' para não.");
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("=== Desafio 61: Unir duas listas com opçoes de personalização ===");
		System.out.println("Escolha o tipo de dado para as listas: 1 - Inteiros, 2 - Ponto Flutuante, 3 - Strings");
		String tipoDado = scanner.nextLine().trim();

		while(!tipoDado.equals("1") && !tipoDado.equals("2") && !tipoDado.equals("3")) {
			System.out.println("Tipo de dado inválido. Por favor, digite 1(int), 2(double) ou 3(string).");
			tipoDado = scanner.nextLine().trim();
		}
		System.out.println("--- Criando a primeira lista ---");
		List<Object> lista1 = criarlista(tipoDado, scanner);

		System.out.println("--- Criando a segunda lista ---");
		List<Object> lista2 = criarlista(tipoDado, scanner);

		boolean removerDuplicatasInternas = perguntarSimNao(scanner, "Remover duplicatas internas?");

		System.out.println("--- Unindo as listas ---");
		List<Object> listaUnida = unirListas(lista1, lista2, removerDuplicatasInternas);

		System.out.println("Lista unida: " + listaUnida);

		scanner.close();
	}
}
