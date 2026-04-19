package parte11;

public class CalculadoraAvançado implements Calculadora {
	@Override
	public int somar(int a, int b) {
		return a + b;
	}

	@Override
	public int multiplicar(int a, int b) {
		System.out.println("Multiplicando " + a + " por " + b);
		return a * b;
	}
}
