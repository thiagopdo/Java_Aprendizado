package chat;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.Binary;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class JanelaPerfilPublico extends JFrame {
	private JLabel labelFoto, labelNome, labelEmail, labelTelefone;
	private JPanel painelAlbum;
	private Document docUsuario;
	private Usuario usuarioLogado;
	private FotoAlbumPainel.JanelaFoto janelaFoto;


	public JanelaPerfilPublico(Document doc, Usuario usuarioLogado) {
		this.docUsuario = doc;
		this.usuarioLogado = usuarioLogado;

		setTitle("Perfil de" + doc.getString("nome"));
		setSize(500, 400);

		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topo.setBorder(new EmptyBorder(10, 10, 10, 10));

		labelFoto = new JLabel();
		labelFoto.setPreferredSize(new Dimension(70, 70));

		topo.add(labelFoto);

		JPanel texto = new JPanel(new GridLayout(3, 1));

		String nome = doc.getString("nome");

		String telefone = doc.getString("telefone");

		String email = doc.getString("email");

		labelNome = new JLabel("Nome: " + (nome != null ? nome : ""));
		labelTelefone = new JLabel("Telefone: " + (telefone != null ? telefone : ""));
		labelEmail = new JLabel("Email: " + (email != null ? email : ""));

		texto.add(labelNome);
		texto.add(labelTelefone);
		texto.add(labelEmail);

		topo.add(texto);

		add(topo, BorderLayout.NORTH);

		painelAlbum = new JPanel(new FlowLayout());
		painelAlbum.setBackground(new Color(230, 230, 230));

		JScrollPane scrollAlbum = new JScrollPane(painelAlbum);
		add(scrollAlbum, BorderLayout.CENTER);

		carregarInfoUsuario();
		carregarAlbum(doc.getString("email"));


	}

	private void carregarAlbum(String email) {

		painelAlbum.removeAll();

		MongoCollection<Document> photos = ChatApp.database.getCollection("photos");

		List<Document> lista = photos.find(eq("ownerEmail", email)).into(new ArrayList<>());

		for(Document df : lista) {
			Binary bin = (Binary) df.get("photoData");

			byte[] bytes = (bin != null) ? bin.getData() : null;

			ImageIcon icon = ChatApp.carregarImagemOuPadrao(bytes, 100, 100);
			JLabel lbl = new JLabel(icon);
			lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));

			lbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					new FotoAlbumPainel.JanelaFoto(df, usuarioLogado).setVisible(true);
				}
			});

			painelAlbum.add(lbl);
		}

		painelAlbum.revalidate();
		painelAlbum.repaint();
	}

	private void carregarInfoUsuario() {
		Binary bin = (Binary) docUsuario.get("fotoPerfilBytes");
		byte[] bytes = (bin != null) ? bin.getData() : null;

		ImageIcon icon = ChatApp.carregarImagemOuPadrao(bytes, 70, 70);

		labelFoto.setIcon(icon);
	}
}