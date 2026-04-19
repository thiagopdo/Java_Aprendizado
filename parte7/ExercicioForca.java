package parte7;

import java.util.Scanner;


public class ExercicioForca {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    // // String palavraSecreta = "JAVA";
    // // char[] palavraOculta = new char[palavraSecreta.length()];
    // // int tentativas = 10;
    // // boolean ganhou = false;
    // // for (int i = 0; i < palavraOculta.length; i++) {
    // // palavraOculta[i] = '_';
    // // }
    // // // System.out.println(Arrays.toString(palavraOculta));A

    // // // loop para adivinhar a palavra
    // // while (tentativas > 0 && !ganhou) {
    // // System.out.println("Palavra: " + String.valueOf(palavraOculta));
    // // System.out.println("Tentativas restantes: " + tentativas);
    // // System.out.println("Digite uma letra: ");
    // // char letra = scanner.next().toUpperCase().charAt(0);
    // // System.out.println("Letra digitada: " + letra);

    // // boolean acertou = false;

    // // for (int i = 0; i < palavraSecreta.length(); i++) {
    // // if (palavraSecreta.charAt(i) == letra) {
    // // palavraOculta[i] = letra;
    // // acertou = true;
    // // }
    // // }

    // // // deduzindo se o jogador ganhou
    // // if (!acertou) {
    // // tentativas--;
    // // System.out.println("Letra incorreta! Tentativas restantes: " +
    // tentativas);
    // // } else {
    // // System.out.println("Letra correta!");
    // // }

    // // System.out.println("Palavra: " + String.valueOf(palavraOculta));

    // // if (String.valueOf(palavraOculta).equals(palavraSecreta)) {
    // // ganhou = true;
    // // System.out.println("Parabéns! Você ganhou!");
    // // break;
    // // }
    // // }

    // // for (int i = 0; i < tentativas; i++) {
    // // System.out.println("Digite uma letra: ");
    // // String letra = scanner.nextLine().toUpperCase();
    // // if (palavraSecreta.containPs(letra)) {
    // // palavraAdivinhada += letra;
    // // System.out.println("Acertou! Palavra adivinhada: " + palavraAdivinhada);
    // // if (palavraAdivinhada.equals(palavraSecreta)) {
    // // System.out.println("Parabéns! Você ganhou!");
    // // break;
    // // }
    // // } else {
    // // System.out.println("Errou! Tentativas restantes: " + (tentativas - i -
    // 1));
    // // }
    // // }

    // // EXERCICIO SENHA FORTE
    // System.out.println("Digite uma senha: ");
    // String senha = scanner.nextLine();
    // boolean temLetraMaiuscula = false;
    // boolean temLetraMinuscula = false;
    // boolean temNumero = false;

    // for (int i = 0; i < senha.length(); i++) {
    // char c = senha.charAt(i);
    // if (Character.isUpperCase(c)) {
    // temLetraMaiuscula = true;
    // }
    // if (Character.isLowerCase(c)) {
    // temLetraMinuscula = true;
    // }
    // if (Character.isDigit(c)) {
    // temNumero = true;
    // }
    // }

    // if (temLetraMaiuscula && temLetraMinuscula && temNumero) {
    // System.out.println("Senha forte!");
    // } else {
    // System.out
    // .println("Senha fraca! A senha deve conter pelo menos uma letra maiúscula,
    // uma letra minúscula e um número.");
    // }

    System.out.println("Digite uma senha: ");
    String senha = scanner.next();

    int forca = verificarSenhaForte(senha);
    if (forca == 2) {
      System.out.println("Senha fraca!");
    } else if (forca == 3) {
      System.out.println("Senha média!");
    } else if (forca == 4) {
      System.out.println("Senha forte!");
    } else {
      System.out.println("Senha muito forte!");
    }

    // verificar força

    scanner.close();
  }

  public static int verificarSenhaForte(String senha) {
    int forca = 0;

    if (senha.length() > 8) {
      forca++;
    }
    // letras maiusculas, minusculas, numeros
    if (senha.matches(".*[A-Z].*")) {
      forca++;
    }
    if (senha.matches(".*[a-z].*")) {
      forca++;
    }
    if (senha.matches(".*\\d.*")) {
      forca++;
    }
    if (senha.matches(".*[!@#$&()\\-_?<>]*")) {
      forca++;
    }
    return forca;
  }

}
