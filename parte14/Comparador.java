package parte14;

public class Comparador<T extends Number> {
	public T obterMaior(T n1, T n2) {
		if(n1.doubleValue() > n2.doubleValue()) {
			return n1;
		} else {
			return n2;
		}
	}
}
