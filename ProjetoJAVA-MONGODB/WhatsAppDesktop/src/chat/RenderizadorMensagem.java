package chat;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;

import static chat.ChatApp.carregarImagemOuPadrao;
import static com.mongodb.client.model.Filters.eq;

public class RenderizadorMensagem implements ListCellRenderer<ChatApp.Mensagem> {
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private Usuario usuarioLogado;
	private Map<ObjectId, ChatApp.EstiloMensagem> estilos;

	private static MongoDatabase database = ChatApp.database;

	public RenderizadorMensagem(Usuario logado, Map<ObjectId, ChatApp.EstiloMensagem> estilos) {
		this.usuarioLogado = logado;
		this.estilos = estilos;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ChatApp.Mensagem> list, ChatApp.Mensagem mensagem, int index, boolean isSelected, boolean cellHasFocus) {
		boolean isMe = usuarioLogado != null && mensagem.remetente.equals(usuarioLogado.email);
		JPanel panel = new JPanel(new FlowLayout(isMe ? FlowLayout.RIGHT : FlowLayout.LEFT));

		panel.setBorder(new EmptyBorder(5, 10, 5, 10));

		if(isMe) {
			panel.setBackground(new Color(220, 250, 200));
		}else{
			panel.setBackground(Color.WHITE);
		}

		JPanel conteudoPanel = new JPanel();

		conteudoPanel.setLayout(new BoxLayout(conteudoPanel, BoxLayout.Y_AXIS));
		conteudoPanel.setOpaque(false);

		String nomeRemetente = isMe ? "Voces" : buscarNomeUsuarioPorEmail(mensagem.remetente);

		JLabel labelRemetente = new JLabel("<html><font color ='black'><b>" + nomeRemetente + "</b></font></html>");
		labelRemetente.setCursor(new Cursor(Cursor.HAND_CURSOR));

		labelRemetente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!mensagem.remetente.equals(usuarioLogado.email)) {
					Document docUsuario = database.getCollection("users").find(eq("email", mensagem.remetente)).first();

					if(docUsuario != null) {
						new JanelaPerfilPublico(docUsuario, usuarioLogado).setVisible(true);
					}
				}
			}
		});

		conteudoPanel.add(labelRemetente);

		String dataFormatada = (mensagem.dataEnvio > 0) ? sdf.format(mensagem.dataEnvio) : "Data desconhecida";

		switch(mensagem.tipo) {
			case "texto":
				JLabel lblTexto = new JLabel(mensagem.conteudo);
				conteudoPanel.add(lblTexto);
				break;

			case "imagem":
				byte[] imagemAExibir = mensagem.imagemBytes;
				if(imagemAExibir != null && imagemAExibir.length > 0) {
					ImageIcon icon = carregarImagemOuPadrao(imagemAExibir, 150, 150);
					JLabel lblImagem = new JLabel(icon);

					lblImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));

					lblImagem.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							JFrame janelaFull = new JFrame("Imagem tela cheia");

							janelaFull.setSize(600, 600);
							janelaFull.setLocationRelativeTo(null);

							ImageIcon grande = carregarImagemOuPadrao(imagemAExibir, 500, 500);

							JLabel lblFull = new JLabel(grande);

							janelaFull.add(lblFull);

							janelaFull.setVisible(true);
						}
					});

					conteudoPanel.add(lblImagem);
				}else{
					conteudoPanel.add(new JLabel("[Imagem vazia ou corrompida]"));
				}
				break;

			case "arquivo":
				File arq = new File(mensagem.conteudo);

				JLabel lblArq = new JLabel("<html>Enviou um arquivo: <b>" + arq.getName() + "</b></html>");
				conteudoPanel.add(lblArq);
				break;
		}

		JLabel lblData = new JLabel(dataFormatada);

		lblData.setFont(new Font("Arial", Font.ITALIC, 10));

		conteudoPanel.add(lblData);

		panel.add(conteudoPanel);

		return panel;
	}

	private String buscarNomeUsuarioPorEmail(String outroEmail) {
		MongoCollection<Document> colecao = database.getCollection("users");

		Document doc = colecao.find(eq("email", outroEmail)).first();

		if(doc != null && doc.getString("nome") != null) {
			return doc.getString("nome");
		}

		return outroEmail;
	}
}