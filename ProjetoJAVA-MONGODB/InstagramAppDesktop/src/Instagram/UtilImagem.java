package Instagram;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

public final class UtilImagem {
	private UtilImagem() {}

	public static ImageIcon imageEscalada(String caminho, int largura, int altura) {
		try {

			Image imgOriginal = ImageIO.read(new File(caminho));

			if(altura <= 0) {
				double proporcao = (double) largura / imgOriginal.getWidth(null);

				altura = (int) (imgOriginal.getHeight(null) * proporcao);
			}

			Image imgRedimensionada = imgOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

			return new ImageIcon(imgRedimensionada);

		}catch(Exception e) {
			int w = Math.max(largura, 1);
			int h = Math.max(altura, 1);

			BufferedImage vazio = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			return new ImageIcon(vazio);
		}
	}
}
