package conferidor.ui;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import conferidor.dao.JogoDAO;
import conferidor.modelo.Jogo;

public class TelaCadastroJogo extends JFrame {
	private final JPanel painelCampos = new JPanel();
	private final java.util.List<JTextField> campos = new ArrayList<>();
	private final JSpinner spQtd;
	private final JSpinner spData;

	public TelaCadastroJogo() {
		EstiloUI.aplicar(this, "Cadastrar Jogo");

		add(EstiloUI.titulo("Cadastrar Jogo"), BorderLayout.NORTH);

		JPanel topo = EstiloUI.painelComPadding();
		topo.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));
		topo.add(new JLabel("Qtd. dezendas (6-20):"));

		spQtd = new JSpinner(new SpinnerNumberModel(6, 6, 20, 1));
		topo.add(spQtd);

		topo.add(new JLabel("Data do Sorteio:"));

		spData = new JSpinner(new SpinnerDateModel(
						java.sql.Date.valueOf(LocalDate.now()),
						null,
						null,
						java.util.Calendar.DAY_OF_MONTH
		));

		spData.setEditor(new JSpinner.DateEditor(spData, "dd/MM/yyyy"));

		topo.add(spData);

		JButton btnGerarCampos = new JButton("Gerar Campos");

		topo.add(btnGerarCampos);

		add(topo, BorderLayout.NORTH);

		painelCampos.setOpaque(false);
		painelCampos.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

		add(painelCampos, BorderLayout.CENTER);

		btnGerarCampos.addActionListener(e -> criarCampos());

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
		botoes.setOpaque(false);

		JButton btnSalvar = new JButton("Salvar");
		JButton btnVoltar = new JButton("Voltar");

		botoes.add(btnSalvar);
		botoes.add(btnVoltar);
		add(botoes, BorderLayout.SOUTH);

		btnVoltar.addActionListener(e -> dispose());
		btnSalvar.addActionListener(e -> salvar());

		criarCampos();

		setSize(700, 640);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TelaCadastroJogo().setVisible(true));
	}

	private void criarCampos() {
		painelCampos.removeAll();
		campos.clear();

		int qtd = (int) spQtd.getValue();

		painelCampos.setLayout(new GridLayout(qtd, 2, 8, 8));

		for(int i = 1; i <= qtd; i++) {
			painelCampos.add(
							new JLabel("Número " + i + ":", SwingConstants.RIGHT)
			);

			JTextField txt = new JTextField();
			txt.setHorizontalAlignment(SwingConstants.CENTER);
			txt.setInputVerifier(new NumeroVerifier());

			campos.add(txt);

			painelCampos.add(txt);
		}

		painelCampos.revalidate();
		painelCampos.repaint();
	}

	private void salvar() {
		try {

			Set<Integer> numeros = new TreeSet<>();

			for(JTextField t : campos) {
				String s = t.getText().trim();

				if(s.isEmpty())
					throw new Exception("Preencha todos os números!");


				int n = Integer.parseInt(s);

				if(n < 1 || n > 60)
					throw new Exception("Numeros entre 1 e 60!");

				if(!numeros.add(n))
					throw new Exception("Não repita números iguais!");
			}

			LocalDate data = ((java.util.Date) spData.getValue())
							.toInstant()
							.atZone(java.time.ZoneId.systemDefault())
							.toLocalDate();

			new JogoDAO().inserir(new Jogo(numeros, data));

			JOptionPane.showMessageDialog(this, "Jogo salvo!");

			dispose();

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,
							e.getMessage(),
							"Erro",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	private static class NumeroVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent c) {
			JTextField campos = (JTextField) c;
			String texto = campos.getText().trim();

			return texto.matches("\\d{1,3}");
		}
	}
}

