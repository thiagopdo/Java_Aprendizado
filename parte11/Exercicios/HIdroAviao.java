package parte11.Exercicios;

public class HIdroAviao extends Barco implements Pilotavel {

	@Override
	public void pilotar() {
		System.out.println("O hidroaviao está voando e depois vai navegar");
	}
}
