package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static chat.ChatApp.carregarImagemOuPadrao;

public class TelaPerfil extends JPanel {
	private JTextField campoNome, campoStatus, campoTelefone;
	private JLabel labelFoto;
	private JButton botaoFoto, botaoSalvar, botaoVoltar;
	private JButton botaoAdicionarFoto;
	private JPanel painelAlbum;
	private CardLayout controladorTelas;
	private TelaListaChats telaListaChats;
	private JPanel painelPrincipal;

	private Usuario usuarioAtual;

	MongoDatabase database;


	public TelaPerfil(JPanel painelPrincipal, CardLayout controladorTelas, MongoDatabase database) {
		this.painelPrincipal = painelPrincipal;
		this.controladorTelas = controladorTelas;
		this.database = database;

		setLayout(new GridBagLayout());
		setBackground(new Color(230, 230, 230));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel titulo = new JLabel("Meu perfil");
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(titulo, gbc);

		gbc.gridwidth = 1;

		JLabel ln = new JLabel("Nome:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(ln, gbc);
		campoNome = new JTextField(20);
		gbc.gridx = 1;
		add(campoNome, gbc);

		JLabel lstatus = new JLabel("Status:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(lstatus, gbc);
		campoStatus = new JTextField(20);
		gbc.gridx = 1;
		add(campoStatus, gbc);

		JLabel ltelefone = new JLabel("Telefone:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(ltelefone, gbc);
		campoTelefone = new JTextField(20);
		gbc.gridx = 1;
		add(campoTelefone, gbc);

		botaoFoto = new JButton("Foto de Perfil");
		botaoFoto.setBackground(new Color(7, 91, 80));
		botaoFoto.setForeground(Color.WHITE);
		botaoFoto.setOpaque(true);
		botaoFoto.setBorderPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 4;
		add(botaoFoto, gbc);

		labelFoto = new JLabel();
		labelFoto.setBorder(new EmptyBorder(0, 10, 0, 10));
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(labelFoto, gbc);

		botaoSalvar = new JButton("Salvar");
		botaoSalvar.setBackground(new Color(7, 91, 80));
		botaoSalvar.setForeground(Color.WHITE);
		botaoSalvar.setOpaque(true);
		botaoSalvar.setBorderPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 5;
		add(botaoSalvar, gbc);

		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBackground(new Color(37, 211, 102));
		botaoVoltar.setForeground(Color.BLACK);
		botaoVoltar.setOpaque(true);
		botaoVoltar.setBorderPainted(false);
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.EAST;
		add(botaoVoltar, gbc);

		botaoAdicionarFoto = new JButton("Adicionar Foto ao Album");
		botaoAdicionarFoto.setBackground(new Color(37, 211, 102));
		botaoAdicionarFoto.setForeground(Color.WHITE);
		botaoAdicionarFoto.setOpaque(true);
		botaoAdicionarFoto.setBorderPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		add(botaoAdicionarFoto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		painelAlbum = new JPanel(new FlowLayout());
		painelAlbum.setBackground(new Color(230, 230, 230));

		JScrollPane scrollAlbum = new JScrollPane(painelAlbum);
		scrollAlbum.setPreferredSize(new Dimension(400, 180));
		add(scrollAlbum, gbc);

		botaoFoto.addActionListener(e -> {
			if(usuarioAtual == null) {
				JOptionPane.showMessageDialog(this, "Usuário não carregado na tela de perfil.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			System.out.println("Botão Foto de Perfil clicado.");
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));

			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File arquivo = fc.getSelectedFile();

				if(arquivo != null) {

					try {

						byte[] bytes = lerBytesDoArquivo(arquivo);

						if(bytes != null) {
							ImageIcon icon = carregarImagemOuPadrao(bytes, 70, 70);
							labelFoto.setIcon(icon);

							usuarioAtual.fotoPerfilBytes = new Binary(BsonBinarySubType.BINARY, bytes);
							revalidate();
							repaint();
						}

					}catch(Exception ex) {
						JOptionPane.showMessageDialog(this,
										"Erro ao carregar imagem.",
										"Foto de Perfil",
										JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		botaoSalvar.addActionListener(e -> {
			if(usuarioAtual == null) {
				JOptionPane.showMessageDialog(this, "Usuário não carregado!", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			MongoCollection<Document> users = database.getCollection("users");

			Document updateDoc = new Document("nome", campoNome.getText())
							.append("status", campoStatus.getText())
							.append("telefone", campoTelefone.getText())
							.append("fotoPerfilBytes", usuarioAtual.fotoPerfilBytes);

			users.updateOne(eq("_id", usuarioAtual.id), new Document("$set", updateDoc));

			usuarioAtual.nome = campoNome.getText();
			usuarioAtual.status = campoStatus.getText();
			usuarioAtual.telefone = campoTelefone.getText();

			controladorTelas.show(painelPrincipal, "lista");
			telaListaChats.carregarChats();
		});

		botaoVoltar.addActionListener(e -> controladorTelas.show(painelPrincipal, "lista"));

		botaoAdicionarFoto.addActionListener(e -> {
			if(usuarioAtual == null) {
				JOptionPane.showMessageDialog(this, "Usuário não carregado na tela de perfil.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));

			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();

				if(f != null && f.exists()) {
					try {

						byte[] bytes = lerBytesDoArquivo(f);

						if(bytes != null) {
							MongoCollection<Document> photos = database.getCollection("photos");
							Document docFoto = new Document("ownerEmail", usuarioAtual.email)
											.append("photoName", f.getName())
											.append("photoData", new Binary(BsonBinarySubType.BINARY, bytes))
											.append("likes", new ArrayList<String>())
											.append("comments", new ArrayList<String>());

							photos.insertOne(docFoto);

							carregarAlbum(usuarioAtual.email);
						}

					}catch(Exception ex) {
						JOptionPane.showMessageDialog(this,
										"Erro ao adicionar foto ao álbum." + ex.getMessage(),
										"Erro ao adicionar foto",
										JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}

	public void carregarAlbum(String emailUsuario) {
		painelAlbum.removeAll();

		MongoCollection<Document> photos = database.getCollection("photos");

		List<Document> listaFotos = photos.find(eq("ownerEmail", emailUsuario)).into(new ArrayList<>());

		for(Document df : listaFotos) {
			FotoAlbumPainel painelFoto = new FotoAlbumPainel(df, usuarioAtual, () -> {
				carregarAlbum(usuarioAtual.email);
			});

			painelAlbum.add(painelFoto);
		}

		painelAlbum.revalidate();
		painelAlbum.repaint();
	}

	public void setTelaListaChats(TelaListaChats telaListaChats) {
		this.telaListaChats = telaListaChats;
	}

	public void carregarPerfil(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
		System.out.println("[DEBUG_LOG] TelaPerfil.carregarPerfil() - usuarioAtual: " + (usuarioAtual != null ? usuarioAtual.nome : "null"));
		if(usuarioAtual != null) {
			campoNome.setText(usuarioAtual.nome != null ? usuarioAtual.nome : "");
			campoStatus.setText(usuarioAtual.status != null ? usuarioAtual.status : "");
			campoTelefone.setText(usuarioAtual.telefone != null ? usuarioAtual.telefone : "");

			byte[] fotoBytes = (usuarioAtual.fotoPerfilBytes != null) ? usuarioAtual.fotoPerfilBytes.getData() : null;
			ImageIcon icon = carregarImagemOuPadrao(fotoBytes, 70, 70);
			labelFoto.setIcon(icon);

			carregarAlbum(usuarioAtual.email);
		}
	}

	private static byte[] lerBytesDoArquivo(File f) throws IOException {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    FileInputStream fis = new FileInputStream(f)) {

			byte[] buffer = new byte[8192];
			int len;

			while((len = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}

			return bos.toByteArray();
		}
	}
}
