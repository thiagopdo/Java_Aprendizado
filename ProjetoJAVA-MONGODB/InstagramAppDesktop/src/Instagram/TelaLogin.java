package Instagram;

import org.bson.Document;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static com.mongodb.client.model.Filters.eq;


class TelaLogin extends JPanel {
	private final InstagramApp app;

	private final JTextField campoEmail = new JTextField(24);
	private final JPasswordField campoSenha = new JPasswordField(24);
	private final JLabel rotErro = new JLabel(" ", SwingConstants.CENTER);

	TelaLogin(InstagramApp app) {
		this.app = app;
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel titulo = new JLabel("Instagram", SwingConstants.CENTER);
		titulo.setFont(new Font("SansSerif", Font.BOLD, 32));
		titulo.setBorder(new EmptyBorder(0, 0, 20, 0));

		gbc.gridx = 0;
		gbc.gridy = 0;

		gbc.gridwidth = 2;

		add(titulo, gbc);

		gbc.gridwidth = 1;

		add(new JLabel("E-mail:"), next(gbc, 0, 1));
		add(campoEmail, next(gbc, 1, 1));

		add(new JLabel("Senha:"), next(gbc, 0, 2));
		add(campoSenha, next(gbc, 1, 2));

		rotErro.setForeground(Color.RED);
		add(rotErro, next(gbc, 0, 3, 2));

		JButton bEntrar = new JButton("Entrar");
		JButton bCadastrar = new JButton("Cadastrar");

		bEntrar.addActionListener(e -> login());
		bCadastrar.addActionListener(e -> cadastrar());

		JPanel botoes = new JPanel(new FlowLayout());
		botoes.setOpaque(false);
		botoes.add(bEntrar);
		botoes.add(bCadastrar);

		gbc.gridy = 4;
		gbc.gridwidth = 2;
		add(botoes, next(gbc, 0, 4, 2));

	}

	private void cadastrar() {
		String email = campoEmail.getText().trim();
		String senha = new String(campoSenha.getPassword());

		if(email.isEmpty() || senha.isEmpty()) {
			rotErro.setText("Preencha todos os campos.");
			return;
		}

		boolean jaExiste = ConexaoMongo.USUARIOS.find(eq("email", email)).first() != null;

		if(jaExiste) {
			rotErro.setText("E-mail já cadastrado.");
			return;
		}

		Document novo = new Document("usuario", email.split("@")[0])
						.append("email", email)
						.append("senha", senha)
						.append("nome", "")
						.append("bio", "")
						.append("foto_perfil", "")
						.append("criado_em", new java.util.Date());

		ConexaoMongo.USUARIOS.insertOne(novo);

		JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso! Agora faça login.");

	}

	private void login() {
		String email = campoEmail.getText().trim();
		String senha = new String(campoSenha.getPassword());
		if(email.isEmpty() || senha.isEmpty()) {
			rotErro.setText("Preencha todos os campos.");
			return;
		}

		Document u = ConexaoMongo.USUARIOS.find(eq("email", email)).first();

		if(u == null || !u.getString("senha").equals(senha)) {
			rotErro.setText("E-mail ou senha incorretos.");
			return;
		}

		app.setUsuarioAtual(u);

		campoEmail.setText("");
		campoSenha.setText("");

		rotErro.setText("");
		app.mostrarTela("feed");
	}

	private GridBagConstraints next(GridBagConstraints base, int x, int y) {
		return next(base, x, y, 1);
	}

	private GridBagConstraints next(GridBagConstraints base, int x, int y, int w) {
		GridBagConstraints n = (GridBagConstraints) base.clone();
		n.gridx = x;
		n.gridy = y;
		n.gridwidth = w;
		return n;
	}
}
