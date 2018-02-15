import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private int x, y, width, height, dx, lives, oX, oY;
	private Image img, dImg, aImg;
	private Component comp;
	private boolean recent;

	public Player(Component c) {
		try {			
			aImg = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\ship.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lives = 3;
		comp = c;
		dx = 5;
		img = aImg;
		width = img.getWidth(null) / 2;
		height = img.getHeight(null) / 2;
		x = c.getWidth() / 2 - width / 2;
		y = c.getHeight() - height * 2 - 25;
		oX = x;
		oY = y;
		recent = false;
		try {
			dImg = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\player_death.png"));
		} catch (Exception e) {
		}
	}

	public Image getImage() {
		if(recent) {
			return dImg;
		}
		
		return aImg;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void moveLeft() {
		if (x - dx >= 0)
			x -= dx;
	}

	public void moveRight() {
		if (x + dx + width <= comp.getWidth()) {
			x += dx;
		}
	}

	public boolean checkCollision(EnemyBullet b) {
		boolean a = new Rectangle(x, y, width, height)
				.intersects(new Rectangle(b.getX(), b.getY(), b.getWidth(), b
						.getHeight()));
		if (a) {
			recent = true;
			lives--;
			img = dImg;
		}
		return a;
	}

	public int getLives() {
		return lives;
	}
	public boolean recentlyDied() {
		return recent;
	}
	public void resetPosition() {
		x = oX;
		y = oY;
		recent = false;
	}
}
