package interfaca_grafica;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class Aula12_JTree {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JTree");
		janela.setSize(600, 400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout(8, 8));

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Pastas");
		DefaultMutableTreeNode documentos = new DefaultMutableTreeNode("Documentos");

		documentos.add(new DefaultMutableTreeNode("Relatório.docx"));
		documentos.add(new DefaultMutableTreeNode("Apresentação.pptx"));

		DefaultMutableTreeNode imagens = new DefaultMutableTreeNode("Imagens");
		imagens.add(new DefaultMutableTreeNode("Logo.png"));
		imagens.add(new DefaultMutableTreeNode("Foto.png"));

		raiz.add(documentos);
		raiz.add(imagens);

		DefaultTreeModel modelo = new DefaultTreeModel(raiz);
		JTree tree = new JTree(modelo);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		JScrollPane rolagem = new JScrollPane(tree);

		JLabel lblCaminho = new JLabel("Selecione um nó...");
		lblCaminho.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		tree.addTreeSelectionListener(e -> {
			TreePath path = e.getPath();
			if(path != null) {
				lblCaminho.setText("Caminho: " + pathToString(path));
			} else {
				lblCaminho.setText("Selecione um nó...");
			}
		});

		JPanel rodape = new JPanel(new GridBagLayout());
		rodape.setBorder(BorderFactory.createTitledBorder("Ações"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;

		JTextField campoNome = new JTextField(30);
		rodape.add(campoNome, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0;

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		JButton btnAdicionar = new JButton("Adicionar filho");
		JButton btnRenomear = new JButton("Renomear");
		JButton btnRemover = new JButton("Remover");

		botoes.add(btnAdicionar);
		botoes.add(btnRenomear);
		botoes.add(btnRemover);
		rodape.add(botoes, gbc);

		btnAdicionar.addActionListener(e -> {
			DefaultMutableTreeNode selecionado = getSelecionado(tree);

			if(selecionado == null) {
				JOptionPane.showMessageDialog(janela, "Adicionar filho a Pasta");
				return;
			}
			String nome = campoNome.getText().trim();

			if(nome.isEmpty()) {
				JOptionPane.showMessageDialog(janela, "Digite um nome para o novo nó.");
				return;
			}

			DefaultMutableTreeNode novoNode = new DefaultMutableTreeNode(nome);
			modelo.insertNodeInto(novoNode, selecionado, selecionado.getChildCount());

			TreePath caminho = new TreePath(modelo.getPathToRoot(novoNode));
			tree.scrollPathToVisible(caminho);
			tree.setSelectionPath(caminho);
			campoNome.setText("");
		});

		btnRenomear.addActionListener(e -> {
			DefaultMutableTreeNode selecionado = getSelecionado(tree);

			if(selecionado == null) {
				JOptionPane.showMessageDialog(janela, "Selecione um nó para renomear.");
				return;
			}
			if(selecionado.isRoot()) {
				JOptionPane.showMessageDialog(janela, "Não é possível renomear a raiz.");
				return;
			}

			String novoNome = campoNome.getText().trim();
			if(novoNome.isEmpty()) {
				JOptionPane.showMessageDialog(janela, "Digite um novo nome.");
				return;
			}

			selecionado.setUserObject(novoNome);
			modelo.nodeChanged(selecionado);
			tree.setSelectionPath(new TreePath(modelo.getPathToRoot(selecionado)));

			campoNome.setText("");
		});

		btnRemover.addActionListener(e -> {
			DefaultMutableTreeNode selecionado = getSelecionado(tree);

			if(selecionado == null) {
				JOptionPane.showMessageDialog(janela, "Selecione um nó para remover.");
				return;
			}

			if(selecionado.isRoot()) {
				JOptionPane.showMessageDialog(janela, "Não é possível remover a raiz.");
				return;
			}

			int op = JOptionPane.showConfirmDialog(janela, "Tem certeza que deseja remover " + selecionado.getUserObject() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);

			if(op == JOptionPane.YES_OPTION) {
				MutableTreeNode pai = (MutableTreeNode) selecionado.getParent();
				int idx = pai != null ? ((DefaultMutableTreeNode) pai).getIndex(selecionado) : -1;

				modelo.removeNodeFromParent(selecionado);
				if(pai != null) {
					TreePath caminhoPai = new TreePath(modelo.getPathToRoot(pai));

					tree.setSelectionPath(caminhoPai);
					tree.scrollRowToVisible(Math.max(0, idx - 1));
				}
			}
		});

		tree.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode sel = getSelecionado(tree);

			if(sel != null) {
				campoNome.setText(String.valueOf(sel.getUserObject()));
			}
		});


		janela.add(lblCaminho, BorderLayout.NORTH);
		janela.add(rolagem, BorderLayout.CENTER);
		janela.add(rodape, BorderLayout.SOUTH);

		expandirTudo(tree);


		janela.setVisible(true);

	}

	private static DefaultMutableTreeNode getSelecionado(JTree tree) {
		TreePath path = tree.getSelectionPath();
		if(path == null) return null;

		return (DefaultMutableTreeNode) path.getLastPathComponent();
	}

	private static String pathToString(TreePath path) {
		Object[] nodes = path.getPath();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < nodes.length; i++) {
			sb.append(nodes[i]);
			if(i < nodes.length - 1) sb.append(" > ");
		}

		return sb.toString();
	}

	private static void expandirTudo(JTree tree) {
		for(int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

}
