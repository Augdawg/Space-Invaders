import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Alien2 extends Enemy{
	Image img1, img2;
	public Alien2(int x, int y) {
		super(x,y,20);
		try {
			img1 = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\Enemy21.png"));
			img2 = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\Enemy22.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setWidth(35);
		setImages(img1, img2);
	}
}