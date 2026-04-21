package interfaca_grafica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

public class Aula11_JTable_CRUD {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Aula11_JTable_CRUD::criarTabela);
	}

	private static void criarTabela() {
		JFrame janela = new JFrame("JTable - CRUD");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(900, 500);
		janela.setLocationRelativeTo(null);
		janela.setLayout(new BorderLayout(10, 10));

		String[] colunas = {"ID", "Nome"};
		DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Integer.class : String.class;
			}
		};

		modelo.addRow(new Object[]{1, "Alice"});
		modelo.addRow(new Object[]{2, "Bob"});
		modelo.addRow(new Object[]{3, "Charlie"});

		JTable tabela = new JTable(modelo);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setRowHeight(24);
		tabela.setAutoCreateRowSorter(true);

		TableColumnModel colunasTabela = tabela.getColumnModel();
		colunasTabela.getColumn(0).setPreferredWidth(80);
		colunasTabela.getColumn(1).setPreferredWidth(300);

		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

		tabela.setRowSorter(sorter);

		JScrollPane rolagem = new JScrollPane(tabela);

		janela.add(rolagem, BorderLayout.CENTER);

		JPanel topo = new JPanel(new BorderLayout(8, 8));
		JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
		painelBusca.add(new JLabel("Pesquisar: "));

		JTextField campoPesquisa = new JTextField(25);

		JButton botaoLimparPesquisa = new JButton("Limpar");

		painelBusca.add(campoPesquisa);
		painelBusca.add(botaoLimparPesquisa);

		JLabel lblContagem = new JLabel("Itens: 0 | Total: 0");

		JPanel painelContagem = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		painelContagem.add(lblContagem);

		topo.add(painelBusca, BorderLayout.WEST);
		topo.add(painelContagem, BorderLayout.EAST);

		janela.add(topo, BorderLayout.NORTH);

		DocumentListener listenerFiltro = new DocumentListener() {
			private void aplicar() {
				String termo = campoPesquisa.getText().trim();
				if(termo.isEmpty()) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(termo)));
				}
				atualizarContagem(tabela, modelo, lblContagem);
			}

			public void insertUpdate(DocumentEvent e) {
				aplicar();
			}

			public void removeUpdate(DocumentEvent e) {
				aplicar();
			}

			public void changedUpdate(DocumentEvent e) {
				aplicar();
			}
		};

		campoPesquisa.getDocument().addDocumentListener(listenerFiltro);
		botaoLimparPesquisa.addActionListener(e -> {
			campoPesquisa.setText("");
			sorter.setRowFilter(null);
			atualizarContagem(tabela, modelo, lblContagem);
		});

		/**
		 * SECAO RODAPE
		 * Formulário e botoes de CRUD
		 */

		JPanel rodape = new JPanel(new GridBagLayout());
		rodape.setBorder(BorderFactory.createTitledBorder("Cadastro simples"));

		//armazena restriçoes(pos, preenchimento e pesos) a cada componente added no GRIDBAG
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		//Entrada ID
		//cria o rotulo e o campo do texto ID
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		rodape.add(new JLabel("ID: "), gbc);

		JTextField campoId = new JTextField(10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		rodape.add(campoId, gbc);


		//ENtrada Nome
		//cria o rotulo e o campo do texto Nome
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		rodape.add(new JLabel("Nome: "), gbc);

		JTextField campoNome = new JTextField(30);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		rodape.add(campoNome, gbc);

		//botoes de açao CRUD
		//bloco cria botoes de ação CRUD e os posiciona no canto inferior direito do rodape usando um painel auxiliar com FlowLayout
		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		JButton btnAdicionar = new JButton("Adicionar");
		JButton btnAlterar = new JButton("Alterar");
		JButton btnExcluir = new JButton("Excluir");
		JButton btnLimpar = new JButton("Limpar campos");

		botoes.add(btnAdicionar);
		botoes.add(btnAlterar);
		botoes.add(btnExcluir);
		botoes.add(btnLimpar);


		//posicionamento dos botoes no rodapé
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weightx = 0;

		rodape.add(botoes, gbc);
		janela.add(rodape, BorderLayout.SOUTH);

		atualizarContagem(tabela, modelo, lblContagem);

		tabela.getSelectionModel().addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting()) {
				int viewRow = tabela.getSelectedRow();
				if(viewRow >= 0) {
					int modelRow = tabela.convertRowIndexToModel(viewRow);
					Object id = modelo.getValueAt(modelRow, 0);
					Object nome = modelo.getValueAt(modelRow, 1);

					campoId.setText(String.valueOf(id));
					campoNome.setText(nome == null ? "" : nome.toString());
				}
			}
		});
		tabela.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int row = tabela.getSelectedRow();

					if(row >= 0) {
						int m = tabela.convertRowIndexToModel(row);
						campoId.setText(String.valueOf(modelo.getValueAt(m, 0)));
						campoNome.setText(String.valueOf(modelo.getValueAt(m, 1)));
					}
				}
			}
		});

		tabela.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("DELETE"), "ExcluirLinha");

		tabela.getActionMap().put("excluirLinha", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				excluirSelecionado(janela, tabela, modelo, lblContagem);
			}
		});

		btnAdicionar.addActionListener(e -> {

			String sId = campoId.getText().trim();
			String nome = campoNome.getText().trim();

			if(sId.isEmpty() || nome.isEmpty()) {
				JOptionPane.showMessageDialog(janela, "Preencha ID e Nome.");
				return;
			}

			Integer id = 0;
			try {

				id = Integer.parseInt(sId);

			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(janela, "ID deve ser um número inteiro.");
			}

			if(existeId(modelo, id)) {
				JOptionPane.showMessageDialog(janela, "ID já existe. Use outro.");
				return;
			}

			modelo.addRow(new Object[]{id, nome});

			limparCampos(campoId, campoNome);
			atualizarContagem(tabela, modelo, lblContagem);
		});

		btnAlterar.addActionListener(e -> {
			int viewRow = tabela.getSelectedRow();
			if(viewRow < 0) {
				JOptionPane.showMessageDialog(janela, "Selecione uma linha para alterar.");
				return;
			}

			String sId = campoId.getText().trim();
			String nome = campoNome.getText().trim();
			if(sId.isEmpty() || nome.isEmpty()) {
				JOptionPane.showMessageDialog(janela, "Preencha ID e Nome.");
				return;
			}

			Integer idNovo;
			try {
				idNovo = Integer.valueOf(sId);

			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(janela, "ID deve ser um número inteiro.");
				return;
			}
			int modelRow = tabela.convertRowIndexToModel(viewRow);

			Integer idAtual = (Integer) modelo.getValueAt(modelRow, 0);

			//verificar duplicidade do ID
			if(!idNovo.equals(idAtual) && existeId(modelo, idNovo)) {
				JOptionPane.showMessageDialog(janela, "ID já existe. Use outro.");
				return;
			}

			//atualiza valor do ID na linha correspondente do modelo
			modelo.setValueAt(idNovo, modelRow, 0);
			//atualiza o valor do Nome na mesma linha do modelo
			modelo.setValueAt(nome, modelRow, 1);

			atualizarContagem(tabela, modelo, lblContagem);

			JOptionPane.showMessageDialog(janela, "Registro alterado com sucesso!");

		});

		//BOTAO EXCLUIR
		btnExcluir.addActionListener(e -> {
			excluirSelecionado(janela, tabela, modelo, lblContagem);
		});

		//BOTAO LIMPAR CAMPOS
		btnLimpar.addActionListener(e -> limparCampos(campoId, campoNome));


		janela.setVisible(true);
	}

	private static void atualizarContagem(JTable tabela, DefaultTableModel modelo, JLabel lbl) {
		int total = modelo.getRowCount();
		int exibidos = tabela.getRowCount();
		lbl.setText("Itens: " + exibidos + " | Total: " + total);
	}

	private static boolean existeId(DefaultTableModel modelo, Integer id) {
		for(int i = 0; i < modelo.getRowCount(); i++) {
			Object valor = modelo.getValueAt(i, 0);
			if(valor instanceof Integer && valor == id) return true;
		}
		return false;
	}

	private static void excluirSelecionado(JFrame janela, JTable tabela, DefaultTableModel modelo, JLabel lbl) {
		int viewRow = tabela.getSelectedRow();
		if(viewRow < 0) {
			JOptionPane.showMessageDialog(janela, "Selecione uma linha para excluir.");
			return;
		}
		int op = JOptionPane.showConfirmDialog(janela, "Confirma exclusão?", "Excluir", JOptionPane.YES_NO_OPTION);

		if(op == JOptionPane.YES_NO_OPTION) {
			int modelRow = tabela.convertRowIndexToModel(viewRow);
			modelo.removeRow(modelRow);
			atualizarContagem(tabela, modelo, lbl);
		}
	}

	private static void limparCampos(JTextField campoId, JTextField campoNome) {
		campoId.setText("");
		campoNome.setText("");
		campoId.requestFocus();
	}

}
