package interfaca_grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class Aula11_JTable {

	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JTable - Aula 11");
		janela.setSize(600,400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout(10,10));

		String[] colunas = {"ID", "Nome"};
		Object[][] dados = {{1, "Ana"}, {2, "Thiago"}, {3, "Maria"}};

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
		JTable tabela = new JTable(modelo);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setRowHeight(25);

		TableColumnModel colunasTabela = tabela.getColumnModel();
		colunasTabela.getColumn(0).setPreferredWidth(50);
		colunasTabela.getColumn(1).setPreferredWidth(200);

		JScrollPane rolagem = new JScrollPane(tabela);
		JPanel painelBotoes = new JPanel(new FlowLayout());
		JButton botaoMostrar = new JButton("Mostrar Seleção");
		botaoMostrar.addActionListener(e->{
			int linha = tabela.getSelectedRow();

			if(linha != -1) {
				int id = (int) tabela.getValueAt(linha, 0);
				String nome = (String) tabela.getValueAt(linha, 1);

				JOptionPane.showMessageDialog(janela, "ID: " + id + " | Nome: " + nome);
			} else{
				JOptionPane.showMessageDialog(janela, "Nenhuma linha selecionada!");
			}
		});

		JButton botaoAdicionar = new JButton("Adicionar");
		botaoAdicionar.addActionListener(e->{
			int proximoId = modelo.getRowCount() + 1;
			modelo.addRow(new Object[]{proximoId, "Novo Nome " + proximoId});
		});

		painelBotoes.add(botaoMostrar);
		painelBotoes.add(botaoAdicionar);

		janela.add(rolagem, BorderLayout.CENTER);
		janela.add(painelBotoes, BorderLayout.SOUTH);
		janela.setVisible(true);
	}

}
