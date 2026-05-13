package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	public TelaListaChats(Usuario usuarioAtual, TelaPerfil telaPerfil, CardLayout controladorTelas, JPanel painelPrincipal, TelaContatos telaContatos) {
		setLayout(new BorderLayout());
		setBackground(new Color(230, 230, 230));

		JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topo.setBackground(new Color(7, 94, 84));

		this.painelPrincipal = painelPrincipal;
		this.usuarioAtual = usuarioAtual;
		this.telaPerfil = telaPerfil;
		this.telaContatos = telaContatos;


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
				telaContatos.carregarContatos();
				controladorTelas.show(painelPrincipal, "contatos");
			}else{
				JOptionPane.showMessageDialog(this, "A tela de contatos não foi carregada corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		botaoPesquisarMensagens.addActionListener(e -> {
			JanelaPesquisaMensagens jpm = new JanelaPesquisaMensagens();
			jpm.setVisible(true);
		});

		timerAtualizacao = new Timer(2000, e -> {
			if(usuarioAtual != null) {
				carregarChats();
			}
		});

	}

	public void carregarChats() {
	}

	public void iniciarAtualizacao() {
		timerAtualizacao.start();
	}


	public void pararAtualizacao() {
		timerAtualizacao.stop();
	}

	private void abrirChat(String emailOutro) {
	}
}
