import java.awt.Image;

public interface Sprite {
	public int getX();

	public int getY();

	public Image getImage();

	public int getHeight();

	public int getWidth();

	public void moveLeft();

	public void moveRight();
	
	public void moveUp();
	
	public void changeXD();
	
	public void updateImage();
	
	public int getPoints();
	
	public void destroy();
	
}
