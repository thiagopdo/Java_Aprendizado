package parte9;


import java.util.Arrays;
import java.util.Random;


public class Bilhete {
	private final int[] numerosEscolhidos;
	private int[] numerosSorteados;

	public Bilhete(int[] numerosEscolhidos) {
		this.numerosEscolhidos = numerosEscolhidos;
	}

	public int[] getNumerosEscolhidos() {
		return numerosEscolhidos;
	}

	public void realizarSorteio() {
		Random aleatorio = new Random();
		numerosSorteados = new int[6];
		for(int i = 0; i < numerosSorteados.length; i++) {
			numerosSorteados[i] = aleatorio.nextInt(60) + 1;
		}

		Arrays.sort(numerosSorteados);
	}

	//metodo para contar qnts numeros jogador acertou
	public int contarAcertos() {
		int acertos = 0;
		for(int numeroEscolhido : numerosEscolhidos) {
			for(int numeroSorteado : numerosSorteados) {
				if(numeroEscolhido == numeroSorteado) {
					acertos++;
				}
			}
		}

		return acertos;
	}

	public void exibirResultado() {
		System.out.println("Numeros escolhidos: " + Arrays.toString(numerosEscolhidos));
		System.out.println("Numeros sorteados: " + Arrays.toString(numerosSorteados));

		int acertos = contarAcertos();
		System.out.println("acertos = " + acertos);
		System.out.println();

	}
}
