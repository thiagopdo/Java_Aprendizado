package parte7;

import java.util.Arrays;
import java.util.ArrayList;

public class Exercicios {
  public static void main(String[] args) {
    // int[] numeros = { 1, 2, 3, 5, 6, 1, 3, 2, 2, 3, 3 };
    // int[] invertido = new int[numeros.length];
    // for (int i = 0; i < numeros.length; i++) {
    // invertido[numeros.length - 1 - i] = numeros[i];
    // }
    // // System.out.println("Número original: " + Arrays.toString(numeros));
    // // System.out.println("Número invertido: " + Arrays.toString(invertido));

    // int numeroFrequente = numeros[0];
    // int index = 1;
    // for (int i = 0; i < numeros.length; i++) {
    // int contagem = 0;
    // for (int j = 0; j < numeros.length; j++) {
    // if (numeros[j] == numeros[i]) {
    // contagem++;
    // }
    // }
    // if (contagem > index) {
    // index = contagem;
    // numeroFrequente = numeros[i];
    // }
    // }
    // // System.out.println("Número frequente: " + numeroFrequente);

    int[][] matrix = {
        { 1, 2, 3 },
        { 4, 5, 6 },
    };
    int[][] index = new int[matrix[0].length][matrix.length];
    // System.out.println("Matriz original:");
    for (int[] linha : matrix) {
       System.out.println(Arrays.toString(linha));
    }
    // System.out.println("Matriz invertida:");

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        index[j][i] = matrix[i][j];
      }
    }

    for (int[] linha : index) {
       System.out.println(Arrays.toString(linha));
    }

    int[] matrix1 = {
        -1, -2, 3, -5, 6, -1, 3, -2, -2, 3, 3, 2, 2, 4, 4, 5, 5
    };

    // for (int i = 0; i < matrix1.length; i++) {
    // if (matrix1[i] < 0) {
    // matrix1[i] = 0;
    // }
    // }
    // System.out.println(Arrays.toString(matrix1));

    // remover duplicados
    ArrayList<Integer> matrixSemDuplicados = new ArrayList<>();

    for (int i = 0; i < matrix1.length; i++) {
      if (!matrixSemDuplicados.contains(matrix1[i])) {
        matrixSemDuplicados.add(matrix1[i]);
      }
    }
    System.out.println("Matriz com duplicados: " + Arrays.toString(matrix1));
    System.out.println("Matriz sem duplicados: " + matrixSemDuplicados);
  }

}
