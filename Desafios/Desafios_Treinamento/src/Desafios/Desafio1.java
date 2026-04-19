package Desafios;

import java.util.Scanner;

/* 
 Desafio 1 – Verificação de Vogal ou Consoante em Java

 - Faça um programa em Java que verifique se uma letra digitada é uma vogal ou consoante.
 - O programa deverá seguir as seguintes regras:
     - Solicitar ao usuário que insira uma letra.
     - Verificar se o caractere inserido é uma letra do alfabeto.
     - Caso o usuário tenha inserido um caractere válido (ou seja, uma 
     			letra do alfabeto), o programa deverá identificar e informar 
     			se essa letra é uma vogal ou uma consoante.
     - Caso o usuário insira um valor inválido (como números, símbolos ou 
     			mais de um caractere), o programa deverá exibir uma mensagem 
     			de erro, informando que a entrada é inválida e pedindo 
       			para que ele insira apenas uma letra.
*/

public class Desafio1 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Digite uma letra do alfabeto:");
    String input = scanner.nextLine();

    switch (input) {
      case "a", "e", "i", "o", "u", "A", "E", "I", "O", "U":
        System.out.println("A letra '" + input + "' é uma vogal.");
        break;
       case "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "y", "z",
            "B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X", "Y",
            "Z":
        System.out.println("A letra '" + input + "' é uma consoante.");
        break;
      default:
        System.out.println("Caractere inválido. Por favor, insira apenas uma letra do alfabeto.");
    }

    scanner.close();
  }
}
