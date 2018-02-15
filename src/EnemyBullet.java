import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class EnemyBullet {
	private Image img;
	private int x, y, dy, width, height;
	public static final int SLOW = 0, FAST = 1;

	public EnemyBullet(int t, Enemy e) {
		width = 20;
		height = 20;
		x = e.getX() + e.getWidth() / 2;
		y = e.getY() + e.getHeight();
		if (t == 0)
			dy = 4;
		else
			dy = 6;
		try {
			if (t == 0)
				img = ImageIO.read(new File(System.getProperty("user.dir")
						+ "\\..\\resources\\enemy_bullet1.png"));
			else
				img = ImageIO.read(new File(System.getProperty("user.dir")
						+ "\\..\\resources\\enemy_bullet2.png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void move() {
		y += dy;
	}

	public Image getImage() {
		return img;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
