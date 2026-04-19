package parte7;


import java.util.Arrays;

public class Listas {
  public static void main(String[] args) {
    int[] numeros = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    // System.out.println("Primeiro elemento: " + numeros[0]);

    // tamanho fixo, vazio
    String[] frutas = new String[3];
    frutas[0] = "Maçã";
    frutas[1] = "Banana";
    frutas[2] = "Laranja";
    // System.out.println("Frutas: " + frutas[0] + ", " + frutas[1] + ", " +
    // frutas[2]);

    double[] precos = { 1.00, 0.50, 0.75 };
    precos[2] = 0.80; // Atualizando o preço da laranja
    // System.out.println("Preços: " + precos[0] + ", " + precos[1] + ", " +
    // precos[2]);

    // somar dos el do array
    int soma = 0;
    for (int i = 0; i < numeros.length; i++) {
      soma += numeros[i];
    }
    System.out.println("Soma: " + soma);

    // foreach
    for (String fruta : frutas) {
      System.out.println("Fruta: " + fruta);
    }

    int[] valores = { 10, 20, 30, 40, 50 };
    int maiorValor = valores[0];
    int j = 0;
    while (j < valores.length) {
      if (valores[j] > maiorValor) {
        maiorValor = valores[j];
      }
      j++;
    }
    System.out.println("Maior valor: " + maiorValor);

    /// FOREACH
    ///
    for (int numero : numeros) {
      System.out.println("Número: " + numero);
    }

    // contatenar elementos de um array
    String[] palavras = {
        "Olá",
        "mundo",
        "!"
    };
    String frase = "";
    for (String palavra : palavras) {
      frase += palavra + " ";
    }
    System.out.println(frase);

    char[] letras = { 'a', 'e', 'i', 'o', 'u' };
    char letraProcurada = 'i';
    for (char letra : letras) {
      if (letra == letraProcurada) {
        System.out.println("Encontramos a letra: " + letra);
        break;
      }
    }

    // loops com if

    int somaPares = 0;
    for (int numero : numeros) {
      if (numero % 2 == 0) {
        somaPares += numero;
      }
    }
    System.out.println("Soma dos pares: " + somaPares);

    // exibir valores maiores que um determinado valor
    int[] nums = { 12, 6, 18, 25, 49, 50 };
    int limite = 18;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] > limite) {
        System.out.println("Número maior que " + limite + ": " + nums[i]);
      }
    }

    // atualizar valores do array
    for (int i = 0; i < numeros.length; i++) {
      numeros[i] *= 2;
    }
    System.out.println("Array atualizado: " + numeros[1]);

    for (int numero : numeros) {
      System.out.println("Número atualizado: " + numero);
    }

    numeros[5] = 1;
    System.out.println("Número atualizado: " + numeros[5]);

    for (int i = 0; i < frutas.length; i++) {
      if (frutas[i].equals("Banana")) {
        frutas[i] = "Abacaxi";
      }
    }
    System.out.println("Frutas atualizadas: " + frutas[0] + ", " + frutas[1] + ", " + frutas[2]);

    // metodo toString;
    String dadosNumeros = Arrays.toString(numeros);
    System.out.println("Dados dos números: " + dadosNumeros);

    String dadosFrutas = Arrays.toString(frutas);
    System.out.println("Dados das frutas: " + dadosFrutas);

    // adicionar elementos a um array
    int[] novoArray = new int[numeros.length + 1];
    for (int i = 0; i < numeros.length; i++) {
      novoArray[i] = numeros[i];
    }
    System.out.println("Novo array: " + Arrays.toString(novoArray));

    //
    String[] novaFrutas = new String[frutas.length + 1];
    System.arraycopy(frutas, 0, novaFrutas, 0, frutas.length);
    novaFrutas[novaFrutas.length - 1] = "Manga";
    System.out.println("Nova frutas: " + Arrays.toString(novaFrutas));

    // arrayList
    java.util.ArrayList<String> listaFrutas = new java.util.ArrayList<>(Arrays.asList("Maça", "Banana", "Laranja"));
    System.out.println(listaFrutas);

    listaFrutas.add("Abacaxi");
    System.out.println(listaFrutas);

    // REFERENCE TRAP
    int[] arrayOriginal = { 1, 2, 3 };
    int[] arrayCopia = arrayOriginal;
    arrayCopia[0] = 10;
    System.out.println("Array original: " + Arrays.toString(arrayOriginal));
    System.out.println("Array cópia: " + Arrays.toString(arrayCopia));
    // Para evitar isso, podemos criar uma cópia real do array usando Arrays.copyOf
    int[] arrayClone = arrayOriginal.clone();
    arrayClone[0] = 20;
    System.out.println("Array original: " + Arrays.toString(arrayOriginal));
    System.out.println("Array clone: " + Arrays.toString(arrayClone));

    // ARRAYS 2D
    // [[1,2], [2,4], [3,6]]

    int[][] matriz = {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };

    System.out.println(matriz[1][2]);

    // criando array 2d vazio e preenchendo
    int[][] tabela = new int[3][3];
    tabela[0][0] = 10;
    tabela[1][1] = 20;
    tabela[2][2] = 30;

    // for (int[] linha : tabela) {
    //   System.out.println(Arrays.toString(linha));
    // }

    int[][] grade = new int[4][5];
    for (int m = 0; m < grade.length; m++) {
      for (int n = 0; n < grade[m].length; n++) {
        grade[m][n] = m * n;
      }
    }
    for (int[] linha : grade) {
      System.out.println(Arrays.toString(linha));
    }

  }
}
