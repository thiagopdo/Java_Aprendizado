package Instagram;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;

import static com.mongodb.client.model.Filters.*;

public class TelaSeguidores extends JPanel {
	private final InstagramApp app;
	private final Document perfil;
	private final JPanel lista = new JPanel();

	public TelaSeguidores(InstagramApp app, Document perfil) {
		this.app = app;
		this.perfil = perfil;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		add(criarTopo(), BorderLayout.NORTH);
		add(criarCorpo(), BorderLayout.CENTER);

	}

	private JComponent criarCorpo() {
		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
		lista.setBackground(Color.WHITE);
		lista.setBorder(new EmptyBorder(8, 0, 8, 0));

		recarregarLista();

		JScrollPane scroll = new JScrollPane(lista, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		return scroll;
	}

	private void recarregarLista() {
		lista.removeAll();

		var iter = ConexaoMongo.FOLLOWS.find(
						eq("seguindo_id", perfil.getObjectId("_id"))
		).iterator();

		if(!iter.hasNext()) {
			JLabel vazio = new JLabel("Nenhum seguidor ainda.", SwingConstants.CENTER);

			vazio.setBorder(new EmptyBorder(20, 0, 20, 0));
			vazio.setAlignmentX(Component.CENTER_ALIGNMENT);

			lista.add(vazio);
		}else{
			iter.forEachRemaining(f -> {
				ObjectId seguidorId = f.getObjectId("seguidor_id");
				lista.add(criarLinha(seguidorId));
				lista.add(Box.createVerticalStrut(6));
			});

			lista.revalidate();
			lista.repaint();
		}
	}

	private JPanel criarLinha(ObjectId seguidorId) {
		Document u = ConexaoMongo.USUARIOS.find(
						eq("_id", seguidorId)
		).first();

		if(u == null) {
			return new JPanel(); // Ou retorne null e filtre na lista
		}

		ImageIcon icone;

		try {

			Binary bin = u.get("foto_perfil_binario", Binary.class);
			BufferedImage img = bin != null
							? ImageIO.read(new ByteArrayInputStream(bin.getData()))
							: ImageIO.read(new File("users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg"));

			Image thumb = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			icone = new ImageIcon(thumb);

		}catch(Exception e) {
			icone = new ImageIcon(
							new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB)
			);
		}

		JLabel lblAvatar = new JLabel(icone);
		JLabel lblNome = new JLabel(u.getString("usuario"));

		lblNome.setFont(lblNome.getFont().deriveFont(Font.BOLD));
		lblNome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				app.setPerfilVisualizado(u);
				app.mostrarTela("perfil");
			}
		});

		JPanel linha = new JPanel();
		linha.setLayout(new BoxLayout(linha, BoxLayout.X_AXIS));
		linha.setBackground(Color.WHITE);
		linha.setBorder(new EmptyBorder(6, 12, 6, 12));

		linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
		linha.add(lblAvatar);

		linha.add(Box.createRigidArea(new Dimension(12, 0)));
		linha.add(lblNome);

		linha.add(Box.createHorizontalGlue());

		if(!seguidorId.equals(app.getUsuarioAtual().getObjectId("_id"))) {
			JButton botao = new JButton(textoBotao(u));
			botao.setFocusable(false);
			botao.addActionListener(e -> {
								alternarSeguir(u);
								botao.setText(textoBotao(u));
								recarregarLista();
								app.setPerfilVisualizado(perfil);
							}
			);

			linha.add(botao);
		}
		return linha;
	}

	private String textoBotao(Document u) {
		return estaSeguindo(u) ? "Deixar de seguir" : "Seguir de volta";
	}

	private boolean estaSeguindo(Document u) {
		var filtro = and(
						eq("seguidor_id", app.getUsuarioAtual().getObjectId("_id")),
						eq("seguindo_id", u.getObjectId("_id"))
		);

		Document resultado = ConexaoMongo.FOLLOWS.find(filtro).first();
		return resultado != null;
	}

	private void alternarSeguir(Document u) {
		ObjectId me = app.getUsuarioAtual().getObjectId("_id");
		ObjectId alvo = u.getObjectId("_id");

		if(estaSeguindo(u)) {
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

	private JComponent criarTopo() {

		JPanel topo = new JPanel(new BorderLayout());
		topo.setBorder(new EmptyBorder(8, 12, 8, 12));
		topo.setBackground(Color.WHITE);

		JButton btnVoltar = new JButton("<-");
		btnVoltar.setFocusPainted(false);
		btnVoltar.addActionListener(e -> app.mostrarTela("perfil"));
		topo.add(btnVoltar, BorderLayout.WEST);

		JLabel lblTitulo = new JLabel("Seguidores", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

		topo.add(lblTitulo, BorderLayout.CENTER);

		return topo;
	}

}
