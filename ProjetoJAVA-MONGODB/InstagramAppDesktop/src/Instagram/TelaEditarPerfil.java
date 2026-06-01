package Instagram;

import org.bson.Document;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Updates.combine;

public class TelaEditarPerfil extends JPanel {
	private final InstagramApp app;
	private Document perfil;
	private JTextField campoNome;
	private JTextArea campoBio;
	private JLabel lblAvatar;
	private byte[] avatarBytes;


	public TelaEditarPerfil(InstagramApp app, Document perfil) {
		this.app = app;
		this.perfil = perfil;

		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		add(barraTopo(), BorderLayout.NORTH);
		add(formulario(), BorderLayout.CENTER);
		add(rodape(), BorderLayout.SOUTH);
	}

	private JComponent rodape() {
		JPanel act = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
		act.setBackground(Color.WHITE);

		JButton cancelar = new JButton("Cancelar");
		cancelar.setFocusable(false);
		cancelar.addActionListener(e -> app.mostrarTela("perfil"));

		JButton salvar = new JButton("Salvar");
		salvar.setFocusable(false);
		salvar.addActionListener(e -> salvarPerfil());

		act.add(cancelar);
		act.add(salvar);

		return act;
	}

	private void salvarPerfil() {
		List<Bson> ups = new ArrayList<>();

		String novoNome = campoNome.getText().trim();
		String novoBio = campoBio.getText().trim();

		if(!novoNome.equals(perfil.getString("nome"))) {
			ups.add(set("nome", novoNome));
		}

		if(!novoBio.equals(perfil.getString("bio"))) {
			ups.add(set("bio", novoBio));
		}

		if(avatarBytes != null) {
			ups.add(set("foto_perfil_binario", new Binary(avatarBytes)));
		}

		if(ups.isEmpty()) {
			app.mostrarTela("perfil");
			return;
		}

		ConexaoMongo.USUARIOS.updateOne(
						eq("_id", perfil.getObjectId("_id")),
						combine(ups)
		);

		perfil = ConexaoMongo.USUARIOS.find(
						eq("_id", perfil.getObjectId("_id"))
		).first();

		app.setPerfilVisualizado(perfil);
		app.mostrarTela("perfil");

	}

	private JComponent formulario() {
		JPanel form = new JPanel(new GridBagLayout());
		form.setBorder(new EmptyBorder(20, 20, 20, 20));
		form.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;

		form.add(new JLabel("Avatar:"), gbc);

		lblAvatar = new JLabel();
		lblAvatar.setPreferredSize(new Dimension(100, 100));

		carregarAvatar();

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;

		form.add(lblAvatar, gbc);

		JButton escolher = new JButton("Escolher...");
		escolher.setFocusable(false);
		escolher.addActionListener(e -> escolherAvatar());

		gbc.gridx = 2;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		form.add(escolher, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;

		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		form.add(new JLabel("Nome:"), gbc);

		campoNome = new JTextField(perfil.getString("nome"));

		gbc.gridx = 1;
		gbc.weightx = 2;

		form.add(campoNome, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;

		form.add(new JLabel("Bio"), gbc);

		campoBio = new JTextArea(perfil.getString("bio"), 4, 20);
		campoBio.setLineWrap(true);

		gbc.gridx = 1;
		gbc.gridwidth = 2;

		gbc.fill = GridBagConstraints.BOTH;

		form.add(new JScrollPane(campoBio), gbc);

		return form;
	}

	private void carregarAvatar() {
		BufferedImage img;

		Binary bin = perfil.get("foto_perfil_binario", Binary.class);

		try {

			if(bin != null) {

				img = ImageIO.read(new ByteArrayInputStream(bin.getData()));
			}else{
				img = ImageIO.read(new File("/Users/thiagopdo/Desktop/Projetos/ProjetoJAVA-MONGODB/InstagramAppDesktop/src/Imagens/padrao.jpg"));
			}

		}catch(Exception e) {
			img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		}

		Image thumb = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

		lblAvatar.setIcon(new ImageIcon(thumb));
	}

	private void escolherAvatar() {
		JFileChooser fc = new JFileChooser();

		if(fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		try {

			File f = fc.getSelectedFile();

			BufferedImage img = ImageIO.read(f);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ImageIO.write(img, "png", baos);

			avatarBytes = baos.toByteArray();

			Image thumb = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			lblAvatar.setIcon(new ImageIcon(thumb));

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,
							"Erro ao carregar imagem: " + e.getMessage(),
							"Erro",
							JOptionPane.ERROR_MESSAGE);
		}
	}


	private JComponent barraTopo() {
		JPanel top = new JPanel(new BorderLayout());
		top.setBorder(new EmptyBorder(8, 12, 8, 12));
		top.setBackground(Color.WHITE);

		JButton voltar = new JButton("<- voltar");
		voltar.addActionListener(e -> app.mostrarTela("perfil"));
		voltar.setFocusable(false);

		top.add(voltar, BorderLayout.WEST);

		JLabel lbl = new JLabel("Editar perfil", SwingConstants.CENTER);
		lbl.setFont(new Font("SansSerif", Font.BOLD, 16));

		top.add(lbl, BorderLayout.CENTER);

		return top;
	}
}
