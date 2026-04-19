package Desafios;

import java.util.Scanner;

public class Desafio2 {
  public static void main(String[] args) {
    
    Scanner scanner = new Scanner(System.in);
    System.out.println("Digite de 1 a 7 para saber o dia da semana correspondente: ");
    int dia = scanner.nextInt();

    switch (dia) {
      case 1:
        System.out.println("Domingo");
        break;
      case 2:
        System.out.println("Segunda-feira");
        break;
      case 3:
        System.out.println("Terça-feira");
        break;
      case 4:
        System.out.println("Quarta-feira");
        break;
      case 5:
        System.out.println("Quinta-feira");
        break;
      case 6:
        System.out.println("Sexta-feira");
        break;
      case 7:
        System.out.println("Sábado");
        break;
      default:
        System.out.println("Número inválido! Digite um número entre 1 e 7.");
    }

    scanner.close();
  }
  
}
