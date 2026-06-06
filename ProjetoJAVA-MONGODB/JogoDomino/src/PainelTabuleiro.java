import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelTabuleiro extends JPanel {
	private static final int ALTURA = 30;
	private static final int COMPRIMENTO = ALTURA * 2;
	private static final int ESPACO = 2;
	private static final Color COR_FUNDO = new Color(0, 128, 140);
	private static final Color COR_PECA = Color.WHITE;
	private static final Color COR_BORDA = new Color(160, 160, 160);
	private static final Color COR_AZUL = new Color(30, 90, 200);
	private static final Color COR_VERMELHO = new Color(200, 50, 50);

	private List<Peca> sequencia;

	public void setPecas(List<Peca> pecas) {
		this.sequencia = pecas;
		repaint();
	}

	@Override()
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(COR_FUNDO);

		if(sequencia == null || sequencia.isEmpty()) {
			return;
		}

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int cx = 10;
		int cy = getHeight() / 2;
		int dx = 1;
		int dy = 0;

		for(Peca peca : sequencia) {
			int tentativas = 0;
			while(tentativas < 4) {
				boolean vertical = (dx == 0) || peca.duplo();
				int w = vertical ? ALTURA : COMPRIMENTO;
				int h = vertical ? COMPRIMENTO : ALTURA;

				int tlX, tlY;

				if(dx == 1) {
					tlX = cx;
					tlY = cy - h / 2;
				}else if(dx == -1) {
					tlX = cx - w;
					tlY = cy - h / 2;
				}else if(dy == 1) {
					tlX = cx - w / 2;
					tlY = cy;
				}else{
					tlX = cx - w / 2;
					tlY = cy - h;
				}

				if(tlX < 0 || tlY < 0 || tlX + w > getWidth() || tlY + h > getHeight()) {
					int novoDX = -dy;
					int novoDY = dx;

					dx = novoDX;
					dy = novoDY;

					tentativas++;
					continue;
				}

				boolean inverter = (dx == -1) || (dy == -1);

				int ladoA = inverter ? peca.ladoDireito() : peca.ladoEsquerdo();
				int ladoB = inverter ? peca.ladoEsquerdo() : peca.ladoDireito();

				desenharPeca(g2, tlX, tlY, w, h, vertical, ladoA, ladoB);

				if(dx != 0) {
					int passo = (vertical ? ALTURA : COMPRIMENTO) + ESPACO;
					cx += dx * passo;
				}else{
					int passo = (vertical ? COMPRIMENTO : ALTURA) + ESPACO;
					cy += dy * passo;
				}


				break;
			}
		}
	}

	private void desenharPeca(Graphics2D g2, int x, int y, int w, int h, boolean vertical, int ladoA, int ladoB) {
		g2.setColor(COR_PECA);
		g2.fillRoundRect(x, y, w, h, 6, 6);
		g2.setColor(COR_BORDA);
		g2.drawRoundRect(x, y, w, h, 6, 6);

		if(vertical) {
			g2.drawLine(x, y + h / 2, x + w, y + w / 2);

			desenharPips(g2, ladoA, x, y, w, h / 2);
			desenharPips(g2, ladoB, x, y + h / 2, w, h / 2);
		}else{
			g2.drawLine(x + w / 2, y, x + w / 2, y + h);

			desenharPips(g2, ladoA, x, y, w / 2, h);
			desenharPips(g2, ladoB, x + w / 2, y, w / 2, h);
		}
	}

	private void desenharPips(Graphics2D g2, int valor, int x, int y, int w, int h) {
		g2.setColor(valor <= 3 ? COR_AZUL : COR_VERMELHO);
		int cx = x + w / 2;
		int cy = y + h / 2;
		int r = 6;
		int dx = w / 3;
		int dy = h / 3;

		switch(valor) {
			case 0 -> {}
			case 1 -> pip(g2, cx, cy, r);
			case 2 -> {
				pip(g2, cx - dx, cy - dy, r);
				pip(g2, cx + dx, cy + dy, r);
			}
			case 3 -> {
				pip(g2, cx - dx, cy - dy, r);
				pip(g2, cx, cy, r);
				pip(g2, cx + dx, cy + dy, r);
			}
			case 4 -> {
				pip(g2, cx - dx, cy - dy, r);
				pip(g2, cx + dx, cy - dy, r);
				pip(g2, cx - dx, cy + dy, r);
				pip(g2, cx + dx, cy + dy, r);
			}
			case 5 -> {
				desenharPips(g2, 4, x, y, w, h);
				pip(g2, cx, cy, r);
			}
			case 6 -> {
				desenharPips(g2, 4, x, y, w, h);
				pip(g2, cx, cy - dy, r);
				pip(g2, cx, cy + dy, r);
			}
		}
	}

	private void pip(Graphics2D g2, int cx, int cy, int r) {
		int cantoX = cx - r;
		int cantoY = cy - r;

		int diametro = 2 * r;

		g2.fillOval(cantoX, cantoY, diametro, diametro);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 700);
	}
}
