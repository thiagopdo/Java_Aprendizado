package Instagram;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class DialogChat extends JDialog {
	private static final long INTERVALO_ATUALIZADO = 2_000;
	private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("HH:mm");
	private static final Color COR_FUNDO_CHAT = Color.decode("#ECE5DD");
	private static final Color COR_BOLHA_MINHA = Color.decode("#DCF8C6");
	private static final Color COR_BOLHA_OUTRO = Color.WHITE;
	private static final Color COR_BOLHA_BOLHA = new Color(200, 200, 200);
	private static final MongoCollection<Document> COL_CHATS = ConexaoMongo.CHATS;
	private static final MongoCollection<Document> COL_MENSAGENS = ConexaoMongo.MENSAGENS;

	private final InstagramApp app;
	private final ObjectId idChat;
	private final JPanel painelMensagens = new JPanel();
	private final JScrollPane rolagem = new JScrollPane(painelMensagens);
	private final Timer atualizador;

	public DialogChat(InstagramApp app, Document outroUsuario) {
		super(app, "Chat com " + outroUsuario.getString("usuario"), false);
		this.app = app;
		this.idChat = obterOuCriarChat(
						app.getUsuarioAtual().getObjectId("_id"),
						outroUsuario.getObjectId("_id")
		);
		montarInterface();

		atualizador = new Timer((int) INTERVALO_ATUALIZADO, e -> carregarMensagens());

		atualizador.start();

		carregarMensagens();

	}

	private void carregarMensagens() {
		painelMensagens.removeAll();

		COL_MENSAGENS.find(
										eq("chat_id", idChat)
						).sort(ascending("criado_em"))
						.forEach(msg -> painelMensagens.add(criarLinha(msg)));

		painelMensagens.revalidate();
		painelMensagens.repaint();

		SwingUtilities.invokeLater(() -> rolagem.getVerticalScrollBar().setValue(rolagem.getVerticalScrollBar().getMaximum())
		);
	}

	private Component criarLinha(Document msg) {
		boolean minha = msg.getObjectId("autor_id")
						.equals(app.getUsuarioAtual().getObjectId("_id"));

		String html = "<html><body style='width:260px'font-family: sans-serif; font-size: 14px; word-wrap:break-word;'>"
						+ msg.getString("texto").replace("\n", "<br>")
						+ "<div style='text-align:right;font-size:10px;color:#808080'>"
						+ DATE_FMT.format(msg.getDate("criado_em"))
						+ "</body></html>";

		JLabel lbl = new JLabel(html);
		BolhaChat bolha = new BolhaChat(minha);
		bolha.setLayout(new BoxLayout(bolha, BoxLayout.Y_AXIS));
		bolha.add(lbl, BorderLayout.CENTER);
		bolha.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));

		JPanel linha = new JPanel(new FlowLayout(
						minha
										? FlowLayout.RIGHT
										: FlowLayout.LEFT, 10, 0
		));

		linha.setOpaque(false);
		linha.add(bolha);

		Dimension pref = linha.getPreferredSize();

		linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, pref.height));

		if(minha) {
			adicionarMenuContexto(bolha, msg);
		}

		return linha;
	}

	private void adicionarMenuContexto(JComponent alvo, Document msg) {
		alvo.addMouseListener(new MouseAdapter() {

			public void mostrar(MouseEvent e) {
				JPopupMenu pop = new JPopupMenu();
				JMenuItem miEditar = new JMenuItem("Editar");
				JMenuItem miExcluir = new JMenuItem("Excluir");

				miEditar.addActionListener(a -> {
					String novo = JOptionPane.showInputDialog(
									DialogChat.this,
									"Editar mensagem",
									msg.getString("texto")
					);

					if(novo != null && !novo.trim().isEmpty()) {
						COL_MENSAGENS.updateOne(
										eq("_id", msg.getObjectId("_id")),
										set("texto", novo.trim())
						);
						carregarMensagens();
					}
				});

				miExcluir.addActionListener(a -> {
					int op = JOptionPane.showConfirmDialog(
									DialogChat.this,
									"Excluir esta mensagem?",
									"Confirmar",
									JOptionPane.YES_NO_OPTION
					);

					if(op == JOptionPane.YES_OPTION) {
						COL_MENSAGENS.deleteOne(eq("_id", msg.getObjectId("_id")));
						carregarMensagens();
					}
				});

				pop.add(miEditar);
				pop.add(miExcluir);
				pop.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()) {
					mostrar(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					mostrar(e);
				}
			}
		});
	}

	private final class BolhaChat extends JPanel {
		private final boolean minha;

		BolhaChat(boolean minha) {
			this.minha = minha;
			setBackground(minha ? COR_BOLHA_MINHA : COR_BOLHA_OUTRO);

			setBorder(new EmptyBorder(8, 12, 8, 12));

			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setRenderingHint(
							RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON
			);

			int arc = 16;
			int w = getWidth();
			int h = getHeight();

			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, w - 8, h, arc, arc);

			Polygon rab = new Polygon();

			if(minha) {
				rab.addPoint(w - 8, h - 12);
				rab.addPoint(w - 8, h - 2);
				rab.addPoint(w, h - 2);
			}else{
				rab.addPoint(8, h - 12);
				rab.addPoint(8, h - 2);
				rab.addPoint(0, h - 2);
			}

			g2.fillPolygon(rab);
			g2.setColor(COR_BOLHA_BOLHA);
			g2.drawRoundRect(0, 0, w - 8, h, arc, arc);

			g2.dispose();

			super.paintComponent(g);
		}


		@Override
		public boolean isOpaque() {
			return false;
		}
	}

	private void montarInterface() {
		setSize(480, 560);
		setMinimumSize(new Dimension(400, 488));
		setLocationRelativeTo(app);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getContentPane().setBackground(COR_FUNDO_CHAT);

		setLayout(new BorderLayout());

		painelMensagens.setLayout(new BoxLayout(painelMensagens, BoxLayout.Y_AXIS));
		painelMensagens.setBackground(COR_FUNDO_CHAT);

		rolagem.setBorder(null);
		rolagem.getViewport().setOpaque(false);
		rolagem.getVerticalScrollBar().setUnitIncrement(16);

		add(rolagem, BorderLayout.CENTER);

		JTextField campoTexto = new JTextField();
		campoTexto.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JButton botaoEnviar = new JButton("Enviar");
		botaoEnviar.setPreferredSize(new Dimension(82, 34));
		botaoEnviar.addActionListener(e -> {
			String txt = campoTexto.getText().trim();

			if(!txt.isEmpty()) {
				enviar(txt);
				campoTexto.setText("");
			}
		});

		JPanel rodape = new JPanel(new BorderLayout(6, 6));
		rodape.setBackground(Color.WHITE);
		rodape.setBorder(new EmptyBorder(6, 6, 6, 6));
		rodape.add(campoTexto, BorderLayout.CENTER);
		rodape.add(botaoEnviar, BorderLayout.EAST);

		add(rodape, BorderLayout.SOUTH);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				atualizador.stop();
			}
		});
	}

	private void enviar(String txt) {
		COL_MENSAGENS.insertOne(
						new Document()
										.append("chat_id", idChat)
										.append("autor_id", app.getUsuarioAtual().getObjectId("_id"))
										.append("texto", txt)
										.append("criado_em", new Date())
		);


	}

	private ObjectId obterOuCriarChat(ObjectId id1, ObjectId id2) {
		Document chat = COL_CHATS.find(
						Filters.and(
										all("participantes", id1, id2),
										com.mongodb.client.model.Filters.size("participantes", 2)
						)
		).first();

		if(chat != null) {
			return chat.getObjectId("_id");
		}

		List<ObjectId> participantes = Arrays.asList(id1, id2);

		participantes.sort(null);

		Document novo = new Document("participantes", participantes)
						.append("criado_em", new Date());

		COL_CHATS.insertOne(novo);
		return novo.getObjectId("_id");
	}

}
