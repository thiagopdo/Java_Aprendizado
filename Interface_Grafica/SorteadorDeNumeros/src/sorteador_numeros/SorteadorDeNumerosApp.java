package sorteador_numeros;

import javax.swing.SwingUtilities;

public class SorteadorDeNumerosApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new SorteadorDeNumerosJanela().setVisible(true);

		});
	}

	private SorteadorDeNumerosApp() {}
}
