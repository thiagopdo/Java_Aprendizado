package interfaca_grafica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Aula14_JMenuTool {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Aula14_JMenuTool::criarTela);
	}

	private static void criarTela() {
		JFrame janela = new JFrame("Exemplo JMenuTool");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(600, 400);
		janela.setLocationRelativeTo(null);
		janela.setLayout(new BorderLayout());

		JTextArea area = new JTextArea();

		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(new Font("Arial", Font.PLAIN, 14));

		JScrollPane rolagem = new JScrollPane(area);

		janela.add(rolagem, BorderLayout.CENTER);

		JLabel status = new JLabel("Pronto");
		status.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		janela.add(status, BorderLayout.SOUTH);

		Action acNovo = new AbstractAction("+ Novo") {
			@Override
			public void actionPerformed(ActionEvent e) {
				area.setText("");
				status.setText("Novo documendo criado");

				area.requestFocusInWindow();
			}
		};
		acNovo.putValue(Action.SHORT_DESCRIPTION, "Novo (Ctrl+N");

		Action acSalvar = new AbstractAction("Salvar (Ctrl+S)") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String conteudo = area.getText();
				if(conteudo.isEmpty()) {
					JOptionPane.showMessageDialog(janela, "O documento está vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(janela, "Documento salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					status.setText("Documento salvo");
				}
			}
		};
		acSalvar.putValue(Action.SHORT_DESCRIPTION, "Salvar (Ctrl+S)");
		acSalvar.setEnabled(false);

		Action acPesquisar = new AbstractAction("Pesquisar (Ctrl+F)") {
			@Override
			public void actionPerformed(ActionEvent e) {
				String termo = JOptionPane.showInputDialog(janela, "Digite o termo a pesquisar");

				if(termo == null || termo.isEmpty()) return;

				String txt = area.getText();
				int idx = txt.toLowerCase().indexOf(termo.toLowerCase());

				if(idx >= 0) {
					area.requestFocusInWindow();
					area.select(idx, idx + termo.length());

					status.setText("Encontrado: \"" + termo + "\"");
				} else {
					JOptionPane.showMessageDialog(janela, "Termo não encontrado!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
					status.setText("Termo não encontrado");
				}
			}
		};
		acPesquisar.putValue(Action.SHORT_DESCRIPTION, "Pesquisar (Ctrl+F)");

		Action acImprimir = new AbstractAction("Imprimir (Ctrl+P)") {
			@Override
			public void actionPerformed(ActionEvent e) {
				int linhas = area.getLineCount();
				JOptionPane.showMessageDialog(janela, "Simulaçao de impressão: " + linhas + " linhas a imprimir.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
				status.setText("Impressão simulada");
			}
		};
		acImprimir.putValue(Action.SHORT_DESCRIPTION, "Imprimir (Ctrl+P)");

		Action acSair = new AbstractAction("Sair (Ctrl+Q)") {
			@Override
			public void actionPerformed(ActionEvent e) {
				int op = JOptionPane.showConfirmDialog(janela, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
				if(op == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		};
		acSair.putValue(Action.SHORT_DESCRIPTION, "Sair (Ctrl+Q)");

		JToolBar barraFerramentas = new JToolBar("Ferramentas");
		barraFerramentas.setFloatable(false);
		barraFerramentas.setRollover(true);
		barraFerramentas.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		JButton btNovo = barraFerramentas.add(acNovo);
		JButton btSalvar = barraFerramentas.add(acSalvar);
		JButton btPesquisar = barraFerramentas.add(acPesquisar);
		JButton btImprimir = barraFerramentas.add(acImprimir);
		barraFerramentas.addSeparator();

		JToggleButton tgWrap = new JToggleButton("Quebra de linha");
		tgWrap.setToolTipText("Alterar quebra de linha");
		tgWrap.setSelected(true);
		tgWrap.addActionListener(e -> {
			boolean w = tgWrap.isSelected();
			area.setLineWrap(w);
			area.setWrapStyleWord(w);
			status.setText("Quebra de linha: " + (w ? "Ativada" : "Desativada"));
		});
		barraFerramentas.add(tgWrap);

		JToggleButton tgNegrito = new JToggleButton("Negrito");
		tgNegrito.setToolTipText("Alterar negrito");
		tgNegrito.addActionListener(e -> {
			Font font = area.getFont();
			int estilo = tgNegrito.isSelected() ? Font.BOLD : Font.PLAIN;
			area.setFont(font.deriveFont(estilo, font.getSize2D()));

			status.setText("Negrito: " + (tgNegrito.isSelected() ? "Ativado" : "Desativado"));
		});
		barraFerramentas.add(tgNegrito);
		barraFerramentas.add(new JLabel("Tamanho: "));

		JSpinner spTam = new JSpinner(new SpinnerNumberModel(12, 8, 32, 2));
		spTam.setToolTipText("Alterar tamanho da fonte");
		spTam.addChangeListener(e -> {
			Font font = area.getFont();
			int tamanho = (Integer) spTam.getValue();
			area.setFont(font.deriveFont((float) tamanho));
			status.setText("Tamanho da fonte: " + tamanho);
		});
		barraFerramentas.add(spTam);
		barraFerramentas.addSeparator();

		JButton btSair = barraFerramentas.add(acSair);

		btNovo.setToolTipText("Novo documento (Ctrl+N)");
		btSair.setToolTipText("Sair (Ctrl+Q)");
		btPesquisar.setToolTipText("Pesquisar (Ctrl+F)");
		btSalvar.setToolTipText("Sair (Ctrl+Q)");
		btImprimir.setToolTipText("Imprimir (Ctrl+P)");

		janela.add(barraFerramentas, BorderLayout.NORTH);

		JRootPane raiz = janela.getRootPane();
		raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "novo");
		raiz.getActionMap().put("novo", acNovo);

		raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "salvar");
		raiz.getActionMap().put("salvar", acSalvar);

		raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "pesquisar");
		raiz.getActionMap().put("pesquisar", acPesquisar);

		raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "imprimir");
		raiz.getActionMap().put("imprimir", acImprimir);

		raiz.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
						Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "sair");
		raiz.getActionMap().put("sair", acSair);

		area.getDocument().addDocumentListener(new DocumentListener() {

			private void atualizar() {
				acSalvar.setEnabled(area.getText().length() > 0);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				atualizar();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				atualizar();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				atualizar();
			}
		});

		status.setText("Pronto. Use a barra de ferramentas ou atalhos");


		janela.setVisible(true);

	}
}

