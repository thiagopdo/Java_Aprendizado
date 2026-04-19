package parte7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ArrayAvançado {

	public static void main(String[] args) {
		int[] number = { 5, 2, 1, 4, 0, -1, 7, 8 };

		Arrays.sort(number);
		System.out.println(Arrays.toString(number));

		// ordenaçao com Comparator
		String[] name = { "João", "Bob", "Pedro", "Marco" };
		Arrays.sort(name, Comparator.reverseOrder());
		System.out.println(Arrays.toString(name));

		// ordenaçao de matriz
		int[][] matrix = { { 4, 5, 3 }, { 1, 6, 9 }, { 2, 7, 9 } };
		// ordenaçao pela coluna[2]
		Arrays.sort(matrix, Comparator.comparingInt(a -> a[2]));
		for (int[] linha : matrix) {
			System.out.println(Arrays.toString(linha));
		}
		// System.out.println(Arrays.deepToString(matrix));

		// MANIPULAÇÃO DE MATRIZ AVNAÇADA
		// COPIA
		int[] original = { 1, 2, 2, 3, 4, 5, 5, 6 };
		int[] copia = Arrays.copyOf(original, 4);
		System.out.println(Arrays.toString(copia));

		// FILL - preenche o array com um valor específico
		int[] fillArrays = new int[5];
		System.out.println(Arrays.toString(fillArrays));
		Arrays.fill(fillArrays, 7);
		System.out.println(Arrays.toString(fillArrays));

		// transformar array em stream
		int[] streamArray = { 1, 2, 3, 4, 5 };
		int soma = Arrays.stream(streamArray).sum();
		System.out.println("Soma: " + soma);

		// ARRAYS DINAMICOS
		ArrayList<String> frutas = new ArrayList<>();
		System.out.println(frutas);
		frutas.add("Maçã");
		frutas.add("Banana");
		frutas.add("Laranja");

		System.out.println(frutas);

		for (String fruta : frutas) {
			System.out.println(fruta);
		}

		// frutas.remove(0);
		// frutas.remove("Maça")
		System.out.println(frutas);
		frutas.add("Abacaxi");
		String frutaEspecifica = frutas.get(1);
		System.out.println("Fruta específica: " + frutaEspecifica);

	}
}