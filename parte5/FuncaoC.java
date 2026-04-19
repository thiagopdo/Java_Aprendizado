package parte5;

import java.util.Scanner;

public class FuncaoC {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    // Runnable tarefa = () -> System.out.println("Tarefa executada!");
    // tarefa.run();

    // List<String> nomes = Arrays.asList("Alice", "Bob", "Charlie");
    // nomes.forEach(nome -> System.out.println("Olá, " + nome + "!"));

    // System.out.println("Escolha o tipo de conversao:");
    // System.out.println("1 - Celsius para Fahrenheit");
    // System.out.println("2 - Fahrenheit para Celsius");

    // int escolha = scanner.nextInt();

    // Runnable celToFah = () -> {
    // System.out.println("Digite a temperatura em Celsius:");
    // double celsius = scanner.nextDouble();
    // double fahrenheit = (celsius * 9 / 5) + 32;
    // System.out.println(celsius + "°C é igual a " + fahrenheit + "°F");
    // };

    // Runnable fahToCel = () -> {
    // System.out.println("Digite a temperatura em Fahrenheit:");
    // double fahrenheit = scanner.nextDouble();
    // double celsius = (fahrenheit - 32) * 5 / 9;
    // System.out.println(fahrenheit + "°F é igual a " + celsius + "°C");
    // };

    // if (escolha == 1) {
    // celToFah.run();
    // } else if (escolha == 2) {
    // fahToCel.run();
    // } else {
    // System.out.println("Opção inválida.");
    // }

    // scanner.close();

    contadorDePalavras();

    scanner.close();
  }

  public static void contadorDePalavras() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Digite a palavra que deseja contar:");
    String frase = scanner.nextLine();

    String[] palavras = frase.trim().split("\\s+");

    int numeroPalavras = palavras.length;
    System.out.println("Número de palavras na frase: " + numeroPalavras);
    System.out.println("Deseja digitar outra palavra? (s/n)");
    String resposta = scanner.nextLine();
    if (resposta.equalsIgnoreCase("s")) {
      contadorDePalavras();
    } else {
      System.out.println("Encerrando o programa.");
    }
    scanner.close();
  }
}
