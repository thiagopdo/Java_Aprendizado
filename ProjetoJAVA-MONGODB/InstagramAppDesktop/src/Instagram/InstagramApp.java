package Instagram;

import javax.swing.*;
import java.awt.*;

import org.bson.types.ObjectId;
import org.bson.Document;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import static com.mongodb.client.model.Filters.eq;

public class InstagramApp extends JFrame {

	private final CardLayout cards = new CardLayout();
	private final JPanel painelCentral = new JPanel(cards);

	private final TelaLogin telaLogin = new TelaLogin(this);
	private final TelaFeed telaFeed = new TelaFeed(this);
	private final TelaPesquisa telaPesquisa = new TelaPesquisa(this);
	private final TelaNovoPost telaNovoPost = new TelaNovoPost(this);
	private final TelaPerfil telaPerfil = new TelaPerfil(this);

	private Document usuarioAtual;
	private Document perfilVisualizado;

	public InstagramApp() {
		super("Instagram");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);

		inicializarTelas();
		tentarAutoLogin();
	}

	private void tentarAutoLogin() {
		Document sessao = ConexaoMongo.SESSIONS.find().first();
		if(sessao != null) {
			ObjectId uid = sessao.getObjectId("usuario_id");
			Document u = ConexaoMongo.USUARIOS.find(eq("_id", uid)).first();
			if(u != null) {
				setUsuarioAtual(u);
				mostrarTela("feed");
				return;
			}
		}

		mostrarTela("login");
	}

	public void mostrarTela(String nome) {
		if(usuarioAtual == null && !"login".equals(nome)) {
			cards.show(painelCentral, "login");
			return;
		}

		if("feed".equals(nome)) {
			telaFeed.recarregar();
		}

		cards.show(painelCentral, nome);
	}


	private void inicializarTelas() {
		painelCentral.add(telaLogin, "login");
		painelCentral.add(telaFeed, "feed");
		painelCentral.add(telaPesquisa, "pesquisa");
		painelCentral.add(telaNovoPost, "novoPost");
		painelCentral.add(telaPerfil, "perfil");

		setContentPane(painelCentral);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new InstagramApp().setVisible(true));
	}

	public void setUsuarioAtual(Document u) {
		this.usuarioAtual = u;

		ConexaoMongo.SESSIONS.deleteMany(new Document());

		ConexaoMongo.SESSIONS.insertOne(new Document()
						.append("usuario_id", u.getObjectId("_id"))
						.append("logado_em", new java.util.Date()));
	}

	public Document getUsuarioAtual() {
		return usuarioAtual;
	}

	public void recarregarFeed() {
		telaFeed.recarregar();
	}

	public void setPerfilVisualizado(Document usuarioAtual) {
		this.perfilVisualizado = usuarioAtual;

		telaPerfil.carregar(usuarioAtual);
	}

	public void logout() {
		usuarioAtual = null;
		ConexaoMongo.SESSIONS.deleteMany(new Document());

		mostrarTela("login");
	}

	public MouseAdapter abrirPostModal(Document post) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DialogPost dialog = new DialogPost(InstagramApp.this, post);
				dialog.setVisible(true);
			}
		};
	}

	public MouseAdapter irParaPerfil(Document perfil) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setPerfilVisualizado(perfil);
				mostrarTela("perfil");
			}
		};
	}

	public MouseAdapter abrirPostNoFeed(Document p) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				telaFeed.recarregar();
				mostrarTela("feed");
			}
		};
	}

	public void trocarPara(JPanel tela, String chave) {
		painelCentral.add(tela, chave);

		cards.show(painelCentral, chave);
	}
}
