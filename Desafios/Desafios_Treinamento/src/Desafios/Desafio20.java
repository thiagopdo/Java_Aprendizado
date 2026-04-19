package Desafios.Desafios_Treinamento.src.Desafios;

import java.util.ArrayList;
import java.util.Arrays;

public class Desafio20 {
	public static void trocarPrimeiroUltimo(ArrayList<Object> lista) {
		if(lista.size() > 1) {
			Object temp = lista.getFirst();
			lista.set(0, lista.getLast());
			lista.set(lista.size() - 1, temp);
		}
	}

	public static void main(String[] args) {
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList(5, 10, 15, 20, 25, 30, 35, "X", "r"));

		System.out.println("Lista original: " + lista);
		trocarPrimeiroUltimo(lista);
		System.out.println("Lista modificada: " + lista);
	}
}
