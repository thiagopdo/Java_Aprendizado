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


public class TelaSeguindo extends JPanel {
	private final InstagramApp app;
	private final Document perfil;
	private final JPanel lista = new JPanel();

	public TelaSeguindo(InstagramApp app, Document perfil) {
		this.app = app;
		this.perfil = perfil;
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);


		add(criarTopo(), BorderLayout.NORTH);
		add(criarCorpo(), BorderLayout.CENTER);

	}

	private JComponent criarTopo() {
		JPanel topo = new JPanel(new BorderLayout());
		topo.setBorder(new EmptyBorder(8, 12, 8, 12));
		topo.setBackground(Color.WHITE);

		JButton btnVoltar = new JButton("<-");
		btnVoltar.setFocusPainted(false);
		btnVoltar.addActionListener(e -> app.mostrarTela("perfil"));

		topo.add(btnVoltar, BorderLayout.WEST);

		JLabel lblTitulo = new JLabel("Seguindo", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));

		topo.add(lblTitulo, BorderLayout.CENTER);

		return topo;
	}

	private JComponent criarCorpo() {
		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
		lista.setBackground(Color.WHITE);
		lista.setBorder(new EmptyBorder(8, 0, 8, 0));

		recarregarLista();

		JScrollPane scroll = new JScrollPane(lista,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		return scroll;
	}

	private void recarregarLista() {
		lista.removeAll();

		var iter = ConexaoMongo.FOLLOWS.find(
						eq("seguidor_id", perfil.getObjectId("_id"))
		).iterator();

		if(!iter.hasNext()) {
			JLabel vazio = new JLabel("Você ainda não segue ninguém.", SwingConstants.CENTER);
			vazio.setBorder(new EmptyBorder(20, 0, 20, 0));
			vazio.setAlignmentX(Component.CENTER_ALIGNMENT);

			lista.add(vazio);
		}else{
			iter.forEachRemaining(f -> {
				ObjectId seguindoId = f.getObjectId("seguindo_id");
				lista.add(criarLinha(seguindoId));
				lista.add(Box.createVerticalStrut(6));
			});
		}

		lista.revalidate();
		lista.repaint();
	}

	private JPanel criarLinha(ObjectId seguindoId) {
		Document u = ConexaoMongo.USUARIOS.find(
						eq("_id", seguindoId)
		).first();

		ImageIcon icone;

		try {
			Binary bin = u.get("foto_perfil_binario", Binary.class);

			BufferedImage img = (bin != null)
							? ImageIO.read(new ByteArrayInputStream(bin.getData()))
							: ImageIO.read(new File("users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg"));

			Image thumb = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

			icone = new ImageIcon(thumb);

		}catch(Exception e) {
			icone = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		}

		JLabel lblAvatar = new JLabel(icone);
		JLabel lblNome = new JLabel(u.getString("usuario"));

		lblNome.setFont(lblNome.getFont().deriveFont(Font.BOLD, 14f));
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

		if(!seguindoId.equals(app.getUsuarioAtual().getObjectId("_id"))) {

			JButton btn = new JButton("Deixar de seguir");
			btn.setFocusPainted(false);
			btn.addActionListener(e -> {
				ConexaoMongo.FOLLOWS.deleteOne(
								and(
												eq("seguindo_id", app.getUsuarioAtual().getObjectId("_id")),
												eq("seguindo_id", seguindoId)
								)
				);
				recarregarLista();

				app.setPerfilVisualizado(perfil);
			});

			linha.add(btn);
		}

		return linha;
	}
}