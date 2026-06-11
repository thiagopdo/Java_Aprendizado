package conferidor.ui;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		super();

		EstiloUI.aplicar(this, "Conferidor de Jogos da Mega-Sena");
		setLayout(new BorderLayout());

		add(EstiloUI.titulo("Conferidor de Jogos da Mega-Sena"),
						BorderLayout.NORTH
		);

		JPanel centro = EstiloUI.painelComPadding();

		centro.setLayout(new GridLayout(0, 1, 0, 12));

		add(centro, BorderLayout.CENTER);

		JButton btnCadastrar = novoBotao("Cadastrar Jogo");
		JButton btnConferir = novoBotao("Conferir Jogos");
		JButton btnHistorico = novoBotao("Histórico de Jogos");
		JButton btnGerar = novoBotao("Gerar Jogo Aleatório");
		JButton btnSair = novoBotao("Sair");

		btnCadastrar.addActionListener(e -> new TelaCadastroJogo().setVisible(true));

		btnConferir.addActionListener(e -> new TelaConferencia().setVisible(true));

		btnHistorico.addActionListener(e -> new TelaHistorico().setVisible(true));

		btnGerar.addActionListener(e -> new TelaGerador().setVisible(true));

		btnSair.addActionListener(e ->
						System.exit(0)
		);

		for(JButton btn : new JButton[]{ btnCadastrar, btnConferir, btnHistorico, btnGerar, btnSair }) {
			centro.add(btn);
		}

		setSize(500, 550);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private static JButton novoBotao(String texto) {
		JButton btn = new JButton(texto);
		btn.setFocusable(false);
		return btn;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
	}
}