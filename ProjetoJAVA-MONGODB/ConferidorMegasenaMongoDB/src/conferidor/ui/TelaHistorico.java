package conferidor.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

import conferidor.dao.JogoDAO;
import conferidor.modelo.Jogo;
import org.bson.types.ObjectId;


public class TelaHistorico extends JFrame {
	private static final int COL_ID_OCULTO = 0;
	private static final int COL_ID_CURTO = 1;
	private static final int COL_DATA = 2;
	private static final int COL_DEZENAS = 3;

	private final DefaultTableModel modelo;

	private final JSpinner spData;

	public TelaHistorico() {
		EstiloUI.aplicar(this, "Histório de Jogos");

		add(EstiloUI.titulo("Histórico de Jogos Cadastrados"), BorderLayout.NORTH);

		JPanel filtro = EstiloUI.painelComPadding();
		filtro.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
		filtro.add(new JLabel("Data do Sorteio:"));

		spData = new JSpinner(new SpinnerDateModel(
						java.sql.Date.valueOf(LocalDate.now()),
						null,
						null,
						java.util.Calendar.DAY_OF_MONTH
		));

		spData.setEditor(new JSpinner.DateEditor(spData, "dd/MM/yyyy"));

		filtro.add(spData);

		JButton btnBuscar = new JButton("Buscar");
		JButton btnLimpar = new JButton("Limpar Filtro");

		filtro.add(btnBuscar);
		filtro.add(btnLimpar);

		add(filtro, BorderLayout.NORTH);

		String[] colunas = { "_ID", "ID", "Data", "Dezenas" };

		modelo = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		JTable tabela = new JTable(modelo);
		tabela.setRowHeight(28);
		tabela.getColumnModel().
						getColumn(COL_ID_OCULTO)
						.setMinWidth(0);
		tabela.getColumnModel()
						.getColumn(COL_ID_OCULTO)
						.setMaxWidth(0);

		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel()
						.getColumn(COL_ID_CURTO)
						.setCellRenderer(centro);

		tabela.getColumnModel()
						.getColumn(COL_DATA)
						.setCellRenderer(centro);
		add(new JScrollPane(tabela), BorderLayout.CENTER);

		JButton excluir = new JButton("Excluir Jogo");
		JButton voltar = new JButton("Voltar");

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));

		botoes.setOpaque(false);
		botoes.add(voltar);
		botoes.add(excluir);

		add(botoes, BorderLayout.SOUTH);

		voltar.addActionListener(e -> dispose());

		excluir.addActionListener(e -> excluirSelecionado(tabela));

		btnBuscar.addActionListener(e -> aplicarFiltro());

		btnLimpar.addActionListener(e -> limparFiltro());

		carregarTodos();

		setSize(940, 540);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void limparFiltro() {
		carregarTodos();
	}

	private void aplicarFiltro() {
		java.util.Date dataSelecionada = (java.util.Date) spData.getValue();

		LocalDate d = dataSelecionada
						.toInstant()
						.atZone(java.time.ZoneId.systemDefault())
						.toLocalDate();

		java.util.List<Jogo> lista = new JogoDAO().buscarPorData(d);

		modelo.setRowCount(0);

		for(Jogo jogo : lista) {
			modelo.addRow(new Object[]{
											jogo.getId(),
											jogo.getIdCurto(),
											jogo.getDataFormatada(),
											jogo.dezenasComoString()
							}
			);
		}

		if(lista.isEmpty()) {
			JOptionPane.showMessageDialog(this,
							"Nenhum jogo encontrado com a data informada!");
		}
	}

	private void carregarTodos() {
		modelo.setRowCount(0);

		for(Jogo jogo : new JogoDAO().listarTodos()) {
			modelo.addRow(new Object[]{
											jogo.getId(),
											jogo.getIdCurto(),
											jogo.getDataFormatada(),
											jogo.dezenasComoString()
							}
			);
		}
	}

	private void excluirSelecionado(JTable tabela) {
		int sel = tabela.getSelectedRow();

		if(sel < 0)
			return;

		ObjectId id = (ObjectId) modelo.getValueAt(sel, COL_ID_OCULTO);

		int resposta = JOptionPane.showConfirmDialog(this,
						"Excluir o jogo" + modelo.getValueAt(sel, COL_ID_OCULTO) + "?",
						"Confirmarção",
						JOptionPane.YES_NO_OPTION);

		if(resposta == JOptionPane.YES_OPTION) {
			new JogoDAO().excluirPorId(id);
			modelo.removeRow(sel);
		}
	}
}