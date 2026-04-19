package parte14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Generics {
	public static void main(String[] args) {

		// classes GENERICS
		// ao instanciar o objeto, ele vira alguma coisa
		Caixa<Integer> caixaInteira = new Caixa<>();
		caixaInteira.adicionar(10);
		System.out.printf("Conteúdo da caixa: %d\n", caixaInteira.getConteudo());

		Caixa<String> caixaTexto = new Caixa<>();
		caixaTexto.adicionar("Olá, Generics!");
		System.out.printf("Conteúdo da caixa: %s\n", caixaTexto.getConteudo());

		// metodos GENERICS
		System.out.println("Maior valor entre 5 e 10: " + obterMaior(5, 10));

		System.out.println("Maior valor entre JAVA e PYTHON: " + obterMaior("JAVA", "PYTHON"));

		String[] letras = { "A", "B", "C" };
		imprimirArray(letras);

		Integer[] numeros = { 1, 2, 3 };
		imprimirArray(numeros);

		Boolean[] booleans = { true, false, true };
		imprimirArray(booleans);

		// BOUNDED TYPES
		Comparador<Integer> comparadorInteiro = new Comparador<>();
		System.out.println("Maior valor entre 3 e 7: " + comparadorInteiro.obterMaior(3, 7));
		Comparador<Double> comparadorDouble = new Comparador<>();
		System.out.println("Maior valor entre 3.5 e 7.2: " + comparadorDouble.obterMaior(3.5, 7.2));

		// WILDCARDS
		List<Integer> numeros2 = List.of(1, 2, 3, 4, 5);
		List<String> palavras2 = List.of("teste", "java", "generigo");

		imprimirLista(numeros2);
		imprimirLista(palavras2);

		// wildcars
		System.out.println(somarNumeros(numeros2));
		// somarNumeros(palavras2);
		// aceita apenas tipos numericos
		List<Integer> numeros3 = new ArrayList<>();
		adicionarNumeros(numeros3);
		for (Integer numero : numeros3) {
			System.out.println(numero);
		}

		// GENERICS COM COLLECTIOSN
		List<Integer> listaInteiros = new ArrayList<>();
		listaInteiros.add(10);
		listaInteiros.add(20);
		listaInteiros.add(9);
		System.out.println("Lista de Inteiros: " + listaInteiros);

		for (Number numero : listaInteiros) {
			System.out.println("Número: " + numero);
		}

		Set<String> conjuntoStrings = new HashSet<>();
		conjuntoStrings.add("Java");
		conjuntoStrings.add("Java");
		conjuntoStrings.add("Generics");
		for (String elemento : conjuntoStrings) {
			System.out.println("Elemento: " + elemento);
		}

		Map<String, Integer> mapa = new HashMap<>();
		mapa.put("Um", 1);
		mapa.put("Dois", 2);
		for (Map.Entry<String, Integer> entrada : mapa.entrySet()) {
			System.out.println("Chave: " + entrada.getKey() + ", Valor: " + entrada.getValue());
		}

		// GENERICS COM INTERFACE
		Armazenamento<String> armazenamentoTexto = new ArmazenamentoTexto();
		armazenamentoTexto.salvar("Olá, Generics!");
		System.out.println("Recuperando texto: " + armazenamentoTexto.recuperar());

		Armazenamento<Integer> armazenamentoNumero = new ArmazenamentoNumero();
		armazenamentoNumero.salvar(42);
		System.out.println("Recuperando número: " + armazenamentoNumero.recuperar());

		// RESTRICAO MULTIPLA DE TIPOS
		Pato pato = new Pato();
		CriaturaGenerica<Pato> criaturaPato = new CriaturaGenerica<>(pato);
		criaturaPato.usarHabilidades();
	}

	public static <T extends Comparable<T>> T obterMaior(T valor1, T valor2) {
		return (valor1.compareTo(valor2) > 0 ? valor1 : valor2);
	}

	public static <T> void imprimirArray(T[] array) {
		for (T elemento : array) {
			System.out.print(elemento + ", ");
		}
	}

	public static void imprimirLista(List<?> lista) {
		for (Object elemento : lista) {
			System.out.println(elemento);
		}
	}

	public static double somarNumeros(List<? extends Number> list) {
		double soma = 0;

		for (Number numero : list) {
			soma += numero.doubleValue();
		}

		return soma;
	}

	public static void adicionarNumeros(List<? super Integer> lista) {

		for (int i = 1; i <= 5; i++) {
			lista.add(i);
		}
	}
}
