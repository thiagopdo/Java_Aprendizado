package relogio;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(()-> {
			RelogioMundialApp janela = new RelogioMundialApp();
			janela.setLocationRelativeTo(null);
			janela.setVisible(true);
		});
	}
}
