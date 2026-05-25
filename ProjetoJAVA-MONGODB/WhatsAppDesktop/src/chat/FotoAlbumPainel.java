package chat;


import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.Binary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ArrayList.*;
import java.util.List;

import static chat.ChatApp.database;
import static com.mongodb.client.model.Filters.eq;

public class FotoAlbumPainel extends JPanel {
	public Document docFoto;

	private Usuario usuarioAtual;
	private Runnable aoExcluirCallback;

	public FotoAlbumPainel(Document df, Usuario usuarioAtual, Runnable aoExcluirCallback) {
		this.docFoto = df;
		this.usuarioAtual = usuarioAtual;
		this.aoExcluirCallback = aoExcluirCallback;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(150, 150));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		JLabel lblImagem = new JLabel();
		lblImagem.setHorizontalAlignment(JLabel.CENTER);

		Binary bin = (Binary) docFoto.get("photoData");

		byte[] bytes = (bin != null) ? bin.getData() : null;

		ImageIcon icon = ChatApp.carregarImagemOuPadrao(bytes, 100, 100);

		lblImagem.setIcon(icon);
		lblImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblImagem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new JanelaFoto(docFoto, usuarioAtual).setVisible(true);
			}
		});

		add(lblImagem, BorderLayout.CENTER);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(new Color(220, 50, 50));
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setOpaque(true);
		btnExcluir.addActionListener(e -> {
			int opt = JOptionPane.showConfirmDialog(FotoAlbumPainel.this,
							"Deseja excluir esta foto?",
							"Excluir foto",
							JOptionPane.YES_NO_OPTION);

			if(opt == JOptionPane.YES_OPTION) {
				MongoCollection<Document> photos = database.getCollection("photos");

				photos.deleteOne(eq("_id", docFoto.getObjectId("_id")));

				JOptionPane.showMessageDialog(FotoAlbumPainel.this, "Foto excluída com sucesso.");

				if(this.aoExcluirCallback != null) {
					this.aoExcluirCallback.run();
				}
			}
		});

		add(btnExcluir, BorderLayout.SOUTH);

	}

	public static class JanelaFoto extends JFrame {
		private Document fotoDoc;
		private Usuario usuarioLogado;
		private final JLabel lblImagem;
		private JLabel lblLikes;
		private final JLabel lblComments;
		private final JButton btnLike;
		private final JButton btnVerComentarios;
		private final Timer timerAtualizar;


		public JanelaFoto(Document fotoDoc, Usuario usuarioLogado) {
			this.fotoDoc = fotoDoc;
			this.usuarioLogado = usuarioLogado;
			setTitle("Foto");
			setSize(400, 600);
			setLocationRelativeTo(null);
			setLayout(new BorderLayout());

			lblImagem = new JLabel();
			lblImagem.setHorizontalAlignment(JLabel.CENTER);
			add(lblImagem, BorderLayout.NORTH);

			JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

			btnLike = new JButton("Like");
			lblLikes = new JLabel("0 likes");

			lblComments = new JLabel("0 commentários");

			painelSuperior.add(btnLike);
			painelSuperior.add(lblLikes);
			painelSuperior.add(Box.createHorizontalStrut(20));
			painelSuperior.add(lblComments);

			add(painelSuperior, BorderLayout.CENTER);

			btnVerComentarios = new JButton("Ver Comentários");

			JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));

			painelInferior.add(btnVerComentarios);

			add(painelInferior, BorderLayout.SOUTH);

			carregarFoto();

			atualizarLikesComentarios();

			timerAtualizar = new Timer(2000, e -> {
				recarregarFotoDoBanco();
				atualizarLikesComentarios();
			});

			timerAtualizar.start();

			btnLike.addActionListener(e -> {
				curtirOuDescurtir();
			});

			btnVerComentarios.addActionListener(e -> {
				new JanelaComentarios(fotoDoc, usuarioLogado).setVisible(true);
			});

			addWindowFocusListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					timerAtualizar.stop();
				}
			});
		}

		private void recarregarFotoDoBanco() {
			MongoCollection<Document> photos = database.getCollection("photos");

			Document atual = photos.find(eq("_id", fotoDoc.getObjectId("_id"))).first();
			if(atual != null) {
				this.fotoDoc = atual;
			}
		}

		private void carregarFoto() {
			Binary bin = (Binary) this.fotoDoc.get("photoData");

			byte[] bytes = (bin != null) ? bin.getData() : null;

			ImageIcon icon = ChatApp.carregarImagemOuPadrao(bytes, 400, 400);

			lblImagem.setIcon(icon);
		}

		private void atualizarLikesComentarios() {
			List<String> likes = (List<String>) this.fotoDoc.get("likes");

			if(likes == null)
				likes = new ArrayList<>();

			lblLikes.setText(likes.size() + " likes");

			if(likes.contains(usuarioLogado.email)) {
				btnLike.setText("Descutir");
			}else{
				btnLike.setText("Like");
			}

			List<Document> comments = (List<Document>) this.fotoDoc.get("comments");

			if(comments == null)
				comments = new ArrayList<>();

			lblComments.setText(comments.size() + " comments000");
		}

		private void curtirOuDescurtir() {
			MongoCollection<Document> photos = database.getCollection("photos");

			List<String> likes = (List<String>) this.fotoDoc.get("likes");

			if(likes == null)
				likes = new ArrayList<>();

			if(!likes.contains(usuarioLogado.email)) {
				likes.add(usuarioLogado.email);
			}else{
				likes.remove(usuarioLogado.email);
			}

			this.fotoDoc.put("likes", likes);

			photos.updateOne(eq("_id", this.fotoDoc.getObjectId("_id")), new Document("$set", new Document("likes", likes)));

			atualizarLikesComentarios();
		}
	}

	private static class ComentarioItem {
		private String nomeExibicao;
		private String comentarEmail;
		private String comentario;

		public ComentarioItem(String nomeExibicao, String comentarEmail, String comentario) {
			this.nomeExibicao = nomeExibicao;
			this.comentarEmail = comentarEmail;
			this.comentario = comentario;
		}

		@Override
		public String toString() {
			return nomeExibicao + ": " + comentario;
		}
	}

	private static class JanelaComentarios extends JFrame {
		private Document fotoDoc;
		private Usuario user;
		private DefaultListModel<ComentarioItem> modeloComentarios;
		private JList<ComentarioItem> listaComentarios;

		private JTextField campoComentario;
		private JButton btnComentar;

		private Timer timer;

		public JanelaComentarios(Document fotoDoc, Usuario user) {
			this.fotoDoc = fotoDoc;
			this.user = user;

			setTitle("Comentários");
			setSize(500, 700);
			setLocationRelativeTo(null);
			setLayout(new BorderLayout());

			JLabel lblTitulo = new JLabel("Comentários", SwingConstants.CENTER);

			lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
			add(lblTitulo, BorderLayout.NORTH);

			modeloComentarios = new DefaultListModel<>();
			listaComentarios = new JList<>(modeloComentarios);

			listaComentarios.setCellRenderer(new RenderizadorComentario()
			);
			JScrollPane scroll = new JScrollPane(listaComentarios);
			add(scroll, BorderLayout.CENTER);

			JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.LEFT));

			campoComentario = new JTextField(20);

			btnComentar = new JButton("Comentar");

			painelSul.add(campoComentario);
			painelSul.add(btnComentar);

			add(painelSul, BorderLayout.SOUTH);

			timer = new Timer(2000, e -> carregarComentarios());

			timer.start();

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					timer.stop();
					super.windowClosing(e);
				}
			});

			listaComentarios.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						int index = listaComentarios.locationToIndex(e.getPoint());

						if(index >= 0) {
							excluirComentario(index);
						}
					}
				}
			});

			btnComentar.addActionListener(e -> adicionarComentario());

			carregarComentarios();

		}

		private void adicionarComentario() {
			String texto = campoComentario.getText().trim();

			if(texto.isEmpty())
				return;

			MongoCollection<Document> photos = database.getCollection("photos");
			Document atual = photos.find(eq("_id", fotoDoc.getObjectId("_id"))).first();

			if(atual == null) {
				JOptionPane.showMessageDialog(this,
								"Foto não encontrada",
								"Erro",
								JOptionPane.ERROR_MESSAGE);
				return;
			}

			fotoDoc = atual;

			List<Document> comments = (List<Document>) fotoDoc.get("comments");

			if(comments == null)
				comments = new ArrayList<>();

			Document novoComentario = new Document("commenter", user.email)
							.append("texto", texto);
			comments.add(novoComentario);

			fotoDoc.put("comments", comments);

			photos.updateOne(eq(
											"_id",
											fotoDoc.getObjectId("_id")),
							new Document("$set", new Document("comments", comments)));

			campoComentario.setText("");

			carregarComentarios();
		}

		private void excluirComentario(int index) {
			if(index < 0)
				return;
			ComentarioItem ci = modeloComentarios.get(index);

			String donoDaFoto = fotoDoc.getString("ownerEmail");

			boolean ehDono = donoDaFoto != null && donoDaFoto.equals(user.email);
			boolean ehAutor = ci.comentarEmail.equals(user.email);

			if(!ehDono && !ehAutor) {
				JOptionPane.showMessageDialog(this,
								"Você só pode excluir seus próprios comentários ou se for o dono da foto.",
								"Acesso negado",
								JOptionPane.WARNING_MESSAGE);
				return;
			}

			int opt = JOptionPane.showConfirmDialog(this,
							"Deseja excluir o comentário?",
							"Confirmação de exclusão",
							JOptionPane.YES_NO_OPTION);

			if(opt == JOptionPane.YES_OPTION) {
				MongoCollection<Document> photos = database.getCollection("photos");
				Document atual = photos.find(eq("_id", fotoDoc.getObjectId("_id"))).first();

				if(atual == null) {
					JOptionPane.showMessageDialog(this,
									"Foto não encontrada!",
									"Erro",
									JOptionPane.ERROR_MESSAGE);
					return;
				}

				fotoDoc = atual;

				List<Document> comments = (List<Document>) fotoDoc.get("comments");

				if(comments == null)
					comments = new ArrayList<>();

				Document paraRemover = null;

				for(Document d : comments) {
					if(d.getString("commenter").equals(ci.comentarEmail) && d.getString("texto").equals(ci.comentario)) {
						paraRemover = d;
						break;
					}
				}

				if(paraRemover != null) {
					comments.remove(paraRemover);
					fotoDoc.put("comments", comments);

					photos.updateOne(
									eq("_id", fotoDoc.getObjectId("_id")),
									new Document("$set", new Document("comments", comments))
					);

					carregarComentarios();
				}
			}

		}

		private void carregarComentarios() {
			modeloComentarios.clear();

			MongoCollection<Document> photos = database.getCollection("photos");

			Document atual = photos.find(eq("_id", fotoDoc.getObjectId("_id"))).first();

			if(atual == null) {
				JOptionPane.showMessageDialog(this,
								"Foto não encontrada no banco!",
								"Erro",
								JOptionPane.ERROR_MESSAGE);
				return;
			}

			fotoDoc = atual;

			List<Document> comments = (List<Document>) fotoDoc.get("comments");

			if(comments == null)
				comments = new ArrayList<>();

			for(Document cd : comments) {
				String commenterEmail = cd.getString("commenter");
				String commenterNome = buscarNomeUsuarioPorEmail(commenterEmail);
				String texto = cd.getString("texto");

				ComentarioItem ci = new ComentarioItem(commenterNome, commenterEmail, texto);

				modeloComentarios.addElement(ci);

			}
		}

		protected String buscarNomeUsuarioPorEmail(String commenterEmail) {
			MongoCollection<Document> colecao = database.getCollection("users");

			Document doc = colecao.find(eq("email", commenterEmail)).first();

			if(doc != null && doc.getString("nome") != null) {
				return doc.getString("nome");
			}

			return commenterEmail;
		}

	}

	private static class RenderizadorComentario implements ListCellRenderer<ComentarioItem> {

		@Override
		public Component getListCellRendererComponent(JList<? extends ComentarioItem> list, ComentarioItem value, int index, boolean isSelected, boolean cellHasFocus) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			panel.setBackground(isSelected ? new Color(220, 240, 220) : Color.WHITE);

			JLabel lbl = new JLabel("<html><b>" + value.nomeExibicao + ":</b> " + value.comentario + "</html>");
			panel.add(lbl, BorderLayout.CENTER);

			return panel;
		}
	}
}
