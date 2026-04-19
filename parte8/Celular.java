package parte8;

public class Celular {
	String marca;
	String modelo;
	int bateria = 100;


	public void ligar() {
		System.out.println("Ligando o celular " + modelo);
	}

	public void desligar() {
		System.out.println("Desligando o celular " + modelo);
	}

	void usar(int consumo) {
		if (bateria - consumo >= 0) {
			bateria -= consumo;
			System.out.println("Bateria restante: " + bateria + "%");
		} else {
			System.out.println("Bateria cheia");
		}
	}


	public int getBateria() {
		return bateria -= 10;
	}
}
