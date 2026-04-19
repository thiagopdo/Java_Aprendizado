package parte6;

import java.util.Scanner;

public class LoopsB {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    // System.out.print("Entre com primeiro numero: ");
    // double number1 = scanner.nextDouble();
    // System.out.print("Entre com segundo numero: ");
    // double number2 = scanner.nextDouble();
    // System.out.println("Escolha qual operaçao deseja realizar: ");
    // System.out.println("1 - Adição");
    // System.out.println("2 - Subtração");
    // System.out.println("3 - Multiplicação");
    // System.out.println("4 - Divisão");
    // int choice = scanner.nextInt();

    // switch (choice) {
    // case 1 -> System.out.println("Resultado: " + (number1 + number2));
    // case 2 -> System.out.println("Resultado: " + (number1 - number2));
    // case 3 -> System.out.println("Resultado: " + (number1 * number2));
    // case 4 -> {
    // if (number2 != 0) {
    // System.out.println("Resultado: " + (number1 / number2));
    // } else {
    // System.out.println("Erro: Divisão por zero não é permitida.");
    // }
    // }
    // default -> System.out.println("Opção inválida.");
    // }

    int numeroAleatorio = (int) (Math.random() * 100) + 1;
    int tentativas = 0;
    System.out.println("Bem-vindo ao jogo de adivinhação! Tente adivinhar o número entre 1 e 100.");
    int palpite = scanner.nextInt();
    while (palpite != numeroAleatorio) {
      tentativas++;
      if (palpite < numeroAleatorio) {
        System.out.println("Muito baixo! Tente novamente.");
      } else {
        System.out.println("Muito alto! Tente novamente.");
      }
      palpite = scanner.nextInt();
    }
    System.out.println("Parabéns! Você acertou o número em " + tentativas + " tentativas.");

    scanner.close();
  }
}
