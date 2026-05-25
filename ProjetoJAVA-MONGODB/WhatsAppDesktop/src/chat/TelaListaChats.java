package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TelaListaChats extends JPanel {
	private JList<ContatoItem> listaChats;
	private DefaultListModel<ContatoItem> modelo;
	private JButton botaoPerfil, botaoContatos, botaoPesquisarMensagens;
	private Timer timerAtualizacao;
	private Usuario usuarioAtual;
	private TelaPerfil telaPerfil;
	private CardLayout controladorTelas;
	private JPanel painelPrincipal;
	private TelaContatos telaContatos;
	private TelaListaChats telaListaChats;

	MongoDatabase database;

	public TelaListaChats(TelaPerfil telaPerfil, CardLayout controladorTelas, JPanel painelPrincipal, TelaContatos telaContatos, MongoDatabase database, TelaListaChats telaListaChats) {
		setLayout(new BorderLayout());
		setBackground(new Color(230, 230, 230));

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topo.setBackground(new Color(7, 94, 84));

		this.painelPrincipal = painelPrincipal;
		this.telaPerfil = telaPerfil;
		this.telaContatos = telaContatos;
		this.controladorTelas = controladorTelas;
		this.database = database;
		this.usuarioAtual = null; // começa nulo por segurança
		this.telaListaChats = telaListaChats;


		botaoPerfil = new JButton("Meu Perfil");
		botaoPerfil.setBackground(new Color(7, 94, 84));
		botaoPerfil.setForeground(Color.WHITE);
		botaoPerfil.setOpaque(true);
		botaoPerfil.setBorderPainted(false);

		botaoContatos = new JButton("Meus Contatos");
		botaoContatos.setBackground(new Color(7, 94, 84));
		botaoContatos.setForeground(Color.WHITE);
		botaoContatos.setOpaque(true);
		botaoContatos.setBorderPainted(false);

		botaoPesquisarMensagens = new JButton("Pesquisar");
		botaoPesquisarMensagens.setBackground(new Color(7, 94, 84));
		botaoPesquisarMensagens.setForeground(Color.WHITE);
		botaoPesquisarMensagens.setOpaque(true);
		botaoPesquisarMensagens.setBorderPainted(false);

		topo.add(botaoPerfil);
		topo.add(botaoContatos);
		topo.add(botaoPesquisarMensagens);

		add(topo, BorderLayout.NORTH);

		modelo = new DefaultListModel<>();

		listaChats = new JList<>(modelo);
		listaChats.setCellRenderer(new ChatApp.RenderizadorContato());
		listaChats.setBackground(new Color(245, 245, 245));
		listaChats.setOpaque(true);

		add(new JScrollPane(listaChats), BorderLayout.CENTER);

		listaChats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					ContatoItem selecionado = listaChats.getSelectedValue();

					if(selecionado != null) {
						abrirChat(selecionado.email);
					}
				}
			}
		});

		botaoPerfil.addActionListener(e -> {
			if(telaPerfil != null) {
				telaPerfil.carregarPerfil(usuarioAtual);
				controladorTelas.show(painelPrincipal, "perfil");
			}else{
				JOptionPane.showMessageDialog(this, "A tela de perfil não foi carregada corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		botaoContatos.addActionListener(e -> {
			if(telaContatos != null) {
				telaContatos.setUsuarioAtual(usuarioAtual);
				carregarContatos();
				controladorTelas.show(painelPrincipal, "contatos");
			}else{
				JOptionPane.showMessageDialog(this, "A tela de contatos não foi carregada corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		botaoPesquisarMensagens.addActionListener(e -> {
			JanelaPesquisaMensagens jpm = new JanelaPesquisaMensagens(usuarioAtual, this);
			jpm.setVisible(true);
		});

		timerAtualizacao = new Timer(2000, e -> {
			if(usuarioAtual != null) {
				carregarChats();
			}
		});

	}

	public void setTelaListaChats(TelaListaChats telaListaChats) {
		this.telaListaChats = telaListaChats;
	}

	public void carregarChats() {
		modelo.clear();

		if(usuarioAtual == null)
			return;

		MongoCollection<Document> chats = database.getCollection("chats");

		List<Document> listaDoc = chats.find().into(new ArrayList<>());

		Set<String> emailsEncontrados = new HashSet<>();

		Map<String, Integer> mapaNaoLidas = new HashMap<>();

		for(Document d : listaDoc) {

			List<String> parts = d.getList("participantes", String.class);
			if(parts != null && parts.contains(usuarioAtual.email)) {

				for(String p : parts) {

					if(!p.equals(usuarioAtual.email)) {
						emailsEncontrados.add(p);

						List<Document> msgs = (List<Document>) d.get("mensagens");

						int naoLidas = 0;

						if(msgs != null) {
							for(Document msg : msgs) {
								String remetente = msg.getString("remetente");
								List<String> lidoPor = (List<String>) msg.get("lidaPor");

								if(remetente != null && !remetente.equals(usuarioAtual.email)) {
									if(lidoPor == null || !lidoPor.contains(usuarioAtual.email)) {
										naoLidas++;
									}
								}
							}
						}

						mapaNaoLidas.put(p, mapaNaoLidas.getOrDefault(p, 0) + naoLidas);
					}
				}
			}
		}

		MongoCollection<Document> users = database.getCollection("users");

		for(String email : emailsEncontrados) {
			Document doc = users.find(eq("email", email)).first();

			if(doc != null) {
				String nome = doc.getString("nome");

				Binary fotoBin = (Binary) doc.get("fotoPerfilBytes");

				int qtd = mapaNaoLidas.getOrDefault(email, 0);

				String titulo = nome + (qtd > 0 ? " (" + qtd + " não lidas)" : "");

				ContatoItem item = new ContatoItem(doc.getObjectId("_id"), titulo, doc.getString("email"), fotoBin);

				modelo.addElement(item);
			}
		}
	}


	public void carregarContatos() {
		modelo.clear();

		MongoCollection<Document> users = database.getCollection("users");

		List<Document> listaDoc = users.find().into(new ArrayList<>());

		for(Document d : listaDoc) {
			String email = d.getString("email");
			String nome = d.getString("nome");

			if(email != null && usuarioAtual != null && !email.equals(usuarioAtual.email)) {
				Binary fotoBin = (Binary) d.get("fotoPerfilBytes");
				ContatoItem ci = new ContatoItem(
								d.getObjectId("_id"),
								d.getString("nome"),
								nome,
								fotoBin);

				modelo.addElement(ci);
			}
		}
	}


	public void setUsuarioAtual(Usuario usuarioAtual) {
		System.out.println("[DEBUG_LOG] TelaListaChats.setUsuarioAtual() chamado para: " + (usuarioAtual != null ? usuarioAtual.nome : "null"));
		this.usuarioAtual = usuarioAtual;
	}

	public void iniciarAtualizacao() {
		timerAtualizacao.start();
	}


	public void pararAtualizacao() {
		timerAtualizacao.stop();
	}

	private void abrirChat(String emailOutro) {

		MongoCollection<Document> chats = database.getCollection("chats");

		List<Document> todosChats = chats.find().into(new ArrayList<>());

		Document conversa = null;

		for(Document doc : todosChats) {

			List<String> parts = doc.getList("participantes", String.class);

			if(parts != null && parts.contains(usuarioAtual.email) && parts.contains(emailOutro)) {
				conversa = doc;
				break;
			}
		}

		if(conversa == null) {
			JOptionPane.showMessageDialog(this,
							"Nenhuma conversa encontrada!",
							"Chat",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		JanelaChat jc = new JanelaChat(conversa, emailOutro, telaListaChats, -1, database, usuarioAtual);
		jc.setVisible(true);
	}
}
