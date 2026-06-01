package Instagram;

import org.bson.Document;
import org.bson.types.Binary;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.mongodb.client.model.Updates.*;

public class TelaNovoPost extends JPanel {
	private final InstagramApp app;

	private final JTextField campoImg = new JTextField(24);
	private JTextArea areaLeg = new JTextArea(8, 24);
	private final JLabel previewLabel = new JLabel();
	private BufferedImage selectedImage;
	private static final int PREVIEW_WIDTH = 400;
	private static final int PREVIEW_HEIGHT = 400;

	public TelaNovoPost(InstagramApp app) {
		this.app = app;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		toolbar.setBackground(Color.WHITE);

		JButton voltar = new JButton("<- Feed");
		voltar.addActionListener(e -> app.mostrarTela("feed"));

		toolbar.add(voltar);
		toolbar.add(new JLabel("Novo Post"));

		add(toolbar, BorderLayout.NORTH);

		JPanel center = new JPanel(new BorderLayout(16, 0));
		center.setBorder(new EmptyBorder(12, 12, 12, 12));
		center.setBackground(Color.WHITE);

		JPanel form = new JPanel(new GridBagLayout());
		form.setBackground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JButton escolher = new JButton("Escolher...");
		escolher.addActionListener(e -> escolherImagem());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;

		form.add(new JLabel("Imagem:"), gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;

		form.add(campoImg, gbc);

		gbc.gridx = 2;
		gbc.weightx = 0;

		form.add(escolher, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;

		form.add(new JLabel("Legenda:"), gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;

		gbc.fill = GridBagConstraints.BOTH;

		form.add(new JScrollPane(areaLeg), gbc);

		gbc.gridwidth = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JButton postar = new JButton("Postar");
		postar.addActionListener(e -> postar());

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weightx = 0;

		form.add(postar, gbc);

		center.add(form, BorderLayout.CENTER);

		previewLabel.setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
		previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		previewLabel.setVerticalAlignment(SwingConstants.CENTER);
		previewLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		center.add(previewLabel, BorderLayout.EAST);

		add(center, BorderLayout.CENTER);
	}

	private void postar() {
		String caminho = campoImg.getText().trim();

		if(caminho.isBlank() || selectedImage == null) {
			JOptionPane.showMessageDialog(this, "Selecione uma imagem para postar.",
							"Imagem obrigatória",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(selectedImage, "png", baos);

			byte[] bytes = baos.toByteArray();

			Document novo = new Document("usuario_id", app.getUsuarioAtual().getObjectId("_id"))
							.append("imagem", new Binary(bytes))
							.append("legenda", areaLeg.getText().trim())
							.append("criado_em", new java.util.Date())
							.append("curtidas", 0)
							.append("comentarios", new java.util.ArrayList<Document>());

			ConexaoMongo.POSTS.insertOne(novo);

			campoImg.setText("");
			areaLeg.setText("");

			selectedImage = null;

			previewLabel.setIcon(null);


			app.recarregarFeed();
			app.mostrarTela("feed");

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,
							"Erro ao gravar imagem: " + e.getMessage());
		}

	}

	private void escolherImagem() {
		JFileChooser fc = new JFileChooser();

		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {

				File f = fc.getSelectedFile();
				selectedImage = ImageIO.read(f);

				campoImg.setText(f.getAbsolutePath());
				atualizarPreview();


			}catch(Exception e) {
				JOptionPane.showMessageDialog(this, "Erro ao carregar imagem." + e.getMessage(),
								"Erro de leitura",
								JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void atualizarPreview() {
		if(selectedImage == null)
			return;

		int w = selectedImage.getWidth();
		int h = selectedImage.getHeight();

		double f = Math.min(
						(double) PREVIEW_WIDTH / w,
						(double) PREVIEW_HEIGHT / h
		);

		Image scaled = selectedImage.getScaledInstance(
						(int) (w * f),
						(int) (h * f),
						Image.SCALE_SMOOTH
		);

		previewLabel.setIcon(new ImageIcon(scaled));
	}
}
