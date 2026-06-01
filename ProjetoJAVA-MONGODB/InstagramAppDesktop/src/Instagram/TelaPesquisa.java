package Instagram;

import org.bson.Document;
import org.bson.types.Binary;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.*;


public class TelaPesquisa extends JPanel {

	private static final String PATH_AVATAR_FALLBACK = "/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg";
	private final InstagramApp app;
	private final JTextField campo = new JTextField(20);
	private final JPanel resultados = new JPanel();

	public TelaPesquisa(InstagramApp app) {
		this.app = app;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		add(criarTopo(), BorderLayout.NORTH);
		add(criarBody(), BorderLayout.CENTER);

		resultados.add(aviso("Digite para procurar e clique em \"Buscar\" para encontrar usuários."));
	}

	private JLabel aviso(String texto) {
		JLabel lbl = new JLabel(texto);
		lbl.setBorder(new EmptyBorder(20, 0, 20, 0));

		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		return lbl;
	}

	private JComponent criarBody() {
		resultados.setLayout(new BoxLayout(resultados, BoxLayout.Y_AXIS));
		resultados.setBackground(Color.WHITE);
		resultados.setBorder(new EmptyBorder(8, 12, 8, 12));

		JScrollPane scroll = new JScrollPane(resultados, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		return scroll;
	}

	private JComponent criarLinha(Document u) {
		JPanel linha = new JPanel(new BorderLayout());
		linha.setLayout(new BoxLayout(linha, BoxLayout.X_AXIS));
		linha.setMaximumSize(new Dimension(600, 64));
		linha.setPreferredSize(new Dimension(600, 64));
		linha.setBackground(Color.WHITE);
		linha.setBorder(new EmptyBorder(6, 6, 6, 6));
		linha.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel avatar = new JLabel();
		avatar.setPreferredSize(new Dimension(48, 48));

		Binary bin = u.get("foto_perfil_binario", Binary.class);

		BufferedImage img;

		try {

			if(bin != null) {
				img = ImageIO.read(new ByteArrayInputStream(bin.getData()));
			}else{
				img = ImageIO.read(new File(PATH_AVATAR_FALLBACK));
			}

		}catch(Exception e) {
			img = new BufferedImage(48, 48, BufferedImage.TYPE_INT_RGB);
		}

		Image thumb = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);

		avatar.setIcon(new ImageIcon(thumb));
		linha.add(avatar);
		linha.add(Box.createRigidArea(new Dimension(12, 0)));

		String usuario = u.getString("usuario");
		String nome = u.getString("nome");

		JPanel textos = new JPanel();
		textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
		textos.setOpaque(false);

		JLabel lblUsuario = new JLabel(usuario);
		lblUsuario.setFont(lblUsuario.getFont().deriveFont(Font.BOLD));

		textos.add(lblUsuario);

		if(!nome.isBlank()) {
			JLabel lblNome = new JLabel(nome);
			lblNome.setFont(lblNome.getFont().deriveFont(Font.PLAIN, 12f));
		}

		linha.add(textos);
		linha.add(textos);
		linha.add(Box.createHorizontalGlue());

		if(!u.getObjectId("_id").equals(app.getUsuarioAtual().getObjectId("_id"))) {
			JButton btnVer = new JButton("Ver Perfil");
			btnVer.setFocusable(false);

			btnVer.addActionListener(e -> {
				app.setPerfilVisualizado(u);
				app.mostrarTela("perfil");
			});

			linha.add(btnVer);
		}
		linha.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));

		return linha;

	}

	private JComponent criarTopo() {
		JPanel topo = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
		topo.setBackground(Color.WHITE);


		JButton voltar = new JButton("<- Feed");
		voltar.setFocusable(false);
		voltar.addActionListener(e -> app.mostrarTela("feed"));

		topo.add(voltar);
		topo.add(new JLabel("Pesquisar usuários"));
		topo.add(campo);

		JButton buscar = new JButton("Buscar");
		buscar.setFocusable(false);
		buscar.addActionListener(e -> pesquisar());

		topo.add(buscar);

		return topo;
	}

	private void pesquisar() {
		resultados.removeAll();

		String q = campo.getText().trim();

		if(q.isBlank()) {
			resultados.add(aviso("Digite algo para procurar."));
		}else{
			var achados = ConexaoMongo.USUARIOS.find(
							or(
											regex("usuario", ".*" + q + ".*", "i"),
											regex("nome", ".*" + q + ".*", "i")
							)
			).into(new ArrayList<>());

			if(achados.isEmpty()) {
				resultados.add(aviso("Nenhum resultado encontrado."));
			}else{
				for(Document u : achados) {
					resultados.add(criarLinha(u));
					resultados.add(Box.createVerticalStrut(8));
				}
			}
		}

		resultados.revalidate();
		resultados.repaint();
	}

}
