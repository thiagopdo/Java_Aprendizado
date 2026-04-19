package interfaca_grafica;

import javax.swing.*;
import java.awt.*;


public class Aula2 {
	public static void main(String[] args) {
		JFrame janela = new JFrame("Exemplo JLabel");

		janela.setSize(600, 400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new BorderLayout(10,10));

		JLabel rotulo1 = new JLabel("JLabel padrão");

		JLabel rotulo2 = new JLabel("Fonte maior e negrito");
		rotulo2.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel rotulo3 = new JLabel("Text em azul");
		rotulo3.setForeground(Color.BLUE);

		JLabel rotulo4 = new JLabel("Texto com fundo amarelo");
		rotulo4.setOpaque(true);
		rotulo4.setBackground(Color.YELLOW);

		ImageIcon icone = new ImageIcon("/Users/thunder/Desktop/Projetos/JAVA/Interface_Grafica" +
						"/Interface_Grafica/src/interfaca_grafica/java.png");
		Image img = icone.getImage();
		Image imgRedimensionada = img.getScaledInstance(100,200, Image.SCALE_SMOOTH);
		ImageIcon iconeRedimensionado = new ImageIcon(imgRedimensionada);
		JLabel rotulo5 = new JLabel("Texto centralizado + icone",iconeRedimensionado, JLabel.CENTER);
		rotulo5.setHorizontalTextPosition(JLabel.CENTER);
		rotulo5.setVerticalTextPosition(JLabel.BOTTOM);

		JPanel painelTexto = new JPanel(new GridLayout(4,1,10,10));

		painelTexto.add(rotulo1);
		painelTexto.add(rotulo2);
		painelTexto.add(rotulo3);
		painelTexto.add(rotulo4);

		janela.add(painelTexto, BorderLayout.CENTER);

		JPanel painelIcone = new JPanel(new FlowLayout(FlowLayout.CENTER));
		painelIcone.add(rotulo5);
		janela.add(painelIcone, BorderLayout.SOUTH);


		janela.pack();
		janela.setLocationRelativeTo(null);

		janela.setVisible(true);

	}
}
