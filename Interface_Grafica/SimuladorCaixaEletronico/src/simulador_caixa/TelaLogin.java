package simulador_caixa;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {
	public interface OuvinteLogin {
		void aoTentarLogin(String login, String senha);
		void aoSolicitarCriacaoConta();
	}

	private final JTextField campoNome = new JTextField();
	private final JPasswordField campoSenha = new JPasswordField();
	private final OuvinteLogin ouvinte;

	public TelaLogin(OuvinteLogin ouvinte) {
		super(new GridBagLayout());
		this.ouvinte = ouvinte;

		construtorInterface();
	}

	private void construtorInterface() {
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titulo = new JLabel("Bem-vindo ao Caixa Eletrônico", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;

		add(titulo,gbc);

		gbc.gridwidth = 1;

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 14));

		gbc.gridx = 0;
		gbc.gridy = 1;

		add(lblNome,gbc);

		campoNome.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		add(campoNome,gbc);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial", Font.BOLD, 14));

		gbc.gridx = 0;
		gbc.gridy = 2;

		add(lblSenha,gbc);

		campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));

		gbc.gridx = 1;
		add(campoSenha,gbc);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(btnEntrar,gbc);

		JButton btnCriar = new JButton("Criar Conta");
		btnCriar.setFont(new Font("Arial", Font.BOLD, 14));
		gbc.gridx = 1;
		add(btnCriar,gbc);

		btnEntrar.addActionListener(e ->
			ouvinte.aoTentarLogin(campoNome.getText().trim(), new String(campoSenha.getPassword()))
		);

		btnCriar.addActionListener(e -> ouvinte.aoSolicitarCriacaoConta());
	}
}
