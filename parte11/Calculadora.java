package parte11;

interface Calculadora {
	int somar(int a, int b);

	//pode ser utilizado sem implementaçao
	default int multiplicar(int a, int b) {
		return a * b;
	}
}
