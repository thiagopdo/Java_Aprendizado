package interfaca_grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Aula19_JMessages {
	public static void main(String[] args) {
		try {

			for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch(Exception ignore) {
		}
		SwingUtilities.invokeLater(Aula19_JMessages::criarTela);
	}

	private static void criarTela() {
		JFrame janela = new JFrame("JOptionPane - Catálogo de Mensagens");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(800, 600);
		janela.setLocationRelativeTo(null);
		janela.setLayout(new BorderLayout(10, 10));

		JLabel titulo = new JLabel("Clique em um botão para exibir uma mensagem");
		titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		janela.add(titulo, BorderLayout.NORTH);

		JPanel botoes = new JPanel(new GridLayout(0, 2, 10, 10));
		botoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		//criar botao
		botoes.add(criarBotao("Informação", e -> JOptionPane.showMessageDialog(
						janela,
						"Operação concluída com sucesso!",
						"Mensagem de informaçao",
						JOptionPane.INFORMATION_MESSAGE
		)));

		botoes.add(criarBotao("Aviso", e -> JOptionPane.showMessageDialog(
						janela,
						"Atenção: Esta ação pode ser perigosa!",
						"Mensagem de aviso",
						JOptionPane.WARNING_MESSAGE
		)));

		botoes.add(criarBotao("Erro", e -> JOptionPane.showMessageDialog(
						janela,
						"Ocorreu um erro inesperado!",
						"Mensagem de erro",
						JOptionPane.ERROR_MESSAGE
		)));

		botoes.add(criarBotao("Interrogação", e -> JOptionPane.showMessageDialog(
						janela,
						"Esta é uma mensagem de interrogação.",
						"Mensagem de interrogação",
						JOptionPane.QUESTION_MESSAGE
		)));

		botoes.add(criarBotao("Simples (Plain)", e -> JOptionPane.showMessageDialog(
						janela,
						"Mensagem simples sem icone",
						"Mensagem simples",
						JOptionPane.PLAIN_MESSAGE
		)));

		botoes.add(criarBotao("Confirmar (S/N)", e -> {
			int op = JOptionPane.showConfirmDialog(
							janela,
							"Deseja realmente confirmar ?",
							"Confirmação",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE
			);

			JOptionPane.showMessageDialog(janela, "Voce escolheu: " + decodeYesNo(op));
		}));

		botoes.add(criarBotao("Confirmar (S/N/Cancelar)", e -> {
			int op = JOptionPane.showConfirmDialog(
							janela,
							"Deseja salvar alterações antes de sair ?",
							"Confirmação",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE
			);

			JOptionPane.showMessageDialog(janela, "Voce escolheu: " + decodeYesNoCancel(op));
		}));

		botoes.add(criarBotao("Entrada de texto", e -> {
			String nome = JOptionPane.showInputDialog(
							janela,
							"Qual é o seu nome ?",
							"Entrada de texto",
							JOptionPane.QUESTION_MESSAGE
			);
			if(nome != null) {
				JOptionPane.showMessageDialog(janela, "Olá " + nome + "!");
			}
		}));

		botoes.add(criarBotao("Entrada com opções", e -> {
			String[] linguagens = {"Java", "C++", "Python", "JavaScript"};
			String escolha = (String) JOptionPane.showInputDialog(
							janela,
							"Escolha sua linguagem favorita",
							"Entrada opções",
							JOptionPane.QUESTION_MESSAGE,
							null,
							linguagens,
							linguagens[0]
			);

			if(escolha != null) {
				JOptionPane.showMessageDialog(janela, "Voce escolheu: " + escolha);
			}
		}));

		botoes.add(criarBotao("Opções personalizadas", e -> {
			Object[] opcoes = {"Salvar", "Não salvar", "Cancelar"};
			int op = JOptionPane.showOptionDialog(
							janela,
							"O documento foi alterado. O que deseja fazer ?",
							"Opções personalizadas",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE,
							null,
							opcoes,
							opcoes[0]
			);

			JOptionPane.showMessageDialog(janela, "Voce escolheu: " + decodeCustomOption(op, opcoes));
		}));

		botoes.add(criarBotao("Mensagem com HTML", e -> {
			String html = """
							<html>
							  <h3>Atualização disponível</h3>
							  <p>Uma nova versão está pronta para download.</p>
							  <ul>
							    <li>Correções de bugs</li>
							    <li>Melhorias de performance</li>
							  </ul>
							</html>
							""";
			JOptionPane.showMessageDialog(
							janela,
							new JLabel(html),
							"Mensagem com HTML",
							JOptionPane.INFORMATION_MESSAGE
			);
		}));

		botoes.add(criarBotao("Componente Customizado", e -> {
			JPanel painel = new JPanel(new GridLayout(0, 2, 6, 6));
			JTextField usuario = new JTextField(15);
			JPasswordField senha = new JPasswordField(15);
			painel.add(new JLabel("Usuário:"));
			painel.add(usuario);
			painel.add(new JLabel("Senha:"));
			painel.add(senha);


			int op = JOptionPane.showConfirmDialog(
							janela,
							painel,
							"Login (Exemplo de componente customizado)",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE
			);

			if(op == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(
								janela,
								"Usuário: " + usuario.getText() + "\nSenha: " + new String(senha.getPassword())

				);
			}
		}));

		botoes.add(criarBotao("Icone Customizado", e -> {
			Icon icone = iconeQuadrado(new Color(0x007BFF), 32);

			JOptionPane.showMessageDialog(
							janela,
							"Mensagem com ícone customizado",
							"ICONE (Exemplo de ICone customizado)",
							JOptionPane.INFORMATION_MESSAGE,
							icone
			);
		}));


		janela.add(new JScrollPane(botoes), BorderLayout.CENTER);
		JLabel status = new JLabel("Pronto");
		status.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		janela.add(status, BorderLayout.SOUTH);
		janela.setVisible(true);
	}

	private static JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
		JButton botao = new JButton(texto);
		botao.addActionListener(acao);
		return botao;
	}

	private static String decodeYesNo(int op) {
		return switch(op) {
			case JOptionPane.YES_OPTION -> "Sim";
			case JOptionPane.NO_OPTION -> "Não";
			default -> "Janela fechada";
		};
	}

	private static String decodeYesNoCancel(int op) {
		return switch(op) {
			case JOptionPane.YES_OPTION -> "Sim";
			case JOptionPane.NO_OPTION -> "Não";
			case JOptionPane.CANCEL_OPTION -> "Cancelar";
			default -> "Janela fechada";
		};
	}

	private static String decodeCustomOption(int op, Object[] opcoes) {
		if(op >= 0 && op < opcoes.length) {
			return String.valueOf(opcoes[op]);
		}

		return "Janela fechada";
	}

	private static Icon iconeQuadrado(Color cor, int tamanho) {
		BufferedImage img = new BufferedImage(tamanho, tamanho, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(cor);
		g2.fillRoundRect(2, 2, tamanho - 4, tamanho - 4, 12, 12);
		g2.setColor(cor.darker());
		g2.drawRoundRect(2, 2, tamanho - 4, tamanho - 4, 12, 12);
		g2.dispose();

		return new ImageIcon(img);
	}
}
