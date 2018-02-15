import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

public class Enemy implements Sprite {
	private Image img, img1, img2, imgd;
	private int x, y, dx, dy, width, height, value;
	private boolean swapped, destroyed;

	public Enemy(int ix, int iy, int pValue) {
		value = pValue;
		dx = 15;
		dy = 20;
		x = ix;
		y = iy;
		width = 50;
		height = 30;
		swapped = false;
		img = img1;
		try {
			imgd = ImageIO.read(new File(System.getProperty("user.dir") + "\\..\\resources\\explosion.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int getX() {
		return x;
	}
	public void setImages(Image... i) {
		img1 = i[0];
		img2 = i[i.length - 1];
	}
	public int getY() {
		return y;
	}

	public Image getImage() {
		return img;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
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
		y += dy;
	}

	public void changeXD() {
		dx = -dx;
	}

	public void updateImage() {
		if(!swapped)
			img = img1;
		else 
			img = img2;
		swapped = !swapped;
	}

	@Override
	public int getPoints() {
		return value;
	}
	public void setWidth(int w) {
		width = w;
	}
	public void setHeight(int h) {
		height = h;
	}
	@Override
	public void destroy() {
		img1 = null;
		img2 = null;
		destroyed = true;
		dx = 0; 
		dy = 0; 
		img = imgd;
	}
	public boolean destroyed() {
		return destroyed;
	}
	public boolean checkCollision(Bullet b) {
		return new Rectangle(x, y,width,height).intersects(new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight()));
	}
}