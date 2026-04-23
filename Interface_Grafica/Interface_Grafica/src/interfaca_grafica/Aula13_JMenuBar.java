package interfaca_grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Aula13_JMenuBar {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JMenuBar");
		janela.setSize(600, 400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout());

		JLabel barraStatus = new JLabel("Barra de Status");
		barraStatus.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

		janela.add(barraStatus, BorderLayout.SOUTH);

		JMenuBar barraMenu = new JMenuBar();
		JMenu menuArquivo = new JMenu("Arquivo");
		JMenu menuEdit = new JMenu("Editar");
		JMenu menuExibir = new JMenu("Exibir");
		JMenu menuAjuda = new JMenu("Ajuda");

		menuArquivo.setMnemonic(KeyEvent.VK_A);
		menuEdit.setMnemonic(KeyEvent.VK_E);
		menuExibir.setMnemonic(KeyEvent.VK_X);
		menuAjuda.setMnemonic(KeyEvent.VK_J);

		JMenuItem itemNovo = new JMenuItem("Novo");
		JMenuItem itemAbrir = new JMenuItem("Abrir");
		JMenuItem itemSalvar = new JMenuItem("Salvar");
		JMenuItem itemSair = new JMenuItem("Sair");

		itemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		itemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		itemSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		itemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

		JMenu menuRecentes = new JMenu("Recentes");
		JMenuItem itemRecente1 = new JMenuItem("Projeto.txt");
		JMenuItem itemRecente2 = new JMenuItem("Relatorio.docx");
		JMenuItem itemRecente3 = new JMenuItem("Apresentacao.pptx");

		menuRecentes.add(itemRecente1);
		menuRecentes.add(itemRecente2);
		menuRecentes.add(itemRecente3);

		menuArquivo.add(itemNovo);
		menuArquivo.add(itemAbrir);
		menuArquivo.add(itemSalvar);
		menuArquivo.add(menuRecentes);
		menuArquivo.addSeparator();

		menuArquivo.add(itemSair);

		JMenuItem itemRecortar = new JMenuItem("Recortar");
		JMenuItem itemCopiar = new JMenuItem("Copiar");
		JMenuItem itemColar = new JMenuItem("Colar");

		menuEdit.add(itemRecortar);
		menuEdit.add(itemCopiar);
		menuEdit.add(itemColar);

		JCheckBoxMenuItem itemStatus = new JCheckBoxMenuItem("Mostrar barra de status", true);
		menuExibir.add(itemStatus);

		JMenuItem itemSobre = new JMenuItem("Sobre...");
		menuAjuda.add(itemSobre);

		itemNovo.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Novo"));
		itemAbrir.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Abrir"));
		itemSalvar.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Salvar"));
		itemRecente1.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Recentes > Projeto.txt"));
		itemRecente2.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Recentes > Relatorio.docx"));
		itemRecente3.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Arquivo > Recentes > Apresentacao.pptx"));
		itemRecortar.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Editar > Recortar"));
		itemCopiar.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Editar > Copiar"));
		itemColar.addActionListener(e -> JOptionPane.showMessageDialog(janela, "Editar > Colar"));

		itemStatus.addActionListener(e -> {
			boolean visivel = itemStatus.isSelected();
			barraStatus.setVisible(visivel);

			JOptionPane.showMessageDialog(janela, "Exibir > Mostrar barra de status: " + (visivel ?
							"Ativado" : "Desativado"));
		});

		itemSobre.addActionListener(e -> {
			JOptionPane.showMessageDialog(janela, "Exemplo de JMenuBar\nVersao 1.0", "Sobre", JOptionPane.INFORMATION_MESSAGE);
		});


		barraMenu.add(menuArquivo);
		barraMenu.add(menuEdit);
		barraMenu.add(menuExibir);
		barraMenu.add(menuAjuda);


		janela.setJMenuBar(barraMenu);
		janela.add(new JScrollPane(new JTextArea("Area de trabalho...\nUse os menus acima")), BorderLayout.CENTER);


		janela.setVisible(true);
	}
}
