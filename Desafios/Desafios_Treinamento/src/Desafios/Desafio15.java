package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.Scanner;

public class Desafio15 {

	private String text;

	public Desafio15(String text) {
		this.text = "";
	}

	public static void main(String[] args) {
		Desafio15 desafio = new Desafio15("");
		desafio.receberString();
		desafio.exibirTexto();
	}

	public void receberString() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite uma string:");
		this.text = scanner.nextLine();
	}

	public String converMaiuscula() {
		return this.text.toUpperCase();
	}

	public void exibirTexto() {
		System.out.printf(converMaiuscula());
	}
}
