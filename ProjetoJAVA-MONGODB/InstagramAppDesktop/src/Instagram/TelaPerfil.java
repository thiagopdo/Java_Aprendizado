package Instagram;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.types.Binary;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class TelaPerfil extends JPanel {
	private static final String PATH_AVATAR_FALLBACK = "/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg";
	private final InstagramApp app;

	private Document perfil;

	public TelaPerfil(InstagramApp app) {
		this.app = app;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

	}

	public void carregar(Document perfilInicial) {
		this.perfil = ConexaoMongo.USUARIOS.find(eq("_id", perfilInicial.getObjectId("_id"))).first();

		removeAll();

		add(criarTopo(), BorderLayout.NORTH);
		add(criarConteudo(), BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}

	private JComponent criarConteudo() {
		JPanel conteudo = new JPanel(new BorderLayout());
		conteudo.setBackground(Color.WHITE);
		conteudo.add(criarHeader(), BorderLayout.NORTH);
		conteudo.add(criarGradePosts(), BorderLayout.CENTER);

		return conteudo;
	}


	private JComponent criarHeader() {
		JPanel header = new JPanel(new BorderLayout(20, 0));
		header.setBackground(Color.WHITE);
		header.setBorder(new EmptyBorder(16, 20, 16, 20));
		header.add(new JLabel(loadAvatarIcon()), BorderLayout.WEST);

		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setOpaque(false);

		JPanel linhaUser = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		linhaUser.setOpaque(false);

		JLabel lblName = new JLabel(perfil.getString("usuario"));
		lblName.setFont(new Font("SansSerif", Font.BOLD, 24));

		linhaUser.add(lblName);

		boolean meuPerfil = perfil.getObjectId("_id")
						.equals(app.getUsuarioAtual().getObjectId("_id"));
		if(meuPerfil) {
			JButton btEdit = new JButton("Editar perfil");
			btEdit.setFocusPainted(false);
			btEdit.addActionListener(e -> app.trocarPara(new TelaEditarPerfil(app, perfil), "editarPerfil"));

			linhaUser.add(btEdit);

		}else{
			JButton btSeguir = new JButton(estaSeguindo() ? "Seguindo" : "Seguir");
			btSeguir.addActionListener(e -> {
				alternarSeguir();
				carregar(perfil);
			});

			linhaUser.add(btSeguir);
			JButton btChat = new JButton("Chat");
			btChat.setFocusable(false);
			btChat.addActionListener(e -> new DialogChat(app, perfil).setVisible(true));

			linhaUser.add(btChat);
		}

		centro.add(linhaUser);
		centro.add(Box.createVerticalStrut(12));

		JPanel stats = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
		stats.setOpaque(false);
		stats.add(new JLabel(htmlStat(contarPosts(), "Publicações")));

		JButton bSegs = new JButton(htmlStat(contarSeguidores(), "Seguidores"));
		bSegs.setFocusPainted(false);
		bSegs.addActionListener(e -> app.trocarPara(new TelaSeguidores(app, perfil), "seguidores"));

		stats.add(bSegs);

		JButton bSeg = new JButton(htmlStat(contarSeguindo(), "Seguindo"));
		bSeg.setFocusPainted(false);
		bSeg.addActionListener(e -> app.trocarPara(new TelaSeguindo(app, perfil), "seguindo"));

		stats.add(bSeg);
		centro.add(stats);

		if(!perfil.getString("nome").isBlank()) {
			centro.add(Box.createVerticalStrut(0));
			JLabel lblReal = new JLabel(perfil.getString("nome"));
			lblReal.setFont(new Font("SansSerif", Font.BOLD, 16));
			centro.add(lblReal);
		}

		if(!perfil.getString("bio").isBlank()) {
			centro.add(Box.createVerticalStrut(4));
			centro.add(new JLabel("<html>" + perfil.getString("bio") + "</html>"));
		}

		header.add(centro, BorderLayout.CENTER);

		return header;
	}

	private JComponent criarTopo() {
		JPanel top = new JPanel(new BorderLayout());
		top.setBackground(Color.WHITE);
		top.setBorder(new EmptyBorder(8, 12, 8, 12));

		JButton back = new JButton("<-");
		back.setFocusPainted(false);
		back.addActionListener(e -> app.mostrarTela("feed"));

		top.add(back, BorderLayout.WEST);

		JLabel lbl = new JLabel(perfil.getString("usuario"), SwingConstants.CENTER);
		lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
		top.add(lbl, BorderLayout.CENTER);

		return top;
	}

	private JScrollPane criarGradePosts() {
		JPanel grid = new JPanel(new GridLayout(0, 3, 8, 8));
		grid.setBackground(Color.WHITE);
		grid.setBorder(new EmptyBorder(12, 12, 12, 12));

		ConexaoMongo.POSTS.find(eq("usuario_id", perfil.getObjectId("_id"))).sort(descending("criado_em"))
						.forEach(p -> {
							JLabel thumb = new JLabel(loadPostIcon(p));
							thumb.setHorizontalAlignment(SwingConstants.CENTER);
							thumb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							thumb.addMouseListener(new java.awt.event.MouseAdapter() {
								@Override
								public void mouseClicked(java.awt.event.MouseEvent e) {
									new DialogPost(app, p).setVisible(true);
								}
							});
							grid.add(thumb);
						});

		JScrollPane scroll = new JScrollPane(
						grid,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		return scroll;
	}

	private ImageIcon loadPostIcon(Document p) {
		try {

			Binary bin = p.get("imagem", Binary.class);

			BufferedImage img;

			if(bin != null) {
				img = ImageIO.read(new ByteArrayInputStream(bin.getData()));

			}else{
				img = ImageIO.read(new File(p.getString("caminho_imagem")));
			}

			Image scaled = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);

			return new ImageIcon(scaled);

		}catch(Exception e) {

			BufferedImage placeHolder = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);

			return new ImageIcon(placeHolder);
		}
	}

	private ImageIcon loadAvatarIcon() {
		try {

			Binary bin = perfil.get("foto_perfil_binario", Binary.class);

			BufferedImage img;
			if(bin != null) {
				img = ImageIO.read(new ByteArrayInputStream(bin.getData()));
			}else{
				img = ImageIO.read(new File(PATH_AVATAR_FALLBACK));
			}
			Image scaled = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			return new ImageIcon(scaled);

		}catch(Exception e) {
			BufferedImage vazio = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

			return new ImageIcon(vazio);
		}
	}


	private static String htmlStat(long n, String label) {
		return "<html><b>" + n + "</b><br> " + label + "</html>";
	}

	private long contarPosts() {
		return ConexaoMongo.POSTS.countDocuments(eq("usuario_id", perfil.getObjectId("_id")));
	}

	private long contarSeguidores() {
		return ConexaoMongo.FOLLOWS.countDocuments(eq("seguindo_id", perfil.getObjectId("_id")));
	}

	private long contarSeguindo() {
		return ConexaoMongo.FOLLOWS.countDocuments(eq("seguidor_id", perfil.getObjectId("_id")));
	}

	private boolean estaSeguindo() {
		return ConexaoMongo.FOLLOWS.find(and(
						eq("seguidor_id", app.getUsuarioAtual().getObjectId("_id")),
						eq("seguindo_id", perfil.getObjectId("_id"))
		)).first() != null;
	}

	private void alternarSeguir() {
		ObjectId me = app.getUsuarioAtual().getObjectId("_id");
		ObjectId alvo = perfil.getObjectId("_id");

		if(estaSeguindo()) {
			ConexaoMongo.FOLLOWS.deleteOne(
							and(
											eq("seguidor_id", me),
											eq("seguindo_id", alvo)
							)
			);
		}else{
			ConexaoMongo.FOLLOWS.insertOne(
							new Document()
											.append("seguidor_id", me)
											.append("seguindo_id", alvo)
											.append("criado_em", new Date())
			);
		}
	}
}
