package parte15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Colecoes {
  public static void main(String[] args) {

    // Coleção
    /**
     * LIST - ArrayList, LinkedList
     */
    List<String> listaDeNomes = new ArrayList<>();
    // adicionar
    listaDeNomes.add("Maria");
    listaDeNomes.add("João");
    listaDeNomes.add("Ana");
    listaDeNomes.add("Jumenta");
    listaDeNomes.add("Thiago");
    listaDeNomes.add("Bia");
    listaDeNomes.add("Roberto");
    listaDeNomes.add("Eduarda");
    listaDeNomes.add("Diogenes");

    // resgatar
    System.out.println("Primeiro nome: " + listaDeNomes.get(0));
    System.out.println("Segundo nome: " + listaDeNomes.get(1));
    System.out.println("Terceiro nome: " + listaDeNomes.get(2));

    // Alterar
    listaDeNomes.set(1, "Carlos");
    System.out.println("Segundo nome alterado: " + listaDeNomes.get(1));

    // Remover
    listaDeNomes.remove(0);
    System.out.println("Lista após remoção: " + listaDeNomes);

    // Procurar por valor
    System.out.println(listaDeNomes.contains("TEste"));
    System.out.println(listaDeNomes.contains("Ana"));

    // LINKEDLIST
    List<Integer> numeros = new LinkedList<>();
    numeros.add(10);
    numeros.add(20);
    numeros.add(23);
    numeros.add(2);
    numeros.add(1);
    numeros.add(31);
    numeros.add(30);
    numeros.add(40);

    System.out.println(numeros.get(3));
    numeros.remove(0);
    System.out.println(numeros);

    // ver valores
    System.out.println(numeros.toString());

    numeros.set(2, 35);

    System.out.println(numeros.get(2));

    /**
     * SET
     * HashSet, TreeSet, LinkedHashSet
     */
    // HashSet - não mantém ordem e não permite duplicatas
    Set<String> conjunto = new HashSet<>();
    conjunto.add("Laranja");
    conjunto.add("Abacaxi");
    conjunto.add("Maçã");

    System.out.println(conjunto);
    System.out.println(conjunto.contains("Abacaxi"));

    // LiskedHashSet - mantém a ordem de inserção e não permite duplicatas
    Set<Integer> numerosSet = new LinkedHashSet<>();
    numerosSet.add(10);
    numerosSet.add(2);
    numerosSet.add(2);
    numerosSet.add(2);
    numerosSet.add(5);
    numerosSet.add(55);

    System.out.println(numerosSet);

    // TReeSet - mantém os elementos ordenados e não permite duplicatas
    Set<String> nomesSet = new TreeSet<>();
    nomesSet.add("Maria");
    nomesSet.add("João");
    nomesSet.add("Ana");
    nomesSet.add("Carlos");

    System.out.println(nomesSet);
    // System.out.println(nomesSet.get(0)); // TreeSet não suporta acesso por índice

    /**
     * MAP - HashMap, TreeMap, LinkedHashMap
     */
    // HASHMAP - não mantém ordem e permite chaves nulas
    Map<String, Integer> mapa = new HashMap<>();
    mapa.put("Maria", 30);
    mapa.put("João", 25);
    mapa.put("Ana", 28);
    System.out.println(mapa);
    System.out.println("Idade de Maria: " + mapa.get("Maria"));
    System.out.println("Idade de João: " + mapa.get("João"));
    System.out.println("Idade de Ana: " + mapa.get("Ana"));

    System.out.println("Idade de Carlos: " + mapa.get("Carlos")); // Retorna null para chave inexistente
    // mapa.remove("João");
    System.out.println("Mapa após remoção de João: " + mapa);
    System.out.println(mapa.containsKey("Alan")); // Retorna false
    System.out.println(mapa.entrySet());

    // LinkedHashMap - mantém a ordem de inserção e permite chaves nulas
    Map<String, String> capitalMap = new LinkedHashMap<>();
    capitalMap.put("Brasil", "Brasília");
    capitalMap.put("Argentina", "Buenos Aires");
    capitalMap.put("Chile", "Santiago");
    System.out.println(capitalMap);
    capitalMap.put("", "Montevidéu");
    System.out.println(capitalMap);
    System.out.println(capitalMap.entrySet());

    // TREEMAP - mantém os elementos ordenados por chave e não permite chaves nulas
    Map<String, Double> produtoPrecoMap = new TreeMap<>();
    produtoPrecoMap.put("Feijão", 7.49);
    produtoPrecoMap.put("Macarrão", 3.99);
    produtoPrecoMap.put("Arroz", 5.99);
    produtoPrecoMap.put("Açúcar", 4.49);
    System.out.println(produtoPrecoMap);
    System.out.println(produtoPrecoMap.containsKey("Arroz"));

    /**
     * ITERANDO COLLECTIONS
     * - For tradicional
     * - For-each
     * - Iterator
     */
    // for each
    for (String nome : listaDeNomes) {
      System.out.println(nome);
    }

    // Iterator - permite remoção segura durante a iteração
    Iterator<String> nomesIterator = listaDeNomes.iterator();
    // remover
    while (nomesIterator.hasNext()) {
      String nome = nomesIterator.next();
      if (nome.equals("João")) {
        nomesIterator.remove();
      }
    }
    System.out.println("Lista após remoção: " + listaDeNomes);

    // LISTITERATOR - permite navegação bidirecional e modificação da lista durante
    // a iteração
    ListIterator<String> listIterator = listaDeNomes.listIterator();
    while (listIterator.hasNext()) {
      System.out.println("Nome: " + listIterator.next());
    }
    while (listIterator.hasPrevious()) {
      System.out.println("Nome reverso: " + listIterator.previous());
    }

    /**
     * COLEÇÕES IMUTÁVEIS
     * - Collections.unmodifiableList()
     * - Collections.unmodifiableSet()
     * - Collections.unmodifiableMap()
     */
    List<String> listaMutavel = new ArrayList<>();
    listaMutavel.add("Teste");
    listaMutavel.add("Teste2");

    List<String> listaImutavel = Collections.unmodifiableList(listaMutavel);
    System.out.println(listaImutavel);

    // listaImutavel.add("TEste3"); // Lança UnsupportedOperationException

    List<String> listaImutavel2 = List.of("Teste", "Teste2", "Teste3");
    System.out.println(listaImutavel2);
    // listaImutavel2.add("Teste4"); // Lança UnsupportedOperationException

    Set<Integer> listaImutavelSet = Set.of(1, 2, 3, 4);
    System.out.println(listaImutavelSet);
    // listaImutavelSet.add(5); // Lança UnsupportedOperationException

    /**
     * FILTER, MAP, REDUCE - Stream API
     */
    // FILTER
    List<Integer> numerosFiltrados = numeros.stream().filter(numero -> numero > 30).collect(Collectors.toList());
    System.out.println("Números filtrados: " + numerosFiltrados);

    /**
     * Busca
     */
    int numeroParaBuscar = 23;
    boolean encontrou = false;
    for (Integer numero : numeros) {
      if (numero == numeroParaBuscar) {
        System.out.println("Número encontrado: " + numero);
        encontrou = true;
        break;
      }
    }
    if (!encontrou) {
      System.out.println("Número não encontrado");
    }
    // contains
    String nomeBuscado = "Carlos";
    boolean nomeEncontrado = listaDeNomes.contains(nomeBuscado);
    System.out.println("Nome encontrado: " + nomeEncontrado);

    // FINDANY
    Optional<Integer> qualquerNumero = numeros.stream().findAny();
    System.out.println("Qualquer número: " + qualquerNumero.orElse(null));
    Optional<Integer> primeirNumero = numeros.stream().findFirst();
    System.out.println("Primeiro número: " + primeirNumero.orElse(null));

    Optional<Integer> primeiroPar = numeros.stream().filter(num -> num % 2 == 0).findFirst();
    System.out.println("Primeiro número par: " + primeiroPar.orElse(null));

    /**
     * Map - busca por chave
     */
    // modificam uma collection
    List<Integer> quadrados = numeros.stream().map(n -> n * n).collect(Collectors.toList());
    System.out.println(numeros);
    System.out.println("Quadrados: " + quadrados);
    List<String> nomeMaiusculo = listaDeNomes.stream().map(String::toUpperCase).collect(Collectors.toList());
    System.out.println("Nomes em maiúsculo: " + nomeMaiusculo);

    // MODIFICAÇAO
    // add novo el
    numeros.add(50);
    // remove el pelo valor
    System.out.println(numeros);
    numeros.remove(Integer.valueOf(1));
    System.out.println(numeros);
    // alterar todos elementos
    numeros.replaceAll(numero -> numero * 3);
    System.out.println(numeros);

    numeros.add(201);
    numeros.add(205);
    numeros.add(208);
    numeros.add(210);
    System.out.println(numeros);

    // remova os elementos em condiçao
    numeros.removeIf(numero -> numero > 200);
    System.out.println(numeros);

    // REDUCE
    int soma = numeros.stream().reduce(0, (acumulador, numero) -> acumulador + numero);
    System.out.println("Soma: " + soma);

    String concatenacaoNomes = listaDeNomes.stream().reduce("", (acumulador, nome) -> acumulador + nome + " ");
    System.out.println("Concatenacao de nomes: " + concatenacaoNomes.trim());

    /**
     * ordenaçao com comparator
     */

    List<Pessoa> pessoas = new ArrayList<>();
    pessoas.add(new Pessoa("Pedro", 33));
    pessoas.add(new Pessoa("Maria", 28));
    pessoas.add(new Pessoa("João", 25));
    pessoas.add(new Pessoa("Ana", 30));
    pessoas.add(new Pessoa("Ana", 20));
    pessoas.add(new Pessoa("Carlos", 27));
    pessoas.add(new Pessoa("Carlos", 26));

    // ordenar por nome
    pessoas.sort(Comparator.comparing(Pessoa::getNome));
    for (Pessoa pessoa : pessoas) {
      System.out.println(pessoa);
    }
    // ordernar por idade
    pessoas.sort(Comparator.comparingInt(Pessoa::getIdade));
    for (Pessoa pessoa : pessoas) {
      System.out.println(pessoa);
    }
    // ordenar por nome e idade
    pessoas.sort(Comparator.comparing(Pessoa::getNome).thenComparing(Pessoa::getIdade));
    for (Pessoa pessoa : pessoas) {
      System.out.println(pessoa);
    }
    /**
     * Uso avançado de streams
     * 
     */
    // flatMap - transformar uma coleção de coleções em uma coleção única
    List<List<String>> listaDeListas = Arrays.asList(
        Arrays.asList("Maça", "Morango", "Abacaxi"),
        Arrays.asList("Carro", "Moto", "Bicicleta"),
        Arrays.asList("Cachorro", "Gato", "Pássaro"));

    List<String> listaUnica = listaDeListas.stream().flatMap(List::stream).collect(Collectors.toList());
    System.out.println(listaUnica);

    // pipeline - encadeamento de operações em streams
    List<Integer> resultado = numeros.stream()
        .filter(n -> n % 2 == 0) // filtro numeros pares
        .map(n -> n * 5) // multiplica por 5
        .sorted() // ordena os numeros
        .collect(Collectors.toList()); // coleta em uma nova lista
    System.out.println(numeros);
    System.out.println(resultado);

    /**
     * Collectors
     */
    // collectors
    List<Produto> produtos = Arrays.asList(
        new Produto("Camisa", "Roupas"),
        new Produto("Calça", "Roupas"),
        new Produto("Tênis", "Calçados"),
        new Produto("Bola", "Esportes"),
        new Produto("Raquete", "Esportes"),
        new Produto("Tv", "Eletrônicos"));

    // agrupar por categoria
    Map<String, List<Produto>> produtosPorCategoria = produtos.stream()
        .collect(Collectors.groupingBy(p -> p.categoria));
    System.out.println(produtos);
    System.out.println(produtosPorCategoria);

    //particionar
    Map<Boolean, List<Produto>> eletronicosENaoEletronicos = produtos
    .stream()
    .collect(Collectors
      .partitioningBy(p->p.categoria
        .equals("Esportes")));
    System.out.println(eletronicosENaoEletronicos);

    //contando elementos por categoria
    long totalProdutos = produtos.stream()
    .collect(Collectors.counting());
    System.out.println("Total de produtos: " + totalProdutos);
  }
}