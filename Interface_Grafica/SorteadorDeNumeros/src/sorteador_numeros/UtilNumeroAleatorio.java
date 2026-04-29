package sorteador_numeros;

import java.util.Random;

public final class UtilNumeroAleatorio {
	private static final Random GERADOR = new Random();

	public static int sortear(int minimo, int maximo) {
		return GERADOR.nextInt((maximo - minimo) + 1) + minimo;
	}

	private UtilNumeroAleatorio() {}
}
