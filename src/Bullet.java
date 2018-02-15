import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;


public class Bullet {

	private Image img;
	private int x, y, dy, width, height;
	
	public Bullet(Player p) {
		try {
			img = ImageIO.read(new File(System.getProperty("user.dir") + "\\..\\resources\\bullet.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		width = 4;
		height = 15;
		x = p.getX() + p.getWidth()/2 - width/2;
		y = p.getY() - height;
		dy = -6;
	}
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return img;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void moveUp() {		
		y += dy;
	}
}
