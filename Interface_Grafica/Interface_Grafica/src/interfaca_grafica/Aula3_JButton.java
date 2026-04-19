package interfaca_grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class Aula3_JButton {

	public static void main(String[] args) {
	 try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 } catch (Exception ignore) {

	 }

	 JFrame janela = new JFrame("Exemplo JButton melhorado");
	 janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 janela.setLayout(new BorderLayout(10,10));

	 JPanel painelCentro = new JPanel(new GridLayout(3,1,10,10));
	 painelCentro.setBorder(new EmptyBorder(10,10,10,10));

	 JButton botao1 = new	 JButton("Botão 1");

	 botao1.setToolTipText("Este é o Botão 1");
	 botao1.addActionListener(e->{
		 JOptionPane.showMessageDialog(janela, "Você clicou no Botão 1");
	 });

	 JButton botao2 = new JButton("Botão Azul");
	 botao2.setUI(new javax.swing.plaf.basic.BasicButtonUI());
	 botao2.setContentAreaFilled(true);
	 botao2.setOpaque(true);
	 botao2.setBackground(new Color(0,85,204));
	 botao2.setForeground(Color.WHITE);
	 botao2.setBorder(BorderFactory.createLineBorder(new Color(0,70,170)));
	 botao2.setFocusPainted(false);
	 botao2.setFont(new Font("Arial", Font.BOLD, 14));
	 botao2.addActionListener(e->
					 JOptionPane.showMessageDialog(janela, "Botão azul pressionado!")
					 );

	 JButton botao3 = new JButton("Fonte grande");
	 botao3.setFont(new Font("Arial", Font.BOLD, 20));
	 botao3.addActionListener(e->
					 JOptionPane.showMessageDialog(janela, "Você clicou no Botão de Fonte Grande!"));

	 URL urlIcone = Aula3_JButton.class.getResource("/interfaca_grafica/java.png");
	 ImageIcon iconeAjustato = null;

	 if(urlIcone != null) {
		 ImageIcon iconeOriginal = new ImageIcon(urlIcone);
		 Image img = iconeOriginal.getImage();
		 int larguraIcone = 48;
		 int alturaIcone = 48;

		 Image imgEscalada = img.getScaledInstance(larguraIcone, alturaIcone, Image.SCALE_SMOOTH);
		 iconeAjustato = new ImageIcon(imgEscalada);

	 }

	 JButton botao4 = (iconeAjustato != null) ? new JButton("Com icone", iconeAjustato) : new JButton("Com icone");
	 botao4.setHorizontalTextPosition(SwingConstants.CENTER);
	 botao4.setVerticalTextPosition(SwingConstants.BOTTOM);
	 botao4.setIconTextGap(8);
	 botao4.setMargin(new Insets(10,12,10,12));
	 botao4.setFont(new Font("Arial", Font.PLAIN, 14));
	 botao4.addActionListener(e->
					 JOptionPane.showMessageDialog(janela, "Botao com icone pressionado")
					 );

	 painelCentro.add(botao1);
	 painelCentro.add(botao2);
	 painelCentro.add(botao3);
	 painelCentro.add(botao4);

	 janela.add(painelCentro, BorderLayout.CENTER);
	 janela.pack();
	 janela.setMinimumSize(new Dimension(720,480));
	 janela.setLocationRelativeTo(null);
	 janela.setVisible(true);

	}
}
