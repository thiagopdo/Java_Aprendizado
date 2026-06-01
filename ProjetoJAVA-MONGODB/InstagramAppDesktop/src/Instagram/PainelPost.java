package Instagram;

import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class PainelPost extends JPanel {
	private static final int IMG_WIDTH = 760;
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	PainelPost(InstagramApp app, Document post) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(
										Color.LIGHT_GRAY, 1),
						new EmptyBorder(12, 12, 12, 12)
		));

		setBackground(Color.WHITE);

		Document autor = ConexaoMongo.USUARIOS.find(Filters.eq("_id",
						post.getObjectId("usuario_id"))).first();

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

		header.setOpaque(false);

		BufferedImage imgAvatar;

		try {

			Binary binAvatar = autor.get("foto_perfil_binario", Binary.class);

			if(binAvatar != null) {
				imgAvatar = ImageIO.read(new ByteArrayInputStream(binAvatar.getData()));

			}else{
				imgAvatar = ImageIO.read(new File("/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg"));
			}

		}catch(Exception e) {
			imgAvatar = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		}

		header.add(new JLabel(new ImageIcon(imgAvatar.getScaledInstance(32, 32, Image.SCALE_SMOOTH))));

		JLabel lblUser = new JLabel(autor.getString("usuario"));
		lblUser.setFont(lblUser.getFont().deriveFont(Font.BOLD));
		header.add(lblUser);

		header.add(Box.createHorizontalStrut(16));

		header.add(new JLabel((DATE_FMT.format(post.getDate("criado_em")))));

		add(header, BorderLayout.NORTH);

		JLabel lblFoto = new JLabel(postImage(post));
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFoto.setBorder(new EmptyBorder(12, 0, 12, 0));
		lblFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblFoto.addMouseListener(app.abrirPostModal(post));
		add(lblFoto, BorderLayout.CENTER);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
		footer.setOpaque(false);

		Object meuId = app.getUsuarioAtual().getObjectId("_id");

		List<ObjectId> curtidores = post.getList("curtidores", ObjectId.class);

		boolean inicCurtir = curtidores != null && curtidores.contains(meuId);

		AtomicBoolean jaCurtiu = new AtomicBoolean(inicCurtir);

		int curtirInit = post.getInteger("curtidas", 0);

		JButton btnCurtir = new JButton((jaCurtiu.get() ? " 👎" : " 👍") + curtirInit);
		btnCurtir.setFocusPainted(false);

		btnCurtir.addActionListener(e -> {
			int atual = contarCurtidas(post);
			if(jaCurtiu.get()) {
				if(atual > 0) {
					ConexaoMongo.POSTS.updateOne(Filters.eq("_id", post.getObjectId("_id")),
									combine(
													pull("curtidores",
																	meuId),
													inc("curtidas", -1)
									));
				}

				jaCurtiu.set(false);

				btnCurtir.setText("👍 " + (atual - 1));
			}else{
				ConexaoMongo.POSTS.updateOne(Filters.eq("_id", post.getObjectId("_id")),
								combine(
												addToSet("curtidores", meuId),
												inc("curtidas", 1)));

				jaCurtiu.set(true);
				btnCurtir.setText("👎 " + (atual + 1));
			}
		});

		footer.add(btnCurtir);

		List<Document> comentarios = post.getList("comentarios", Document.class);

		int totalCom = comentarios != null ? comentarios.size() : 0;

		JButton btnComentar = new JButton("💬 " + totalCom + " comentários");

		btnComentar.setFocusPainted(false);
		btnComentar.addActionListener(e -> {
			DialogComentarios dc = new DialogComentarios(app, post);

			dc.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosed(java.awt.event.WindowEvent ev) {
					Document postAtualizado = ConexaoMongo.POSTS.find(Filters.eq("_id", post.getObjectId("_id"))).first();

					List<Document> listaAtualComentarios = postAtualizado.getList("comentarios", Document.class);

					int novoTotal = listaAtualComentarios != null ? listaAtualComentarios.size() : 0;

					btnComentar.setText("💬 " + novoTotal + " comentários");
				}
			});

			dc.setVisible(true);
		});

		footer.add(btnComentar);
		add(footer, BorderLayout.SOUTH);

	}

	private ImageIcon postImage(Document post) {
		try {

			BufferedImage img;

			Object raw = post.get("imagem");

			if(raw instanceof Binary) {
				ByteArrayInputStream bais = new ByteArrayInputStream(((Binary) raw).getData());

				img = ImageIO.read(bais);
			}else{
				File arquivo = new File(post.getString("caminho_imagem"));
				img = ImageIO.read(arquivo);
			}

			double f = Math.min(
							(double) IMG_WIDTH / img.getWidth(),
							(double) IMG_WIDTH / img.getHeight());

			Image scaled = img.getScaledInstance(
							(int) (img.getWidth() * f),
							(int) (img.getHeight() * f),
							Image.SCALE_SMOOTH);

			return new ImageIcon(scaled);

		}catch(Exception e) {
			BufferedImage ph = new BufferedImage(IMG_WIDTH, IMG_WIDTH, BufferedImage.TYPE_INT_ARGB);
			return new ImageIcon(ph);
		}
	}

	private int contarCurtidas(Document post) {
		return ConexaoMongo.POSTS.find(Filters.eq("_id", post.getObjectId("_id"))).first().getInteger("curtidas", 0);
	}

}
