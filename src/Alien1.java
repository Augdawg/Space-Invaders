import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;


public class Alien1 extends Enemy{
	private Image img1, img2;
	public Alien1(int x, int y) {
		super(x,y,10);
		try {
			img1 = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Enemy1.png"));
			img2 = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\Enemy12.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		setImages(img1, img2);
		setWidth(40);
	}
}
