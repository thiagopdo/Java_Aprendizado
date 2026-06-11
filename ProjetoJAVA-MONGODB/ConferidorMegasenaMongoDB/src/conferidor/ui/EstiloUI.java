package conferidor.ui;

import javax.swing.*;
import java.awt.*;

public final class EstiloUI {
	public static final Color COR_PRIMARIA = new Color(34, 139, 91);
	public static final Color COR_SECUNDARIA = new Color(23, 102, 173);
	public static final Color COR_FUNDO = new Color(240, 240, 240);

	public static void aplicar(JFrame tela, String titulo) {
		configurarLookAndFeel();

		Font fontPadrao = new Font("Segoe UI", Font.PLAIN, 14);

		atualizarFontes(fontPadrao);

		tela.getContentPane().setBackground(COR_FUNDO);
		tela.setTitle(titulo);
		tela.setIconImage(null);
	}

	private static void atualizarFontes(Font fontPadrao) {
		java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();

		while(keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);

			if(value instanceof Font) {
				UIManager.put(key, fontPadrao);
			}
		}
	}

	public static JPanel painelComPadding() {
		JPanel p = new JPanel();

		p.setOpaque(false);
		p.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
		return p;
	}

	public static JLabel titulo(String texto) {
		JLabel lbl = new JLabel(texto, SwingConstants.CENTER);

		lbl.setFont(new Font("Segoe UI", Font.BOLD, 24));

		return lbl;
	}

	private static boolean feito = false;

	private static void configurarLookAndFeel() {
		if(feito)
			return;

		try {
			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}

			}
		}catch(Exception ignored) {

		}

		UIManager.put("nimbusBase", COR_SECUNDARIA.darker());
		UIManager.put("nimbusBlueGrey", COR_SECUNDARIA);
		UIManager.put("control", COR_FUNDO);
		UIManager.put("Button.background", COR_PRIMARIA);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
		UIManager.put("Button.minimumSize", new Dimension(100, 30));

		feito = true;

	}
}
