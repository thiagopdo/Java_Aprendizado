package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;


import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JPanel {
	private JTextField campoEmail, campoNome;
	private JPasswordField campoSenha;
	private JButton botaoSalvar, botaoVoltar;

	private JPanel painelPrincipal;
	private CardLayout controladorTelas;

	private MongoDatabase database;


	public TelaCadastro(JPanel painelPrincipal, CardLayout controladorTelas, MongoDatabase database) {

		setLayout(new GridBagLayout());
		setBackground(new Color(230, 230, 230));
		setOpaque(true);
		this.database = database;
		this.painelPrincipal = painelPrincipal;
		this.controladorTelas = controladorTelas;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel titulo = new JLabel("Crie sua conta");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(titulo, gbc);

		JLabel labelNome = new JLabel("Nome:");
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(labelNome, gbc);

		campoNome = new JTextField(20);
		gbc.gridx = 1;
		add(campoNome, gbc);

		JLabel labelEmail = new JLabel("Email:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(labelEmail, gbc);

		campoEmail = new JTextField(20);
		gbc.gridx = 1;
		add(campoEmail, gbc);

		JLabel labelSenha = new JLabel("Senha:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(labelSenha, gbc);

		campoSenha = new JPasswordField(20);
		gbc.gridx = 1;
		add(campoSenha, gbc);

		botaoSalvar = new JButton("Salvar");
		botaoSalvar.setBackground(new Color(7, 91, 80));
		botaoSalvar.setForeground(Color.WHITE);
		botaoSalvar.setOpaque(true);
		botaoSalvar.setBorderPainted(false);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		add(botaoSalvar, gbc);


		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBackground(new Color(37, 211, 102));
		botaoVoltar.setForeground(Color.BLACK);
		botaoVoltar.setOpaque(true);
		botaoVoltar.setBorderPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		add(botaoVoltar, gbc);


		botaoSalvar.addActionListener(e -> {
			if(campoNome.getText().trim().isEmpty() ||
							campoEmail.getText().trim().isEmpty() ||
							campoSenha.getPassword().length == 0) {

				JOptionPane.showMessageDialog(this,
								"Preencha todos os campos!",
								"Erro",
								JOptionPane.ERROR_MESSAGE);
			}else{
				fazerCadastro(campoNome.getText(),
								campoEmail.getText(),
								new String(campoSenha.getPassword()));
			}
		});

		botaoVoltar.addActionListener(e -> {
			controladorTelas.show(painelPrincipal, "login");
		});
	}

	private void fazerCadastro(String nome, String email, String senha) {

		MongoCollection<Document> users = database.getCollection("users");

		Document jaExiste = users.find(eq("email", email)).first();

		if(jaExiste != null) {
			JOptionPane.showMessageDialog(this,
							"Email já cadastrado!",
							"Erro de cadastro",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		Document novoDoc = new Document("nome", nome)
						.append("email", email)
						.append("senha", senha)
						.append("fotoPerfilBytes", null)
						.append("status", "Olá!")
						.append("telefone", "");

		users.insertOne(novoDoc);

		JOptionPane.showMessageDialog(this,
						"Cadastro realizado com sucesso!",
						"Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
		controladorTelas.show(painelPrincipal, "login");
	}
}
