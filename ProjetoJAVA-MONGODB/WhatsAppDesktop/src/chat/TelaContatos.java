package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import javax.print.Doc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TelaContatos extends JPanel {
	private JTextField campoBusca;
	private JButton botaoBuscar, botaoVoltar;
	private DefaultListModel<ContatoItem> modelo;
	private JList<ContatoItem> listaContatos;

	private Usuario usuarioAtual;

	private JPanel painelPrincipal;
	private CardLayout controladorTelas;

	MongoDatabase database;

	TelaListaChats telaListaChats;

	JanelaChat janelaChat;

	public TelaContatos(JPanel painelPrincipal, CardLayout controladorTelas, Usuario usuarioAtual, TelaListaChats telaListaChats, MongoDatabase database) {
		this.painelPrincipal = painelPrincipal;
		this.controladorTelas = controladorTelas;
		this.usuarioAtual = usuarioAtual;
		this.telaListaChats = telaListaChats;
		this.database = database;

		setLayout(new BorderLayout());
		setBackground(new Color(230, 230, 230));

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topo.setBackground(new Color(7, 94, 84));
		topo.add(new JLabel("<html><font color ='white'><b>Buscar:</b></font></html>"));

		campoBusca = new JTextField(20);

		botaoBuscar = new JButton("Buscar");
		botaoBuscar.setBackground(new Color(161, 203, 166));
		botaoBuscar.setForeground(Color.BLACK);
		botaoBuscar.setOpaque(true);
		botaoBuscar.setBorderPainted(false);

		topo.add(campoBusca);

		topo.add(botaoBuscar);

		add(topo, BorderLayout.NORTH);

		modelo = new DefaultListModel<>();

		listaContatos = new JList<>(modelo);

		listaContatos.setCellRenderer(new ChatApp.RenderizadorContato());
		listaContatos.setBackground(new Color(245, 245, 245));

		add(new JScrollPane(listaContatos), BorderLayout.CENTER);

		JPanel base = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		base.setBackground(new Color(230, 230, 230));

		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBackground(new Color(7, 94, 84));
		botaoVoltar.setForeground(Color.WHITE);
		botaoVoltar.setOpaque(true);
		botaoVoltar.setBorderPainted(false);

		base.add(botaoVoltar);
		add(base, BorderLayout.SOUTH);

		botaoBuscar.addActionListener(e -> {
			buscarContato(campoBusca.getText().trim());
		});

		listaContatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(e.getClickCount() == 2) {
					ContatoItem selecionado = listaContatos.getSelectedValue();

					if(selecionado != null) {
						criarOuAbrirChat(selecionado.email);
					}
				}
			}
		});

		botaoVoltar.addActionListener(e -> {
			controladorTelas.show(painelPrincipal, "lista");
		});

		buscarContato("");
	}

	private void buscarContato(String texto) {
		modelo.clear();

		MongoCollection<Document> users = ChatApp.database.getCollection("users");
		List<Document> listaDoc = users.find().into(new ArrayList<>());

		for(Document d : listaDoc) {
			String nome = d.getString("nome") == null ? "" : d.getString("nome");
			String email = d.getString("email") == null ? "" : d.getString("email");

			if(usuarioAtual != null && email.equals(usuarioAtual.email))
				continue;

			if(nome.toLowerCase().contains(texto.toLowerCase()) || email.toLowerCase().contains(texto.toLowerCase())) {
				Binary fotoBin = (Binary) d.get("fotoPerfilBytes");
				ContatoItem ci = new ContatoItem(d.getObjectId("_id"), nome, email, fotoBin);

				modelo.addElement(ci);
			}
		}
	}

	public void setUsuarioAtual(Usuario usuarioLogado) {
		this.usuarioAtual = usuarioLogado;
	}

	public void setTelaListaChats(TelaListaChats telaListaChats) {
		this.telaListaChats = telaListaChats;
	}

	private void criarOuAbrirChat(String email) {
		MongoCollection<Document> chats = database.getCollection("chats");
		List<Document> todosChats = chats.find().into(new ArrayList<>());
		Document conversa = null;

		for(Document doc : todosChats) {
			List<String> parts = doc.getList("participantes", String.class);

			if(parts != null && parts.contains(usuarioAtual.email) && parts.contains(email)) {
				conversa = doc;
				break;
			}
		}

		if(conversa == null) {
			Document novo = new Document("participantes", List.of(usuarioAtual.email, email))
							.append("mensagens", new ArrayList<>());
			chats.insertOne(novo);
			conversa = novo;
		}

		controladorTelas.show(painelPrincipal, "lista");

		JanelaChat jc = new JanelaChat(conversa, email, telaListaChats, -1, database, usuarioAtual);

		jc.setVisible(true);

	}
}
