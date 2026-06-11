package conferidor.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import conferidor.dao.JogoDAO;
import conferidor.modelo.Jogo;

public class TelaConferencia extends JFrame {
	private final JSpinner spData;
	private final JTextField[] campoNumero = new JTextField[6];
	private final DefaultTableModel modelo;


	public TelaConferencia() {
		EstiloUI.aplicar(this, "Conferir Jogos");

		add(EstiloUI.titulo("Conferidor Resultado manual"), BorderLayout.NORTH);

		JPanel topo = EstiloUI.painelComPadding();
		topo.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));

		topo.add(new JLabel("Data do Sorteio:"));

		spData = new JSpinner(new SpinnerDateModel(
						java.sql.Date.valueOf(LocalDate.now()),
						null,
						null,
						java.util.Calendar.DAY_OF_MONTH
		));

		spData.setEditor(new JSpinner.DateEditor(spData, "dd/MM/yyyy"));
		topo.add(spData);
		topo.add(Box.createHorizontalStrut(20));
		topo.add(new JLabel("Dezenas sorteadas:"));

		for(int i = 0; i < 6; i++) {
			JTextField t = new JTextField(2);
			t.setHorizontalAlignment(SwingConstants.CENTER);
			t.setInputVerifier(new NumberVerifier());

			campoNumero[i] = t;
			topo.add(t);
		}

		JButton btnConferir = new JButton("Conferir");
		topo.add(btnConferir);
		add(topo, BorderLayout.NORTH);

		String[] col = { "Jogo", "Qtd", "Dezenas", "Acertadas", "Acertos", "Prêmio" };
		modelo = new DefaultTableModel(col, 0) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		JTable tabela = new JTable(modelo);
		tabela.setRowHeight(28);

		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);

		for(int i : new int[]{ 0, 1, 4, 5 }) {
			tabela.getColumnModel()
							.getColumn(i)
							.setCellRenderer(centro);
		}

		tabela.setDefaultRenderer(
						Object.class,
						new PremioRenderer()
		);

		add(new JScrollPane(tabela), BorderLayout.CENTER);

		btnConferir.addActionListener(e -> conferir());

		setSize(980, 540);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void conferir() {
		try {

			int[] sorteadas = new int[6];

			Set<Integer> setSorteadas = new HashSet<>();

			for(int i = 0; i < 6; i++) {
				String s = campoNumero[i].getText().trim();

				if(s.isEmpty())
					throw new Exception("Preencha todas as dezenas sorteadas!");

				int n = Integer.parseInt(s);

				if(n < 1 || n > 60)
					throw new Exception("Dezenas entre 1 e 60!");

				if(!setSorteadas.add(n))
					throw new Exception("Dezenas repetidas!");

				sorteadas[i] = n;
			}

			java.util.Date dataSpinner = (java.util.Date) spData.getValue();

			LocalDate data = dataSpinner
							.toInstant()
							.atZone(java.time.ZoneId.systemDefault())
							.toLocalDate();

			List<Jogo> jogos = new JogoDAO().buscarPorData(data);

			if(jogos.isEmpty()) {
				JOptionPane.showMessageDialog(this,
								"Nenhum jogo cadastrado nessa data");

				modelo.setRowCount(0);
				return;
			}

			modelo.setRowCount(0);

			for(Jogo j : jogos) {
				List<Integer> acertadas = new ArrayList<>();

				for(int n : j.getDezenas()) {
					if(setSorteadas.contains(n)) {
						acertadas.add(n);
					}
				}

				int acertos = acertadas.size();

				String premio = switch(acertos) {
					case 6 -> "Sena";
					case 5 -> "Quina";
					case 4 -> "Quadra";
					default -> "-";
				};

				modelo.addRow(new Object[]{
								j.getIdCurto(),
								j.getDezenas().size(),
								j.dezenasComoString(),
								acertadas.toString(),
								acertos,
								premio
				});

				((PremioRenderer) ((JTable) ((JScrollPane) getContentPane().getComponent(2)).getViewport()
								.getView())
								.getDefaultRenderer(Object.class))
								.setColAcertos(4);
			}

		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
							"Digito apenas numeros",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(
							this,
							e.getMessage(),
							"Erro",
							JOptionPane.ERROR_MESSAGE
			);
		}
	}

	private static class NumberVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent c) {
			JTextField campo = (JTextField) c;
			String texto = campo.getText().trim();

			return texto.matches("\\d{1,2}");
		}
	}

	private static class PremioRenderer extends DefaultTableCellRenderer {
		private int colAcertos = 4;

		public void setColAcertos(int c) {
			this.colAcertos = c;
		}

		@Override
		public Component getTableCellRendererComponent(
						JTable table,
						Object value,
						boolean isSelected,
						boolean hasFocus,
						int row,
						int column
		) {
			super.getTableCellRendererComponent(
							table, value, isSelected, hasFocus, row, column
			);

			int ac = Integer.parseInt(
							table.getValueAt(row, colAcertos).toString()
			);

			setBackground(ac >= 4
							? new Color(255, 241, 150)
							: table.getBackground());

			return this;
		}
	}
}
