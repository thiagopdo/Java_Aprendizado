package Desafios;

import java.util.Scanner;

public class Desafio3 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    @SuppressWarnings("unused")
    double notaValida = -1;
    while (true) {
      System.out.println("Digite uma nota entre 0 e 10:");
      if (scanner.hasNextDouble()) {
        double nota = scanner.nextDouble();
        if (nota >= 0 && nota <= 10) {
          System.out.println("Nota válida: " + nota);
          notaValida = nota;
          break;
        } else {
          System.out.println("Nota inválida. Tente novamente.");
        }
      } else {
        System.out.println("Entrada inválida. Por favor, digite um número.");
        scanner.next(); // Limpa a entrada inválida
      }
    }

    System.out.println("Programa encerrado.");

    scanner.close();
  }
}