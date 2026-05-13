package chat;

import javax.swing.*;
import java.awt.*;

public class TelaContatos extends JPanel {
	private JTextField campoEmail;

	public TelaContatos() {
		setLayout(new GridBagLayout());
		setBackground(new Color(230, 230, 230));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel lblTitulo = new JLabel("Acesse sua conta");
	}

	public void carregarContatos() {}
}
