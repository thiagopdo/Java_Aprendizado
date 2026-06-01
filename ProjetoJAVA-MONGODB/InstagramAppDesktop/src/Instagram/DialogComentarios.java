package Instagram;

import org.bson.BsonUndefined;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class DialogComentarios extends JDialog {
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private final InstagramApp app;
	private final Document post;
	private final JPanel lista = new JPanel();
	private final JScrollPane scroll;

	public DialogComentarios(InstagramApp app, Document post) {
		super(app, "Comentários", true);

		this.app = app;
		this.post = post;

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(520, 560);

		setLocationRelativeTo(app);

		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
		lista.setBackground(Color.WHITE);

		scroll = new JScrollPane(lista,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

		JTextField campo = new JTextField(24);

		JButton send = new JButton("⤴️");
		send.setPreferredSize(new Dimension(80, 30));
		send.setFocusable(false);

		send.addActionListener(e -> {
			String txt = campo.getText().trim();

			if(txt.isBlank())
				return;

			Document novo = new Document("_id", new ObjectId())
							.append("usuario_id", app.getUsuarioAtual().getObjectId("_id"))
							.append("texto", txt)
							.append("criado_em", new Date());

			ConexaoMongo.POSTS.updateOne(
							eq("_id", post.getObjectId("_id")),
							push("comentarios", novo));

			campo.setText("");

			recarregar();
		});

		JPanel rodape = new JPanel(new BorderLayout());
		rodape.setBorder(new EmptyBorder(6, 6, 6, 6));
		rodape.add(campo, BorderLayout.CENTER);
		rodape.add(send, BorderLayout.EAST);

		add(scroll, BorderLayout.CENTER);
		add(rodape, BorderLayout.SOUTH);

		recarregar();
	}

	private void recarregar() {
		lista.removeAll();

		List<Document> comentarios = ConexaoMongo.POSTS.find(
						eq("_id", post.getObjectId("_id"))).first().getList("comentarios", Document.class, java.util.Collections.emptyList());

		if(comentarios.isEmpty()) {
			JLabel vazio = new JLabel("Nenhum comentário ainda.");
			vazio.setAlignmentX(Component.CENTER_ALIGNMENT);
			vazio.setBorder(new EmptyBorder(20, 0, 20, 0));
			lista.add(vazio);
		}else{
			for(Document c : comentarios) {
				lista.add(item(c));
			}
		}

		lista.revalidate();
		lista.repaint();
		SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum()));
	}

	private JComponent item(Document c) {
		Document au = ConexaoMongo.USUARIOS.find(eq("_id", c.getObjectId("usuario_id"))).first();

		ImageIcon avatarIcon = carregarAvatarIcone(au, 36, 36);

		JLabel avatar = new JLabel(avatarIcon);
		String html = "<html><body style='font-size: 12px;'>" + "<b>" + au.getString("usuario") + "</b>" + " <span style='color:gray'>* " + DATE_FMT.format(c.getDate("criado_em")) + "</span><br>" + c.getString("texto").replace("\n", "<br>") + "</body></html>";

		JLabel bolha = new JLabel(html);
		bolha.setOpaque(true);
		bolha.setBackground(new Color(245, 245, 245));
		bolha.setBorder(new EmptyBorder(6, 8, 6, 8));

		JPanel centro = new JPanel();

		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setOpaque(false);
		centro.add(avatar);
		centro.add(Box.createRigidArea(new Dimension(8, 0)));

		centro.add(bolha);
		centro.add(Box.createHorizontalGlue());

		Box col = Box.createVerticalBox();
		col.add(centro);

		if(au.getObjectId("_id").equals(app.getUsuarioAtual().getObjectId("_id"))) {
			JButton edt = new JButton("Editar");
			JButton del = new JButton("Excluir");

			edt.setMargin(new Insets(2, 6, 2, 6));
			del.setMargin(new Insets(2, 6, 2, 6));

			edt.addActionListener(e -> editarComentarios(c));

			del.addActionListener(e -> excluirComentario(c));

			JPanel ac = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));

			ac.setOpaque(false);
			ac.add(edt);
			ac.add(del);

			ac.setAlignmentX(Component.RIGHT_ALIGNMENT);

			col.add(ac);

		}

		JPanel linha = new JPanel(new BorderLayout());
		linha.setOpaque(false);
		linha.setBorder(new EmptyBorder(4, 10, 8, 10));
		linha.add(col, BorderLayout.CENTER);

		return linha;

	}

	private void excluirComentario(Document c) {
		if(JOptionPane.showConfirmDialog(this,
						"Excluir este comentário?",
						"Confirmar",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}

		ConexaoMongo.POSTS.updateOne(
						eq("_id", post.getObjectId("_id")),
						pull("comentarios", new Document("_id", c.getObjectId("_id")))
		);

		recarregar();

	}

	private void editarComentarios(Document c) {
		String novo = JOptionPane.showInputDialog(this,
						"Editar Comentário",
						c.getString("texto"));

		if(novo == null || novo.trim().isBlank()) {
			return;
		}


		ConexaoMongo.POSTS.updateOne(and(
										eq("_id", post.getObjectId("_id")),
										elemMatch("comentarios", eq("_id", c.getObjectId("_id")))),
						set("comentarios.$.texto", novo.trim()
						));

		recarregar();
	}

	private ImageIcon carregarAvatarIcone(Document usuario, int w, int h) {
		try {

			Binary bin = usuario.get("foto_perfil_binario", Binary.class);

			BufferedImage img;

			if(bin != null) {
				img = ImageIO.read(new ByteArrayInputStream(bin.getData()));

			}else if(!usuario.getString("foto_perfil").isBlank()) {
				img = ImageIO.read(new File(usuario.getString("foto_perfil")));

			}else{
				img = ImageIO.read(new File("/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg"));
			}

			Image thumb = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

			return new ImageIcon(thumb);

		}catch(Exception e) {
			BufferedImage b = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			return new ImageIcon(b);
		}
	}
}
