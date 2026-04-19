package interfaca_grafica;

import javax.swing.*;
import java.awt.*;


public class Aula10_JList {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JList");

		janela.setSize(500,300);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new GridLayout(3,1,10,10));

		JPanel painelSimples = new JPanel(new BorderLayout());
		painelSimples.setBorder(BorderFactory.createTitledBorder("JList Simples"));

		String[] frutas = {"Maças", "Banana", "Laranja", "Morango", "Uva"};

		JList<String> listaSimples = new JList<>(frutas);

		listaSimples.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton botaMostrarSimples = new JButton("Mostrar Seleção");
		botaMostrarSimples.addActionListener(e->{
			String selecionado = listaSimples.getSelectedValue();

			JOptionPane.showMessageDialog(janela, selecionado != null ? "Voce escolheu: " + selecionado : "Nenhum item selecionado!");
		});

		painelSimples.add(new JScrollPane(listaSimples), BorderLayout.CENTER);
		painelSimples.add(botaMostrarSimples, BorderLayout.SOUTH);

		//painel multiplas escolhas
		JPanel painelMultipla = new JPanel(new BorderLayout());
		painelMultipla.setBorder(BorderFactory.createTitledBorder("JList Multipla"));

		String[] core = {"Vermelho", "Verde", "Azul", "Amarelo", "Preto", "Branco"};

		JList<String> listaMultipla = new JList<>(core);
		listaMultipla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JButton botaoMostrarMultiplas = new JButton("Mostrar seleções");

		botaoMostrarMultiplas.addActionListener(e-> {
			java.util.List<String> selecionados = listaMultipla.getSelectedValuesList();

			if(selecionados.isEmpty()) {
				JOptionPane.showMessageDialog(janela, "Nenhum item selecionado!");
			} else {
				JOptionPane.showMessageDialog(janela, "Voce escolheu: " + selecionados);
			}
		});
		painelMultipla.add(new JScrollPane(listaMultipla), BorderLayout.CENTER);
		painelMultipla.add(botaoMostrarMultiplas, BorderLayout.SOUTH);

		//painel fixado
		JPanel painelFixado = new JPanel(new BorderLayout());
		painelFixado.setBorder(BorderFactory.createTitledBorder("JList Fixado"));

		String[] animais = {"Cachorro", "Gato", "Peixe", "Tartaruga", "Cobra"};
		JList<String> listaFixado = new JList<>(animais);
		listaFixado.setVisibleRowCount(3);
		painelFixado.add(new JScrollPane(listaFixado), BorderLayout.CENTER);


		janela.add(painelSimples);
		janela.add(painelMultipla);
		janela.add(painelFixado);

		janela.setVisible(true);
 	}
}
