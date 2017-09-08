import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StartScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image img;
	private Dimension d;
	private boolean done;
	private InputManager m;

	public StartScreen(Component c) {
		m = new InputManager();
		addKeyListener(m);
		done = false;
		d = c.getSize();
		setSize(d);
		setFocusable(true);
		try {
			img = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\resources\\start_screen.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		repaint();
	}

	public void start() {
		while (!done) {
			if (m.isPressed(KeyEvent.VK_ENTER)) {
				done = true;
			}
		}
	}

	public boolean done() {
		return done;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, d.width, d.height, this);
	}
}
