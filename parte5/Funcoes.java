package parte5;

public class Funcoes {
	public static void main(String[] args) {

		saudacao();
		saudacao();
		saudacao();

		soma(2, 4);
		soma(10, 3);
		soma(56777, 4);
		soma(12313, 4);


		saudar("Thiago");


		System.out.println(dobrar(10));

		int numero = 20;

		int numeroDobrado = dobrar(numero);
		System.out.println(numeroDobrado);

		//********

		System.out.println(verificarPar(231));
		System.out.println(verificarPar(numero));
	}


	public static void saudacao() {
		System.out.println("Ola, está é uma funçÃo!");
	}

	public static void soma(int a, int b) {
		int resultadoSoma = a + b;
		System.out.println("O resultado da soma é: " + resultadoSoma);
	}

	public static void saudar(String nome) {
		System.out.println("Olá, " + nome + ", tudo bem?");
	}

	public static int dobrar(int n) {
		return n * 2;
	}

	public static String verificarPar(int n) {
		if (n % 2 == 0) {
			return "O numero " + n + " é par";
		} else {
			return "O numero " + n + " não é par!";
		}
	}
}
