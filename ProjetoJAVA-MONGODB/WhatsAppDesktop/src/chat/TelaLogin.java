package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import static com.mongodb.client.model.Filters.eq;


import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JPanel {
	private JTextField campoEmail;
	private JPasswordField campoSenha;
	private JButton botaoLogin;
	private JButton botaoCadastro;
	private JPanel painelPrincipal;
	private CardLayout controladorTelas;
	private TelaListaChats telaListaChats;


	private MongoDatabase database;

	public TelaLogin(JPanel painelPrincipal, CardLayout controladorTelas, MongoDatabase database, TelaListaChats telaListaChats) {
		this.painelPrincipal = painelPrincipal;
		this.controladorTelas = controladorTelas;
		this.database = database;
		this.telaListaChats = telaListaChats;
		setLayout(new GridBagLayout());
		setBackground(new Color(230, 230, 230));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel labelTitulo = new JLabel("Acesse sua conta");
		labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(labelTitulo, gbc);

		JLabel labelEmail = new JLabel("Email");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(labelEmail, gbc);

		campoEmail = new JTextField(20);
		gbc.gridx = 1;
		add(campoEmail, gbc);

		JLabel labelSenha = new JLabel("Senha:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(labelSenha, gbc);

		campoSenha = new JPasswordField(20);
		gbc.gridx = 1;
		add(campoSenha, gbc);

		botaoLogin = new JButton("Login");
		botaoLogin.setBackground(new Color(7, 91, 80));
		botaoLogin.setForeground(Color.WHITE);
		botaoLogin.setOpaque(true);
		botaoLogin.setBorderPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		add(botaoLogin, gbc);

		botaoCadastro = new JButton("Cadastre-se");
		botaoCadastro.setBackground(new Color(24, 212, 95));
		botaoCadastro.setForeground(Color.BLACK);
		botaoCadastro.setOpaque(true);
		botaoCadastro.setBorderPainted(false);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.EAST;
		add(botaoCadastro, gbc);

		botaoLogin.addActionListener(e -> fazerLogin(
						campoEmail.getText(),
						new String(campoSenha.getPassword())));

		botaoCadastro.addActionListener(e -> {
			controladorTelas.show(painelPrincipal, "cadastro");
		});

	}

	private void fazerLogin(String email, String senha) {
		MongoCollection<Document> users = database.getCollection("users");

		Document encontrado = users.find(eq("email", email)).first();

		if(encontrado == null) {
			JOptionPane.showMessageDialog(this,
							"Email não encontrado!",
							"Erro de Login",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		String senhaBanco = encontrado.getString("senha");
		if(senhaBanco == null || !senhaBanco.equals(senha)) {
			JOptionPane.showMessageDialog(this,
							"Senha incorreta!",
							"Erro de Login",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		Usuario usuarioAtual = new Usuario(
						encontrado.getObjectId("_id"),
						encontrado.getString("nome"),
						encontrado.getString("email"),
						encontrado.getString("senha"),
						(Binary) encontrado.get("fotoPerfilBytes"),
						encontrado.getString("status"),
						encontrado.getString("telefone")
		);

		controladorTelas.show(painelPrincipal, "lista");
		telaListaChats.carregarChats();
		telaListaChats.iniciarAtualizacao();
	}


}
