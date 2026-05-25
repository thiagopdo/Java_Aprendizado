package chat;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class JanelaPesquisaMensagens extends JFrame {
	private JTextField campoPesquisa;
	private JButton botaoPesquisar;
	private DefaultListModel<ResultadoPesquisa> modeloResultados;
	private JList<ResultadoPesquisa> listaResultados;
	private Usuario usuarioAtual;
	private TelaListaChats telaListaChats;

	public JanelaPesquisaMensagens(Usuario usuarioAtual, TelaListaChats telaListaChats) {
		this.usuarioAtual = usuarioAtual;
		this.telaListaChats = telaListaChats;
		setTitle("Pesquisar Mensagens");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topo.add(new JLabel("Pesquisar por:"));

		campoPesquisa = new JTextField(20);
		topo.add(campoPesquisa);

		botaoPesquisar = new JButton("Pesquisar");
		topo.add(botaoPesquisar);

		add(topo, BorderLayout.NORTH);

		modeloResultados = new DefaultListModel<>();

		listaResultados = new JList<>(modeloResultados);
		listaResultados.setCellRenderer(new RenderizadorResultadoPesquisa());

		add(new JScrollPane(listaResultados), BorderLayout.CENTER);

		listaResultados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					ResultadoPesquisa selecionado = listaResultados.getSelectedValue();

					if(selecionado != null) {
						abrirChatNoIndice(selecionado);
					}
				}
			}
		});

		botaoPesquisar.addActionListener(e -> {
			pesquisarText(campoPesquisa.getText().trim());
		});

	}

	private void pesquisarText(@NotNull String texto) {
		modeloResultados.clear();

		if(texto.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Digite para pesquisar: ");
			return;
		}

		MongoCollection<Document> chatsCol = ChatApp.database.getCollection("chats");

		List<Document> todosChats = chatsCol.find().into(new ArrayList<>());

		for(Document doc : todosChats) {
			List<String> parts = doc.getList("participantes", String.class);

			if(parts != null && parts.contains(usuarioAtual.email)) {
				List<Document> msgs = (List<Document>) doc.get("mensagens");

				if(msgs != null) {
					for(int i = 0; i < msgs.size(); i++) {
						Document msgDoc = msgs.get(i);
						String tipo = msgDoc.getString("tipo");
						String cont = msgDoc.getString("conteudo");

						if(cont == null)
							cont = "";
						if(cont.toLowerCase().contains(texto.toLowerCase())) {
							String remetente = msgDoc.getString("remetente");

							String snippet = (cont.length() > 50) ? cont.substring(0, 50) + "..." : cont;

							String outro = "";

							for(String p : parts) {
								if(!p.equals(usuarioAtual.email)) {
									outro = p;
								}
							}
							ResultadoPesquisa rp = new ResultadoPesquisa();
							rp.idConversa = doc.getObjectId("_id");
							rp.outroEmail = outro;
							rp.indexMensagem = i;
							rp.rementente = remetente;
							rp.snippet = snippet;
							rp.tipoMsg = tipo != null ? tipo : "texto";

							modeloResultados.addElement(rp);
						}
					}
				}
			}
		}

		if(modeloResultados.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Nenhum resultado encontrado para:" + texto,
							"Pesquisa",
							JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void abrirChatNoIndice(ResultadoPesquisa selecionado) {
		Document conversa = ChatApp.database.getCollection("chats").find(eq("_id", selecionado.idConversa)).first();

		if(conversa == null) {
			JOptionPane.showMessageDialog(this,
							"Conversa não encontrada",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		JanelaChat jc = new JanelaChat(conversa, selecionado.outroEmail, telaListaChats, selecionado.indexMensagem, ChatApp.database, usuarioAtual);
		jc.setVisible(true);

	}

}

class RenderizadorResultadoPesquisa implements ListCellRenderer<ResultadoPesquisa> {

	@Override
	public Component getListCellRendererComponent(JList<? extends ResultadoPesquisa> list, ResultadoPesquisa value, int index, boolean isSelected, boolean cellHasFocus) {

		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(isSelected ? new Color(220, 240, 220) : Color.WHITE);

		JLabel lblInfo = new JLabel("<html><b>Conversa c/" + value.outroEmail + "</b> | (" + value.tipoMsg + ") " + value.snippet + "<br>" + "Remetente: " + value.rementente + "</html>");

		panel.add(lblInfo, BorderLayout.CENTER);
		return panel;
	}
}


class ResultadoPesquisa {

	public ObjectId idConversa;
	public String outroEmail;
	public int indexMensagem;
	public String rementente, snippet, tipoMsg;

	@Override
	public String toString() {
		return "[Conversa c/ " + outroEmail + "] " + "Remetente: " + rementente + " | (" + tipoMsg + ") " + snippet;
	}

}