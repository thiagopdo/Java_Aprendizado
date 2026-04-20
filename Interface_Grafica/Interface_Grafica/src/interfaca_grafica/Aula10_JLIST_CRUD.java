package interfaca_grafica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Aula10_JLIST_CRUD {
	static class Item {
		final int id;
		String nome;
		Item(int id, String nome) {
			this.id = id;
			this.nome = nome;
		}

		@Override
		public String toString() {
			return nome;
		}
	}

	private final List<Item> dados = new ArrayList<>();
	private int proximoId = 1;

	private JFrame janela;
	private JList<Item> lista;
	private DefaultListModel<Item> modeloFiltrado;
	private JTextField campoNome;
	private JTextField campoPesquisa;
	private JLabel rotuloContagem;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Aula10_JLIST_CRUD().iniciar());
	}

	private void iniciar() {
		janela = new JFrame("JList com CRUD + Pesquisa");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(800, 500);
		janela.setLocationRelativeTo(null);
		janela.setLayout(new BorderLayout());

		adicionarItemInicial("Maça");
		adicionarItemInicial("Banana");
		adicionarItemInicial("Laranja");
		adicionarItemInicial("Uva");

		JPanel painelTopo = new JPanel(new BorderLayout(8,8));
		JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 8,8));
		painelPesquisa.add(new JLabel("Pesquisar:"));
		campoPesquisa = new JTextField(20);

		JButton botaoLimparBusca = new JButton("Limpar");

		painelPesquisa.add(campoPesquisa);
		painelPesquisa.add(botaoLimparBusca);

		rotuloContagem = new JLabel("Itens: 0");

		JPanel painelDireitaTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8,8));
		painelDireitaTopo.add(rotuloContagem);

		painelTopo.add(painelPesquisa, BorderLayout.WEST);
		painelTopo.add(painelDireitaTopo, BorderLayout.EAST);

		janela.add(painelTopo, BorderLayout.NORTH);

		modeloFiltrado = new DefaultListModel<>();
		lista = new JList<>(modeloFiltrado);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setVisibleRowCount(12);
		lista.setFixedCellHeight(24);
		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					Item selecionado = lista.getSelectedValue();

					if(selecionado != null) {
						campoNome.setText(selecionado.nome);
					}
				}
			}
		});

		lista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					excluirSelecionado();
				}
			}
		});

		JScrollPane rolagem = new JScrollPane(lista);
		janela.add(rolagem, BorderLayout.CENTER);

		JPanel painelRodape = new JPanel(new GridBagLayout());
		painelRodape.setBorder(BorderFactory.createTitledBorder("Cadastro simples"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8,8,8,8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelRodape.add(new JLabel("Nome: "), gbc);

		campoNome = new JTextField(30);
		gbc.gridx = 1; gbc.gridy = 0;
		gbc.weightx = 1.0;
		painelRodape.add(campoNome, gbc);

		JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		JButton btnAdicionar = new JButton("Adicionar");
		JButton btnAlterar = new JButton("Alterar");
		JButton btnExcluir = new JButton("Excluir");
		JButton btnLimpar = new JButton("Limpar");

		painelBotao.add(btnAdicionar);
		painelBotao.add(btnAlterar);
		painelBotao.add(btnExcluir);
		painelBotao.add(btnLimpar);

		gbc.gridx = 0; gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weighty = 0;

		painelRodape.add(painelBotao, gbc);

		janela.add(painelRodape, BorderLayout.SOUTH);

		campoPesquisa.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			aplicarFiltro();
			}
		});

		botaoLimparBusca.addActionListener(e ->{
			campoPesquisa.setText("");
			aplicarFiltro();
		});

btnAdicionar.addActionListener(e -> {
	String nome = campoNome.getText().trim();

	if(nome.isBlank()) {
		JOptionPane.showMessageDialog(janela, "Informe o nome do item!");

		return;
	}

		dados.add(new Item(proximoId++, nome));
		campoNome.setText("");
		aplicarFiltro();

		JOptionPane.showMessageDialog(janela, "Item adicionado com sucesso!");
	});

		btnAlterar.addActionListener(e -> alterarSelecionado());
		btnExcluir.addActionListener(e -> excluirSelecionado());
		btnLimpar.addActionListener(e -> campoNome.setText(""));
		aplicarFiltro();

		janela.setVisible(true);
	}

	private void adicionarItemInicial(String nome) {
		dados.add(new Item(proximoId++, nome));
	}

	private void aplicarFiltro() {
		String textoPesquisa = campoPesquisa == null ? "" :
						campoPesquisa.getText().trim().toLowerCase();

		modeloFiltrado.clear();

		for(Item item : dados) {
			if(textoPesquisa.isEmpty() || item.nome.toLowerCase().contains(textoPesquisa)) {
				modeloFiltrado.addElement(item);
			}}
		atualizarContagem();
		}


	private void atualizarContagem() {
	rotuloContagem.setText("Itens: " + modeloFiltrado.getSize() + " / " + dados.size());
	}

	private void alterarSelecionado() {
		Item selecionado = lista.getSelectedValue();

		if(selecionado == null) {
			JOptionPane.showMessageDialog(janela, "Nenhum item selecionado!");
			return;
		}
		String novoNome = campoNome.getText().trim();

		if(novoNome.isEmpty()) {
			JOptionPane.showMessageDialog(janela, "Digite o novo nome do item!");
			return;
		}

		selecionado.nome = novoNome;
		aplicarFiltro();

		JOptionPane.showMessageDialog(janela, "Item alterado com sucesso!");
	}
	private void excluirSelecionado() {
		Item selecionado = lista.getSelectedValue();
		if(selecionado == null) {
			JOptionPane.showMessageDialog(janela, "Nenhum item selecionado!");
			return;
		}

		int op = JOptionPane.showConfirmDialog(janela, "Excluir \"" + selecionado.nome + "\"?","Confirmação", JOptionPane.YES_NO_OPTION);

		if(op == JOptionPane.YES_OPTION) {
			dados.removeIf(i->i.id == selecionado.id);
			aplicarFiltro();

			JOptionPane.showMessageDialog(janela, "Item excluído com sucesso!");
		}
	}
}
