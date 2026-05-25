package chat;

import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;

public class JanelaChat extends JFrame {

	private DefaultListModel<ChatApp.Mensagem> modeloMsgs;
	private JList<ChatApp.Mensagem> listaMsgs;
	private JTextField campoTexto;
	private JButton botaoEnviar;
	private JButton botaoImagem;
	private JButton botaoArquivo;
	private Document conversa;
	private String outroEmail;
	private List<Document> mensagens;
	private Map<ObjectId, ChatApp.EstiloMensagem> mapaEstilos = new HashMap<>();
	private JPopupMenu menuPopup;
	private JMenuItem itemEditar;
	private JMenuItem itemDeletar;
	private JMenuItem itemBaixar;
	private int indiceMensagemClicada = -1;
	private TelaListaChats referenciaLista;
	private Timer timerChat;
	private int scrollIndex;

	private final MongoDatabase database;
	private Usuario usuarioAtual;


	public JanelaChat(Document conversa, String outroEmail, TelaListaChats listaRef, int scrollIndex, MongoDatabase database, Usuario usuarioAtual) {
		this.conversa = conversa;
		this.outroEmail = outroEmail;
		this.referenciaLista = listaRef;
		this.scrollIndex = scrollIndex;
		this.modeloMsgs = new DefaultListModel<>();
		this.database = database;
		this.usuarioAtual = usuarioAtual;


		JPanel topo = new JPanel(new BorderLayout());
		topo.setBackground(new Color(7, 94, 84));

		JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		esquerda.setOpaque(false);

		String nomeContato = buscarNomeUsuarioPorEmail(outroEmail);

		JLabel labelTitulo = new JLabel("<html><font color ='white'><b>" + nomeContato + "</b></font></html>");

		esquerda.add(labelTitulo);

		JPanel direita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		direita.setOpaque(false);

		JButton botaoVerPerfil = new JButton("Ver Perfil");
		botaoVerPerfil.setBackground(new Color(7, 94, 84));
		botaoVerPerfil.setForeground(Color.WHITE);
		botaoVerPerfil.setOpaque(true);
		botaoVerPerfil.setBorderPainted(false);
		botaoVerPerfil.addActionListener(e -> {
			Document docUsuario = database.getCollection("users").find(eq("email", outroEmail)).first();

			if(docUsuario != null) {
				new JanelaPerfilPublico(docUsuario, usuarioAtual).setVisible(true);
			}
		});

		direita.add(botaoVerPerfil);

		topo.add(esquerda, BorderLayout.WEST);
		topo.add(direita, BorderLayout.EAST);
		add(topo, BorderLayout.NORTH);

		setTitle("Chat com " + nomeContato);
		setSize(600, 600);
		setLocationRelativeTo(null);

		modeloMsgs = new DefaultListModel<>();

		listaMsgs = new JList<>(modeloMsgs);

		listaMsgs.setCellRenderer(new RenderizadorMensagem(usuarioAtual, mapaEstilos));

		JScrollPane scroll = new JScrollPane(listaMsgs);
		add(scroll, BorderLayout.CENTER);

		JPanel painelSul = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painelSul.setBackground(new Color(230, 230, 230));

		campoTexto = new JTextField(20);

		botaoEnviar = new JButton("Enviar");
		botaoEnviar.setBackground(new Color(7, 94, 84));
		botaoEnviar.setForeground(Color.WHITE);
		botaoEnviar.setOpaque(true);
		botaoEnviar.setBorderPainted(false);

		botaoImagem = new JButton("Imagem");
		botaoImagem.setBackground(new Color(37, 211, 102));
		botaoImagem.setForeground(Color.WHITE);
		botaoImagem.setOpaque(true);
		botaoImagem.setBorderPainted(false);

		botaoArquivo = new JButton("Arquivo");
		botaoArquivo.setBackground(new Color(255, 152, 0));
		botaoArquivo.setForeground(Color.WHITE);
		botaoArquivo.setOpaque(true);
		botaoArquivo.setBorderPainted(false);

		painelSul.add(campoTexto);
		painelSul.add(botaoEnviar);
		painelSul.add(botaoImagem);
		painelSul.add(botaoArquivo);

		add(painelSul, BorderLayout.SOUTH);

		menuPopup = new JPopupMenu();

		itemEditar = new JMenuItem("Editar Texto");

		itemDeletar = new JMenuItem("Deletar Mensagem");

		itemBaixar = new JMenuItem("Baixar Arquivo");

		menuPopup.add(itemEditar);
		menuPopup.add(itemBaixar);
		menuPopup.add(itemDeletar);

		itemEditar.addActionListener(e -> editarMensagem());

		itemDeletar.addActionListener(e -> deletarMensagem());

		itemBaixar.addActionListener(e -> baixarArquivo());

		listaMsgs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)) {
					indiceMensagemClicada = listaMsgs.locationToIndex(e.getPoint());

					if(indiceMensagemClicada >= 0) {
						ChatApp.Mensagem msg = modeloMsgs.get(indiceMensagemClicada);

						String tipo = (msg.tipo == null) ? "texto" : msg.tipo;

						boolean ehDono = msg.remetente.equals(usuarioAtual.email);

						itemEditar.setEnabled(ehDono && tipo.equals("texto"));

						itemBaixar.setEnabled(tipo.equals("arquivo"));

						itemDeletar.setEnabled(ehDono);

						menuPopup.show(listaMsgs, e.getX(), e.getY());
					}
				}
			}
		});

		carregarMensagens();

		marcarComoLidas();

		if(referenciaLista != null) {
			referenciaLista.carregarChats();
		}

		if(scrollIndex >= 0 && scrollIndex < modeloMsgs.size()) {
			listaMsgs.setSelectedIndex(scrollIndex);

			listaMsgs.ensureIndexIsVisible(scrollIndex);
		}

		timerChat = new Timer(2000, e -> {
			if(isVisible()) {
				Document conversaAtual = database.getCollection("chats").find(eq("_id", conversa.getObjectId("_id"))).first();

				if(conversaAtual != null) {
					JanelaChat.this.conversa = conversaAtual;

					carregarMensagens();

					marcarComoLidas();

					if(referenciaLista != null) {
						referenciaLista.carregarChats();
					}

					if(scrollIndex >= 0 && scrollIndex < modeloMsgs.size()) {
						listaMsgs.ensureIndexIsVisible(scrollIndex);
					}
				}
			}
		});
		timerChat.start();

		botaoEnviar.addActionListener(e -> {
			enviarMensagemTexto(campoTexto.getText());
			campoTexto.setText("");
		});

		botaoImagem.addActionListener(e -> {
			enviarImagem();
		});

		botaoArquivo.addActionListener(e -> {
			enviarArquivo();
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timerChat.stop();
				if(referenciaLista != null) {
					referenciaLista.carregarChats();
				}
				super.windowClosing(e);
			}
		});
	}

	private void enviarArquivo() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Arquivos", "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "zip", "rar", "7z", "rtf", "csv"));

		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			File arquivo = fc.getSelectedFile();

			if(arquivo != null && arquivo.exists()) {
				long agora = System.currentTimeMillis();

				Document novaMsg = new Document("remetente", usuarioAtual.email)
								.append("tipo", "arquivo")
								.append("conteudo", arquivo.getAbsolutePath())
								.append("dataEnvio", agora)
								.append("lidaPor", Collections.singletonList(usuarioAtual.email));
				mensagens.add(novaMsg);

				database.getCollection("chats").updateOne(eq(
												"_id",
												conversa.getObjectId("_id")),
								new Document("$set", new Document("mensagens", mensagens)));

			}else{
				JOptionPane.showMessageDialog(this,
								"Arquivo não encontrado!",
								"Erro de arquivo",
								JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void enviarImagem() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));

		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File arquivo = fc.getSelectedFile();

			if(arquivo != null && arquivo.exists()) {
				try {

					byte[] dadoImg = lerBytesDoArquivo(arquivo);

					long agora = System.currentTimeMillis();

					Document novaMsg = new Document("remetente", usuarioAtual.email)
									.append("tipo", "imagem")
									.append("dataEnvio", agora)
									.append("lidaPor", Collections.singletonList(usuarioAtual.email))
									.append("imagemBytes", new Binary(BsonBinarySubType.BINARY, dadoImg))
									.append("conteudo", arquivo.getName());

					mensagens.add(novaMsg);

					database.getCollection("chats").updateOne(eq(
													"_id",
													conversa.getObjectId("_id")),
									new Document("$push", new Document("mensagens", novaMsg)));

					Document conversaAtualizada = database.getCollection("chats").find(eq("_id", conversa.getObjectId("_id"))).first();
					if(conversaAtualizada != null) {
						this.conversa = conversaAtualizada;
					}

					carregarMensagens();

				}catch(Exception e) {
					JOptionPane.showMessageDialog(this,
									"Falha ao enviar imagem" + e.getMessage(),
									"Erro",
									JOptionPane.ERROR_MESSAGE);
				}
			}else{

				JOptionPane.showMessageDialog(this,
								"Arquivo inválido!",
								"Erro",
								JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	private byte[] lerBytesDoArquivo(File arquivo) throws IOException {

		try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    FileInputStream fis = new FileInputStream(arquivo)) {

			byte[] buffer = new byte[8192];
			int len;

			while((len = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}

			return bos.toByteArray();
		}
	}

	private void enviarMensagemTexto(String texto) {
		if(texto.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Digite algo antes de enviar!",
							"Mensagem vazia",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		long agora = System.currentTimeMillis();

		Document novaMsg = new Document("remetente", usuarioAtual.email)
						.append("tipo", "texto")
						.append("conteudo", texto)
						.append("dataEnvio", agora)
						.append("lidaPor", Collections.singletonList(usuarioAtual.email));

		mensagens.add(novaMsg);

		database.getCollection("chats").updateOne(eq("_id",
										conversa.getObjectId("_id")),
						new Document("$push", new Document("mensagens", novaMsg)));

		Document conversaAtualizada = database.getCollection("chats").find(eq("_id", conversa.getObjectId("_id"))).first();
		if(conversaAtualizada != null) {
			this.conversa = conversaAtualizada;
		}

		carregarMensagens();
	}

	private void marcarComoLidas() {
		boolean alterou = false;

		mensagens = (List<Document>) conversa.get("mensagens");

		if(mensagens != null) {
			for(Document msg : mensagens) {
				String remetente = msg.getString("remetente");

				if(remetente != null && !remetente.equals(usuarioAtual.email)) {
					List<String> lidoPor = (List<String>) msg.get("lidaPor");

					if(lidoPor == null) {
						lidoPor = new ArrayList<>();
						msg.append("lidaPor", lidoPor);
					}

					if(!lidoPor.contains(usuarioAtual.email)) {
						lidoPor.add(usuarioAtual.email);
						msg.put("lidaPor", lidoPor);
						alterou = true;
					}
				}
			}
		}

		if(alterou) {
			database.getCollection("chats").updateOne(eq("_id",
											conversa.getObjectId("_id")),
							new Document("$set", new Document("mensagens", mensagens)));
		}
	}

	private void carregarMensagens() {
		if(conversa == null)
			return;

		mensagens = (List<Document>) conversa.get("mensagens");

		if(mensagens == null)
			mensagens = new ArrayList<>();

		modeloMsgs.clear();

		for(int i = 0; i < mensagens.size(); i++) {
			Document d = mensagens.get(i);

			String rem = d.getString("remetente") == null ? "Desconhecido" : d.getString("remetente");

			String tp = d.getString("tipo") == null ? "texto" : d.getString("tipo");

			long data = d.containsKey("dataEnvio") ? d.getLong("dataEnvio") : 0L;

			Binary binImagem = (Binary) d.get("imagemBytes");

			String textConteudo = d.getString("conteudo");

			byte[] bytesParaMensagem = null;
			if(binImagem != null) {
				bytesParaMensagem = binImagem.getData();
			}

			ChatApp.Mensagem msg = new ChatApp.Mensagem(textConteudo, rem, tp, data, bytesParaMensagem, d.getObjectId("_id"), i);

			msg.indexInterno = i;

			if("imagem".equals(tp) && binImagem != null) {
				msg.imagemBytes = binImagem.getData();
			}else if("imagem".equals(tp)) {
				// Tenta buscar por outro nome de campo se necessário, 
				// ou loga que a imagem está sem bytes no documento
				System.out.println("[DEBUG_LOG] Mensagem do tipo imagem sem bytes para o índice: " + i);
			}

			msg.messageKey = new ObjectId();

			modeloMsgs.addElement(msg);
		}

		listaMsgs.ensureIndexIsVisible(modeloMsgs.size() - 1);

	}

	private void baixarArquivo() {
		if(indiceMensagemClicada < 0)
			return;

		ChatApp.Mensagem msg = modeloMsgs.get(indiceMensagemClicada);

		if(!"arquivo".equals(msg.tipo))
			return;

		File arquivoOrigem = new File(msg.conteudo);

		if(!arquivoOrigem.exists()) {
			JOptionPane.showMessageDialog(this,
							"Arquivo não encontrado",
							"Erro ao baixar",
							JOptionPane.ERROR_MESSAGE);
			return;
		}

		JFileChooser salvar = new JFileChooser();
		salvar.setSelectedFile(new File(arquivoOrigem.getName()));
		salvar.setDialogTitle("Salvar como");

		if(salvar.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File arquivoDestino = salvar.getSelectedFile();


			try {

				copiarArquivo(arquivoOrigem, arquivoDestino);

				JOptionPane.showMessageDialog(this,
								"Arquivo salvo em:" + arquivoDestino.getAbsolutePath());

			}catch(Exception e) {
				JOptionPane.showMessageDialog(this,
								"Erro ao salvar arquivo: " + e.getMessage(),
								"Erro",
								JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void copiarArquivo(File arquivoOrigem, File arquivoDestino) throws IOException {

		try(FileInputStream fis = new FileInputStream(arquivoOrigem);
		    FileOutputStream fos = new FileOutputStream(arquivoDestino)) {

			byte[] buffer = new byte[8192];
			int bytesLidos;

			while((bytesLidos = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesLidos);
			}
		}
	}

	private void deletarMensagem() {
		if(indiceMensagemClicada < 0)
			return;

		ChatApp.Mensagem msg = modeloMsgs.get(indiceMensagemClicada);

		if(!msg.remetente.equals(usuarioAtual.email)) {
			JOptionPane.showMessageDialog(this,
							"Você só pode deletar suas próprias mensagens!",
							"Deletar Mensagem",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		int opt = JOptionPane.showConfirmDialog(this,
						"Tem certeza que deseja deletar esta mensagem?",
						"Confirmar Deleção",
						JOptionPane.YES_NO_OPTION);

		if(opt == JOptionPane.YES_OPTION) {
			mensagens.remove(indiceMensagemClicada);

			database.getCollection("chats").updateOne(eq("_id", conversa.getObjectId("_id")),
							new Document("$set", new Document("mensagens", mensagens)));

			carregarMensagens();
		}
	}

	private void editarMensagem() {
		if(indiceMensagemClicada < 0)
			return;

		ChatApp.Mensagem msg = modeloMsgs.get(indiceMensagemClicada);

		if(!msg.remetente.equals(usuarioAtual.email)) {
			JOptionPane.showMessageDialog(this,
							"Você só pode editar suas próprias mensagens!",
							"Permissão negada",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(!"texto".equals(msg.tipo)) {
			JOptionPane.showMessageDialog(this,
							"Apenas mensagem de texto para editar",
							"Editar mensagens",
							JOptionPane.WARNING_MESSAGE);
			return;
		}

		String nova = JOptionPane.showInputDialog(this,
						"Editar mensagem",
						msg.conteudo);

		if(nova != null && !nova.trim().isEmpty()) {
			Document docMsg = mensagens.get(indiceMensagemClicada);

			docMsg.put("conteudo", nova);
			database.getCollection("chats").updateOne(eq("_id", conversa.getObjectId("_id")),
							new Document("$set", new Document("mensagens", mensagens)));

			carregarMensagens();
		}
	}


	private String buscarNomeUsuarioPorEmail(String remetente) {
		MongoCollection<Document> colecao = database.getCollection("users");

		Document doc = colecao.find(eq("email", remetente)).first();

		if(doc != null && doc.getString("nome") != null) {
			return doc.getString("nome");
		}

		return remetente;
	}
}


