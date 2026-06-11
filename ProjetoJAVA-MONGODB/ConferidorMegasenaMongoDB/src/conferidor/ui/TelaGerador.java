package conferidor.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

import conferidor.dao.JogoDAO;
import conferidor.modelo.Jogo;

public class TelaGerador extends JFrame {
	private final JLabel lbl = new JLabel("", SwingConstants.CENTER);
	private final JSpinner spQtd;
	private java.util.List<Integer> dezenasGeradas = new ArrayList<>();

	public TelaGerador() {
		EstiloUI.aplicar(this, "Gerador de Jogos");

		add(EstiloUI.titulo("Gerador de Jogos Aleatórios"), BorderLayout.NORTH);

		JPanel topo = EstiloUI.painelComPadding();
		topo.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));

		topo.add(new JLabel("Qtd. dezenas (6-20):"));

		spQtd = new JSpinner(new SpinnerNumberModel(6, 6, 20, 1));

		topo.add(spQtd);

		add(topo, BorderLayout.NORTH);

		lbl.setFont(new Font("monospaced", Font.BOLD, 24));

		add(lbl, BorderLayout.CENTER);

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));

		botoes.setOpaque(false);

		JButton btnGerar = new JButton("Gerar");
		JButton btnSalvar = new JButton("Salvar");
		JButton btnVoltar = new JButton("Voltar");

		botoes.add(btnGerar);
		botoes.add(btnSalvar);
		botoes.add(btnVoltar);

		add(botoes, BorderLayout.SOUTH);

		btnVoltar.addActionListener(e -> dispose());

		btnGerar.addActionListener(e -> gerar());

		btnSalvar.addActionListener(e -> salvar());

		gerar();

		setSize(700, 340);

		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void salvar() {
		try {

			new JogoDAO().inserir(new Jogo(dezenasGeradas, LocalDate.now()));

			JOptionPane.showMessageDialog(this,
							"Jogo salvo!");

		}catch(Exception e) {
			JOptionPane.showMessageDialog(this,
							"Erro: " + e.getMessage());
		}
	}

	private void gerar() {
		int qtd = (int) spQtd.getValue();

		Random r = new Random();

		TreeSet<Integer> set = new TreeSet<>();

		while(set.size() < qtd) {
			set.add(r.nextInt(60) + 1);
		}

		dezenasGeradas = new ArrayList<>(set);

		lbl.setText(dezenasGeradas.toString());
	}
}
