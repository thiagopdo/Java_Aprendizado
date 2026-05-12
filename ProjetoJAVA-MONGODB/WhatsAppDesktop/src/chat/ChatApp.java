package chat;

import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;

import javax.swing.*;
import java.awt.*;


public class ChatApp extends JFrame {
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static final String CAMINHO_IMAGEM_PADRAO = "/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/WhatsAppDesktop/src/perfil/padrao.jpg";

	public JPanel painelPrincipal;
	public CardLayout controladorTelas;
	private TelaLogin telaLogin;
	private TelaCadastro telaCadastro;
	private TelaListaChats telaListaChats;
	private TelaPerfil telaPerfil;
	private TelaContatos telaContatos;
	private Usuario usuarioAtual;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new ChatApp().setVisible(true);
		});
	}

	public ChatApp() {
		conectarComBanco();

		setTitle("Clone WhatsApp");
		setSize(900,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		controladorTelas = new CardLayout();

		painelPrincipal = new JPanel(controladorTelas);

		telaLogin = new TelaLogin(painelPrincipal, controladorTelas);
		telaCadastro = new TelaCadastro();
		telaListaChats = new TelaListaChats();
		telaPerfil = new TelaPerfil();
		telaContatos = new TelaContatos();

		painelPrincipal.add(telaLogin, "login");
		painelPrincipal.add(telaCadastro, "cadastro");
		painelPrincipal.add(telaListaChats, "lista");
		painelPrincipal.add(telaPerfil, "perfil");
		painelPrincipal.add(telaContatos, "contatos");

		add(painelPrincipal);

		controladorTelas.show(painelPrincipal, "login");

	}

	private void conectarComBanco() {}

}
