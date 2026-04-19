package interfaca_grafica;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class Aula1 {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Componentes básicos");

		janela.setSize(500, 400);

		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		janela.setLayout(new GridLayout(0,2,10,10));

		JButton btnLabel = new JButton("Exemplo Jlabel");
		JButton btnLabel1 = new JButton("Exemplo JBUTTON");
		JButton btnLabel2 = new JButton("Exemplo JTEXTFIELD");
		JButton btnLabel3 = new JButton("Exemplo JTEXTAREA");
		JButton btnLabel4 = new JButton("Exemplo JPASSWORDFIELD");
		JButton btnLabel5 = new JButton("Exemplo JCHECKBOX");
		JButton btnLabel6 = new JButton("Exemplo JLIST");
		JButton btnLabel7 = new JButton("Exemplo JTABLE");
		JButton btnLabel8 = new JButton("Exemplo JTREE");
		JButton btnLabel9 = new JButton("Exemplo RADIOBTN");
		JButton btnLabel10 = new JButton("Exemplo COMBOBOX");

		janela.add(btnLabel);
		janela.add(btnLabel1);
		janela.add(btnLabel2);
		janela.add(btnLabel3);
		janela.add(btnLabel4);
		janela.add(btnLabel5);
		janela.add(btnLabel6);
		janela.add(btnLabel7);
		janela.add(btnLabel8);
		janela.add(btnLabel9);
		janela.add(btnLabel10);

		btnLabel.addActionListener(e-> JOptionPane.showMessageDialog(janela,
					new JLabel("Sou jlabel (rótulo de text)")));

		btnLabel1.addActionListener(e-> {
			JButton botao = new JButton("Clique aqui");
			JOptionPane.showMessageDialog(janela, botao, "Exemplo JButton", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel2.addActionListener(e-> {
			JTextField campo = new JTextField(10);
			JOptionPane.showMessageDialog(janela, campo, "Digite algo JTEXTFILED", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel3.addActionListener(e-> {
			JTextArea area = new JTextArea(5,20);
			JScrollPane rolagem = new JScrollPane(area);
			JOptionPane.showMessageDialog(janela, rolagem, "Digite varias linhas", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel4.addActionListener(e-> {
			JPasswordField senha = new JPasswordField(10);
			JOptionPane.showMessageDialog(janela, senha, "Digite sua senha", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel5.addActionListener(e-> {
			JCheckBox check = new JCheckBox("Marque aqui");
			JOptionPane.showMessageDialog(janela, check, "Marque aqui", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel6.addActionListener(e-> {
			String[] itens = {"Item 1", "Item 2", "Item 3"};
			JList<String> lista = new JList<>(itens);
			JScrollPane rolagem = new JScrollPane(lista);
			JOptionPane.showMessageDialog(janela, rolagem, "Exemplo Lista", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel7.addActionListener(e-> {
			String[] colunas = {"Coluna 1", "Coluna 2", "Coluna 3"};
			Object[][] dados = {
							{1, "Item 2", "Item 3"},
							{2, "Item 5", "Item 6"},
							{3, "Item 7", "Item 8"}
			};
			JTable tabela = new JTable(dados, colunas);
			JScrollPane rolagem = new JScrollPane(tabela);
			JOptionPane.showMessageDialog(janela, rolagem, "Exemplo Tabela", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel8.addActionListener(e-> {
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Raiz");
			DefaultMutableTreeNode raiz1 = new DefaultMutableTreeNode("Documentos");
			DefaultMutableTreeNode raiz2 = new DefaultMutableTreeNode("Imagens");
			raiz.add(raiz1);
			raiz.add(raiz2);
			JTree tree = new JTree(raiz);
			JScrollPane rolagem = new JScrollPane(tree);
			JOptionPane.showMessageDialog(janela, rolagem, "Exemplo JTREE", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel9.addActionListener(e-> {
			JRadioButton opc1 = new JRadioButton("Opção 1");
			JRadioButton opc2 = new JRadioButton("Opção 2");
			ButtonGroup grupo = new ButtonGroup();
			grupo.add(opc1);
			grupo.add(opc2);
			JPanel painel = new JPanel();
			painel.add(opc1);
			painel.add(opc2);
			JOptionPane.showMessageDialog(janela, painel, "Escolha uma opção", JOptionPane.PLAIN_MESSAGE);
		});

		btnLabel10.addActionListener(e-> {
			String[] itens = {"Item 1", "Item 2", "Item 3"};
			JComboBox combo = new JComboBox(itens);
			JOptionPane.showMessageDialog(janela, combo, "Escolha um item", JOptionPane.PLAIN_MESSAGE);
		});

		janela.setVisible(true);
	}
}
