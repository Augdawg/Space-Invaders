import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameOver extends JPanel{
	private Image img;
	private Dimension d;
	private Board b;
	private boolean done;
	private volatile InputManager m;
	public GameOver(Component c){
		m = new InputManager();
		addKeyListener(m);
		done = false;
		b = (Board) c;
		d = c.getSize();
		setSize(d);
		setFocusable(true);
		try {
			img = ImageIO.read(new File(System.getProperty("user.dir") + "\\..\\resources\\gameover.jpg"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		repaint();
	}
	public void start() { 
		while(!done) {
			if(m.isPressed(KeyEvent.VK_ENTER)) {
				done = true;
			}
			if(m.isPressed(KeyEvent.VK_ESCAPE))
				System.exit(0);
		}
	}
	public boolean done() {
		return done;
	}
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("Razer Header Regular", Font.PLAIN, 30));
		g2.setColor(Color.WHITE);
		g2.drawImage(img, 0, 0, (int)d.getWidth(), (int)d.getHeight(), this);
		g.drawString("Final Score: " + b.totalScore(),d.width*2/5, 500);
	}
}
