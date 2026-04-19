package Desafios.Desafios_Treinamento.src.Desafios;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Desafio21 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Por favor, insira o intervalo de dadtas");

		LocalDate dataInicio = solicitarData(scanner, "Digite data de inicio(AAAA-MM-DD): ");
		LocalDate dataFim = solicitarData(scanner, "Digite data de fim(AAAA-MM-DD): ");


		if(dataInicio.isAfter(dataFim)) {
			System.out.println("Erro: a data inicio deve ser menor que a data fim");
		} else {
			int totalSegundas = contarSegundasFeirasPorMes(dataInicio, dataFim);

			System.out.println("O numero de segunda-feiras que caem no 1 de cada mes entre " + dataInicio + " e " + dataFim + " é: " + totalSegundas);
		}

		scanner.close();
	}

	public static LocalDate solicitarData(Scanner scanner, String mensagem) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		while(true) {
			System.out.println(mensagem);
			String input = scanner.nextLine();

			try {
				return LocalDate.parse(input, formatter);

			} catch (DateTimeException e) {
				System.out.println("Data inválida. Por favor, tente novamente.");
			}
		}
	}

	/**
	 * Conta o numero de segundas feiras que caem no 1 de cada mes entre dataInicio e dataFim
	 *
	 * @param dataInicio
	 * @param dataFim
	 * @return int - numero de segundas feiras que caem no 1 de cada mes
	 * @throws IllegalArgumentException se dataInicio for depois de dataFim
	 */

	public static int contarSegundasFeirasPorMes(LocalDate dataInicio, LocalDate dataFim) {
		int totalSegundas = 0;

		LocalDate atual = LocalDate.of(dataInicio.getYear(), dataInicio.getMonth(), 1);

		while(!atual.isAfter(dataFim)) {
			if(atual.getDayOfWeek().getValue() == 1) {
				totalSegundas++;
			}
			atual = atual.plusMonths(1);
		}
		return totalSegundas;
	}
}
