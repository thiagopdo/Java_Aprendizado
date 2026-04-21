package interfaca_grafica;

import javax.swing.*;
import java.awt.*;

public class Aula11_JTable_Tabs {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JTablePane - Aula 11");
		janela.setSize(600,400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane abas = new JTabbedPane();
		JTextArea area = new JTextArea("Aba de text...\nDigite aqui!");

		abas.addTab("Texto", new JScrollPane(area));

		String[] itens = {"Item 1", "Item 2", "Item 3"};

		JList<String> lista = new JList<>(itens);
		abas.addTab("Lista", new JScrollPane(lista));

		JPanel config = new JPanel(new FlowLayout());
		config.add(new JLabel("Opção: "));
		config.add(new Checkbox("Ativar"));
		abas.addTab("Config", config);
		janela.add(abas);
		janela.setVisible(true);
	}
}
