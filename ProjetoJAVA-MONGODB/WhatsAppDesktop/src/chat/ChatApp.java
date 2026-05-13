package chat;

import com.mongodb.client.*;
import org.bson.BsonUndefined;
import org.bson.types.ObjectId;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


public class ChatApp extends JFrame {
	private static MongoClient mongoClient;
	private MongoDatabase database;
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
		SwingUtilities.invokeLater(() -> {
			new ChatApp().setVisible(true);
		});
	}

	public ChatApp() {

		conectarComBanco();

		setTitle("Clone WhatsApp");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		controladorTelas = new CardLayout();

		painelPrincipal = new JPanel(controladorTelas);

		telaPerfil = new TelaPerfil();
		telaContatos = new TelaContatos();
		telaListaChats = new TelaListaChats(usuarioAtual, telaPerfil, controladorTelas, painelPrincipal, telaContatos);
		telaLogin = new TelaLogin(painelPrincipal, controladorTelas, this.database, telaListaChats);
		telaCadastro = new TelaCadastro(painelPrincipal, controladorTelas, this.database);

		painelPrincipal.add(telaLogin, "login");
		painelPrincipal.add(telaCadastro, "cadastro");
		painelPrincipal.add(telaListaChats, "lista");
		painelPrincipal.add(telaPerfil, "perfil");
		painelPrincipal.add(telaContatos, "contatos");

		add(painelPrincipal);

		controladorTelas.show(painelPrincipal, "login");

	}

	private void conectarComBanco() {
		mongoClient = MongoClients.create("mongodb://localhost:27017");
		database = mongoClient.getDatabase("chatDB");
	}

	private class Mensagem {
		public String conteudo;
		public String remetente;
		public String tipo;
		public long dataEnvio;
		public byte[] imagemBytes;
		public ObjectId messageKey;
		public int indexInterno;

		public Mensagem(String conteudo, String remetente, String tipo, long dataEnvio, byte[] imagemBytes, ObjectId messageKey, int indexInterno) {
			this.conteudo = conteudo;
			this.remetente = remetente;
			this.tipo = tipo;
			this.dataEnvio = dataEnvio;
			this.imagemBytes = imagemBytes;
			this.messageKey = messageKey;
			this.indexInterno = indexInterno;
		}
	}

	private class EstiloMensagem {
		public int tamanhoFonte = 14;
		public Color corFonte = Color.BLACK;
		public double rotacoesGraus = 0;
	}

	protected static class RenderizadorContato implements ListCellRenderer<ContatoItem> {
		@Override
		public Component getListCellRendererComponent(JList<? extends ContatoItem> list, ContatoItem value, int index, boolean isSelected, boolean cellHasFocus) {
			JPanel panel = new JPanel(new BorderLayout(5, 5));
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setBackground(isSelected ? new Color(220, 240, 220) : Color.WHITE);

			JLabel labelFoto = new JLabel();
			labelFoto.setPreferredSize(new Dimension(50, 50));
			byte[] fotoBytes = (value.fotoPerfilBytes != null) ? value.fotoPerfilBytes.getData() : null;

			ImageIcon icon = carregarImagemOuPadrao(fotoBytes, 50, 50);

			labelFoto.setIcon(icon);
			panel.add(labelFoto, BorderLayout.WEST);

			JPanel painelTexto = new JPanel(new GridLayout(2, 1));
			painelTexto.setOpaque(false);

			JLabel labelNome = new JLabel(value.nome != null ? value.nome : "");
			labelNome.setFont(new Font("Arial", Font.BOLD, 14));

			JLabel labelEmail = new JLabel(value.email != null ? value.email : "");
			labelEmail.setFont(new Font("Arial", Font.BOLD, 12));

			painelTexto.add(labelNome);
			painelTexto.add(labelEmail);

			panel.add(painelTexto, BorderLayout.CENTER);

			return panel;
		}
	}

	private static ImageIcon carregarImagemOuPadrao(byte[] bytes, int largura, int altura) {
		if(bytes == null) {
			return carregarImagemPadrao(largura, altura);
		}
		try {

			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));

			if(img == null) {
				return carregarImagemPadrao(largura, altura);
			}
			return new ImageIcon(img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH));

		}catch(Exception e) {
			return carregarImagemPadrao(largura, altura);
		}
	}

	private static ImageIcon carregarImagemPadrao(int largura, int altura) {

		try {

			File arquivo = new File(CAMINHO_IMAGEM_PADRAO);

			if(!arquivo.exists()) {
				System.err.println("Imagem padrão não encontrada!" + CAMINHO_IMAGEM_PADRAO);

				return new ImageIcon();
			}

			BufferedImage imagemOriginal = ImageIO.read(arquivo);

			if(imagemOriginal == null) {
				System.err.println("Imagem padrão inválida!" + CAMINHO_IMAGEM_PADRAO);

				return new ImageIcon();
			}

			BufferedImage redimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = redimensionada.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(imagemOriginal, 0, 0, largura, altura, null);
			g2d.dispose();

			return new ImageIcon(redimensionada);

		}catch(IOException e) {
			e.printStackTrace();

			return new ImageIcon();
		}
	}
}
