package parte5;



public class FuncoesBuiltin {

	public static void main(String[] args) {
		//Scanner scanner = new Scanner(System.in);
//		String frase = "Java é bom demaisi";
//
//		System.out.println(frase.length());
//
//		System.out.println(frase.substring(0, 4));
//
//		System.out.println(frase.toUpperCase());
//		System.out.println(frase.toLowerCase());
//		System.out.println(frase.replace("a", "e"));
//
//		int valor = 26;
//
//		System.out.println((Math.sqrt(valor)));
//		System.out.println(Math.pow(valor, 3));
//		System.out.println(Math.max(valor, 12));
//		System.out.println(Math.abs(-10));

		int[] numeros = {100, 5, 2, 89, 9};

		System.out.println(verificaArray(numeros));
	}

	public static int verificaArray(int[] numeros) {
		int maior = numeros[0];

		for (int i = 1; i < numeros.length; i++) {
			if (numeros[i] > maior) {
				maior = numeros[i];
			}
		}
		return maior;
	}


//	public static String notas(int nota) {
//		if (nota < 0 || nota > 10) return "nota invalida";
//
//		return switch (nota) {
//			case 10, 9 -> "A";
//			case 8, 7 -> "B";
//			case 6, 5 -> "C";
//			case 4, 3 -> "D";
//			default -> "F";
//		};
//	}


//	public static String parOuImpar(int numero) {
//		if (numero % 2 == 0) {
//			return "O numero é par!";
//		} else {
//			return "O numero é impar";
//		}

//	public static double converteCelsiusParaFahrenheit(double celsius) {
//		return (celsius * 9 / 5) + 32;
//	}

//	public static int calcularFatorial(int numero) {
//		if (numero == 0 || numero == 1) return 1;
//		return numero * calcularFatorial(numero - 1);
//	}
}
