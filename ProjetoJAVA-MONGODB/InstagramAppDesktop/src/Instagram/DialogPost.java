package Instagram;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class DialogPost extends JDialog {

	private static final int IMG_MAX = 420;
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private final InstagramApp app;
	private Document post;
	private JButton btnCurtir;
	private AtomicBoolean jaCurtiu;

	public DialogPost(InstagramApp app, Document postOriginal) {
		super(app, "Publicação", true);
		this.app = app;
		this.post = ConexaoMongo.POSTS.find(
						eq("_id", postOriginal.getObjectId("_id"))
		).first();

		setSize(880, 600);
		setResizable(false);
		setLocationRelativeTo(app);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		add(criarSplit());
	}

	private JSplitPane criarSplit() {

		JLabel foto = new JLabel(redimensionada());
		foto.setHorizontalAlignment(SwingConstants.CENTER);
		foto.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));

		JSplitPane split = new JSplitPane(
						JSplitPane.HORIZONTAL_SPLIT,
						foto,
						criarLateral()
		);

		split.setDividerLocation(IMG_MAX);
		split.setEnabled(false);

		return split;
	}

	private JPanel criarLateral() {
		Document autor = ConexaoMongo.USUARIOS
						.find(eq(
										"_id", post.getObjectId("usuario_id")))
						.first();

		JLabel avatar = new JLabel(UtilImagem.imageEscalada(
						autor.getString("foto_perfil").isBlank()
										? "padrao.jpg"
										: autor.getString("foto_perfil"), 32, 32)
		);

		JLabel lblNome = new JLabel(autor.getString("usuario"));
		lblNome.setFont(lblNome.getFont().deriveFont(Font.BOLD));
		lblNome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNome.addMouseListener(app.irParaPerfil(autor));

		JPanel cab = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));

		cab.add(avatar);
		cab.add(lblNome);
		cab.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		JPanel feed = new JPanel();
		feed.setLayout(new BoxLayout(feed, BoxLayout.Y_AXIS));
		feed.setBackground(Color.WHITE);

		String legenda = post.getString("legenda");

		if(legenda != null && !legenda.isBlank()) {
			feed.add(bloco("<b>" + autor.getString("usuario") + "</b>" + legenda));
		}

		renderizarComentarios(feed);

		JScrollPane scroll = new JScrollPane(
						feed,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

		Object meuId = app.getUsuarioAtual().getObjectId("_id");

		List<ObjectId> curtidores = post.getList("curtidores", ObjectId.class);

		boolean inicioCurtir = curtidores != null && curtidores.contains(meuId);

		int inicioCount = Math.max(0, post.getInteger("curtidas", 0));

		jaCurtiu = new AtomicBoolean(inicioCurtir);

		btnCurtir = new JButton(jaCurtiu.get() ? "C" : "c " + inicioCount);
		btnCurtir.setFocusPainted(false);
		btnCurtir.addActionListener(e -> toogleCurtir(meuId));

		JPanel ac = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
		ac.setBorder(new MatteBorder(1, 0, 1, 0, new Color(230, 230, 230)));
		ac.add(btnCurtir);

		JTextField campo = new JTextField();

		JButton enviar = new JButton("Enviar");
		enviar.setFocusPainted(false);
		enviar.addActionListener(e -> {
			String texto = campo.getText().trim();

			if(texto.isBlank())
				return;

			Document novo = new Document("_id", new ObjectId())
							.append("usuarios_id", meuId)
							.append("texto", texto)
							.append("criado_em", new Date());

			ConexaoMongo.POSTS.updateOne(
							eq("_id", post.getObjectId("_id")),
							push("comentarios", novo)
			);

			campo.setText("");
			recarregar();
		});

		JPanel enviarBox = new JPanel(new BorderLayout());
		enviarBox.setBorder(new EmptyBorder(6, 6, 6, 6));
		enviarBox.add(campo, BorderLayout.CENTER);
		enviarBox.add(enviar, BorderLayout.EAST);

		JPanel lado = new JPanel(new BorderLayout());
		lado.setPreferredSize(new Dimension(440, IMG_MAX));
		lado.add(cab, BorderLayout.NORTH);
		lado.add(scroll, BorderLayout.CENTER);
		lado.add(ac, BorderLayout.SOUTH);

		JPanel col = new JPanel(new BorderLayout());
		col.add(lado, BorderLayout.CENTER);
		col.add(enviarBox, BorderLayout.SOUTH);

		return col;
	}

	private JLabel bloco(String html) {
		JLabel lbl = new JLabel("<html>" + html + "</html>");
		lbl.setBorder(new EmptyBorder(6, 8, 6, 8));

		return lbl;
	}

	private void renderizarComentarios(JPanel feed) {
		feed.removeAll();

		List<Document> comentarios = post.getList("comentarios", Document.class, java.util.Collections.emptyList());

		for(Document comentario : comentarios) {
			ObjectId autorComentarioId = comentario.getObjectId("usuario_id");

			if(autorComentarioId == null) {
				autorComentarioId = comentario.getObjectId("usuarios_id");
			}

			Document au = ConexaoMongo.USUARIOS.find(
							eq("_id", autorComentarioId)

			).first();

			if(au == null) {
				continue;
			}

			String fotoPerfil = au.getString("foto_perfil");
			if(fotoPerfil == null || fotoPerfil.isBlank()) {
				fotoPerfil = "/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg";
			}

			JPanel linha = new JPanel();
			linha.setLayout(new BoxLayout(linha, BoxLayout.X_AXIS));
			linha.setOpaque(false);
			linha.setBorder(new EmptyBorder(2, 8, 2, 8));

			JLabel avatar = new JLabel(UtilImagem.imageEscalada(fotoPerfil, 28, 28));
			avatar.setAlignmentY(Component.TOP_ALIGNMENT);
			linha.add(avatar);

			linha.add(Box.createRigidArea(new Dimension(6, 0)));

			JPanel conteudo = new JPanel();
			conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
			conteudo.setOpaque(false);
			conteudo.setAlignmentY(Component.TOP_ALIGNMENT);

			JPanel primeiraLinha = new JPanel();
			primeiraLinha.setLayout(new BoxLayout(primeiraLinha, BoxLayout.X_AXIS));
			primeiraLinha.setOpaque(false);
			primeiraLinha.setAlignmentX(Component.LEFT_ALIGNMENT);

			JLabel txt = new JLabel(
							"<html><b>" + au.getString("usuario") + "</b> " +
											comentario.getString("texto") + "</html>"
			);
			txt.setAlignmentY(Component.CENTER_ALIGNMENT);
			primeiraLinha.add(txt);

			if(au.getObjectId("_id").equals(app.getUsuarioAtual().getObjectId("_id"))) {

				primeiraLinha.add(Box.createRigidArea(new Dimension(4, 0)));

				Dimension tamanhoBotao = new Dimension(62, 22);

				JButton edt = new JButton("Editar");
				edt.setPreferredSize(tamanhoBotao);
				edt.setMinimumSize(tamanhoBotao);
				edt.setMaximumSize(tamanhoBotao);
				edt.setMargin(new Insets(1, 3, 1, 3));
				edt.setFocusable(false);
				edt.setAlignmentY(Component.CENTER_ALIGNMENT);
				edt.addActionListener(e -> editarComentario(comentario));

				primeiraLinha.add(edt);

				JButton del = new JButton("Excluir");
				del.setPreferredSize(tamanhoBotao);
				del.setMinimumSize(tamanhoBotao);
				del.setMaximumSize(tamanhoBotao);
				del.setMargin(new Insets(1, 3, 1, 3));
				del.setFocusable(false);
				del.setAlignmentY(Component.CENTER_ALIGNMENT);
				del.addActionListener(e -> excluirComentario(comentario));

				primeiraLinha.add(del);
			}

			conteudo.add(primeiraLinha);

			Date criadoEm = comentario.getDate("criado_em");
			String dataFormatada = criadoEm == null ? "" : DATE_FMT.format(criadoEm);

			JLabel data = new JLabel("<html><span style='color:gray; font-size: 12px'>" + dataFormatada + "</span></html>");
			data.setAlignmentX(Component.LEFT_ALIGNMENT);
			conteudo.add(data);

			linha.add(conteudo);

			feed.add(Box.createVerticalStrut(4));

			feed.add(linha);

		}

		feed.revalidate();
		feed.repaint();
	}

	private void editarComentario(Document comentario) {
		String atual = comentario.getString("texto");
		String novo = JOptionPane.showInputDialog(this, "Editar comentario", atual);

		if(novo == null || novo.trim().isEmpty() || novo.equals(atual)) {
			return;
		}

		ConexaoMongo.POSTS.updateOne(
						and(
										eq("_id", post.getObjectId("_id")),
										eq("comentarios._id", comentario.getObjectId("_id"))
						),
						set("comentarios.$.texto", novo.trim())
		);

		recarregar();
	}

	private void excluirComentario(Document comentario) {
		int op = JOptionPane.showConfirmDialog(this,
						"Excluir este comentário",
						"Confirmar",
						JOptionPane.YES_NO_OPTION);

		if(op != JOptionPane.YES_OPTION) {
			return;
		}

		ConexaoMongo.POSTS.updateOne(
						eq("_id", post.getObjectId("_id")),
						pull("comentarios", new Document("_id", comentario.getObjectId("_id")))
		);

		recarregar();
	}

	private void toogleCurtir(Object meuId) {
		post = ConexaoMongo.POSTS
						.find(
										eq("_id", post.getObjectId("_id"))
						).first();

		int c = Math.max(0, post.getInteger("curtidas", 0));

		if(jaCurtiu.get()) {
			if(c > 0) {
				ConexaoMongo.POSTS.updateOne(
								eq("_id", post.getObjectId("_id")),
								combine(
												pull("curtidores", meuId),
												inc("curtidas", -1)
								)
				);

				c--;
			}

			jaCurtiu.set(false);

		}else{
			ConexaoMongo.POSTS.updateOne(
							eq("_id", post.getObjectId("_id")),
							combine(
											addToSet("curtidores", meuId),
											inc("curtidas", 1)
							)
			);
			c++;
			jaCurtiu.set(true);
		}

		btnCurtir.setText((jaCurtiu.get() ? "💔 " : "❤️ ") + c);
	}

	private void recarregar() {
		ConexaoMongo.POSTS.find(
						eq("_id", post.getObjectId("_id"))
		).first();

		dispose();

		DialogPost novoDialog = new DialogPost(app, post);

		novoDialog.setVisible(true);
	}

	private ImageIcon redimensionada() {
		try {
			Binary bin = post.get("imagem", Binary.class);

			if(bin == null)
				throw new Exception("Sem imagem");

			BufferedImage img = ImageIO.read(new ByteArrayInputStream(bin.getData()));

			double f = (double) IMG_MAX / img.getHeight();

			int w = (int) (img.getWidth() * f);

			Image scaled = img.getScaledInstance(w, IMG_MAX, Image.SCALE_SMOOTH);

			return new ImageIcon(scaled);


		}catch(Exception e) {
			BufferedImage vazio = new BufferedImage(IMG_MAX, IMG_MAX, BufferedImage.TYPE_INT_ARGB);

			return new ImageIcon(vazio);
		}
	}

	private JButton botao(String texto, Runnable acao) {
		JButton b = new JButton(texto);

		b.setFocusPainted(false);
		b.setBorderPainted(true);
		b.setContentAreaFilled(true);
		b.setOpaque(true);
		b.addActionListener(e -> acao.run());

		return b;
	}
}
