import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Alien3 extends Enemy {
	private Image img1, img2;
	public Alien3(int x, int y) {
		super(x,y, 30);
		try {
			img1 = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\Enemy31.png"));
			img2 = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\Enemy32.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setImages(img1, img2);
		setWidth(25);
		setHeight(25);
	}
}