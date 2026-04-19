package parte2;

import java.util.Scanner;

public class TesteScanner {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    //

    System.out.print("Digite um número: ");
    int n = scanner.nextInt();

    scanner.nextLine(); // Limpa o buffer do scanner

    System.out.print("Digite uma palavra: "); 

    String palavra = scanner.nextLine();
    
    System.out.println("O número digitado foi: " + n + " e a palavra digitada foi: " + palavra);
    
    scanner.close();
  }
}
