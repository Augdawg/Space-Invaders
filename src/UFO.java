import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class UFO implements Sprite {
	private int x, y, dx, width, height, cntr, value;
	private Image img;
	private boolean destroyed;
	private String sound;
	private Random r;

	public UFO() {
		r = new Random();
		try {
			img = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\..\\resources\\ufo.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		int n = r.nextInt(4);
		value = n * 50 + 50;
		x = 0;
		y = 60;
		dx = 5;
		width = 60;
		height = 30;
		cntr = 0;
		destroyed = false;
		sound = "ufo_lowpitch.wav";
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return img;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void moveLeft() {
		x += dx;
	}

	@Override
	public void moveRight() {
		x += dx;
	}

	@Override
	public void moveUp() {
	}

	@Override
	public void changeXD() {
	}

	@Override
	public void updateImage() {
	}

	@Override
	public int getPoints() {
		if (cntr == 15 || cntr == 23) {
			value = 300;
			return 300;
		}
		return value;
	}

	@Override
	public void destroy() {
		destroyed = true;
		img = null;
		dx = 0;
	}

	public boolean destroyed() {
		return destroyed;
	}

	public void addShot() {
		cntr++;
	}

	public String getSound() {
		return sound;
	}

	public boolean checkCollision(Bullet b) {
		return new Rectangle(x, y, width, height).intersects(new Rectangle(b
				.getX(), b.getY(), b.getWidth(), b.getHeight()));
	}
}
