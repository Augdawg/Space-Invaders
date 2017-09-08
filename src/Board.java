import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JPanel;

public class Board extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image background, logo, pauseImg;
	private final int WIDTH = 1024, HEIGHT = 678;
	private Dimension d;
	private Player p;
	private InputManager manager;
	private ArrayList<Bullet> bullets;
	private long timer, cntr, enemyT, ufoT, soundT, stringT;
	private int score, turns;
	private Rectangle top;
	private Font font;
	private ArrayList<Player> lives;
	private Enemy[][] enemies;
	private UFO ufo;
	private boolean lost, paused;
	private ArrayList<EnemyBullet> eBullets;

	/*
	 * board constructor, to be added to a frame
	 */
	public Board() {
		lives = new ArrayList<Player>();
		eBullets = new ArrayList<EnemyBullet>();
		for (int i = 0; i < 3; i++)
			lives.add(new Player(this));
		try {
			background = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\resources\\background.png"));
			logo = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\resources\\Logo.png"));
			pauseImg = ImageIO.read(new File(System.getProperty("user.dir")
					+ "\\resources\\pause_screen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		manager = new InputManager();
		addKeyListener(manager);
		bullets = new ArrayList<Bullet>();
		d = new Dimension(WIDTH, HEIGHT);
		setSize(d);
		timer = 0;
		score = 0;
		ufoT = 0;
		soundT = 0;
		stringT = 0;
		top = new Rectangle(new Dimension(WIDTH, 50));
		p = new Player(this);
		setFocusable(true);
		font = new Font("System", Font.PLAIN, 30);
		enemyT = 0;
		populateEnemyArray();
		lost = false;
		paused = false;
	}

	/*
	 * start game - the game loop
	 */
	public void start() {
		while (!lost) {
			checkPause();
			if (!paused) {
				checkCollision();
				checkIfEmpty(enemies);
				updateEnemyPosition();
				updatePlayerPosition();
				checkLost();
				try {
					Thread.sleep(7);
				} catch (InterruptedException e) {

				}
			}
			repaint();
		}
	}

	public void checkPause() {
		if (manager.isTyped(KeyEvent.VK_P) && !paused) {
			paused = !paused;
		} else if (!manager.isTyped(KeyEvent.VK_P) && paused) {
			paused = !paused;
		}
	}

	/*
	 * method for updating the enemy array
	 */
	private void updateEnemyPosition() {
		addEnemyBullets();
		/*
		 * UFO updating~
		 */
		long current = System.currentTimeMillis();
		Random r = new Random();
		int n = r.nextInt(10000);
		if (ufo != null && ufo.getX() >= WIDTH)
			ufo = null;
		if (n == 999 && ufo == null && cntr > 30)
			ufo = new UFO();
		if (ufo != null && current - ufoT > 40) {
			ufo.moveLeft();
			if (current - soundT > 2000) {
				playSound(ufo.getSound());
				soundT = current;
			}
			ufoT = current;
		}

		// update alien array
		current = System.currentTimeMillis();
		int update = 500;
		if (turns > 4)
			update = 250;
		else if (turns > 2)
			update = 350;

		// intial update for position every 500 ms
		if (current - enemyT >= update) {
			for (int i = 0; i < enemies.length; i++) {
				for (int j = 0; j < enemies[0].length; j++) {
					Sprite enemy = enemies[i][j];
					if (enemy != null) {
						enemy.moveRight();
						enemy.updateImage();
					}
				}
			}
			enemyT = System.currentTimeMillis();
		}
		int biggest = findMaxRow(enemies, true);
		int smallest = findMaxRow(enemies, false);

		// change direction & y position if needed
		for (int i = 0; i < enemies.length; i++) {
			Enemy s = enemies[i][biggest];
			Enemy s2 = enemies[i][smallest];
			if (!s.destroyed() && s.getX() + s.getWidth() >= WIDTH
					|| s2.getX() <= 0) {
				for (int j = 0; j < enemies.length; j++) {
					for (int k = 0; k < enemies[0].length; k++) {
						Sprite s1 = enemies[j][k];
						if (s1 != null) {
							s1.changeXD();
							s1.moveLeft();
							s1.moveUp();
							if (j + 1 == enemies.length
									&& k + 1 == enemies[0].length)
								turns++;
						}
					}
				}
			}
		}
	}

	/*
	 * populates enemy array with various aliens
	 */
	private void checkLost() {
		if (findMaxEnemy().getY() + findMaxEnemy().getHeight() >= p.getY()
				|| p.getLives() <= 0) {
			lost = true;
		}
	}

	private void populateEnemyArray() {
		int y = 100;
		int x = 50;
		enemies = new Enemy[5][11];
		Sprite st = new Alien1(0, 0);
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[0].length; j++) {
				if (i == 0)
					enemies[i][j] = new Alien3(x, y - 5);
				else if (i > 0 && i < 3)
					enemies[i][j] = new Alien2(x, y - 5);
				else
					enemies[i][j] = new Alien1(x, y - 5);
				x += st.getWidth() + 15;
			}
			y += st.getHeight() + 15;
			x = 50;
		}
	}

	/*
	 * checks for bullet - alien collision
	 */
	private void checkCollision() {
		if (!bullets.isEmpty()) {
			Bullet b = bullets.get(0);
			for (int i = 0; i < enemies.length; i++) {
				for (int j = 0; j < enemies[0].length; j++) {
					Enemy s = enemies[i][j];
					if (!s.destroyed() && b != null && s.checkCollision(b)) {
						playSound("invaderKilled.wav");
						score += s.getPoints();
						bullets.remove(b);
						enemies[i][j].destroy();
					}
				}
			}
			if (ufo != null && !ufo.destroyed() && ufo.checkCollision(b)) {
				ufo.destroy();
				score += ufo.getPoints();
				bullets.remove(b);
				stringT = System.currentTimeMillis();
			}
		}
		if (!eBullets.isEmpty()) {
			EnemyBullet b = eBullets.get(0);
			if (p.checkCollision(b)) {
				eBullets.remove(b);
			}
		}
	}

	/*
	 * listens for key events from player
	 */
	private void updatePlayerPosition() {
		if (manager.isPressed(KeyEvent.VK_LEFT)) {
			p.moveLeft();
		} else if (manager.isPressed(KeyEvent.VK_RIGHT)) {
			p.moveRight();
		}
		if (manager.isPressed(KeyEvent.VK_SPACE) && bullets.isEmpty()) {
			bullets.add(new Bullet(p));
			playSound("shoot.wav");
			if (ufo != null)
				ufo.addShot();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		if (!lost) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			// initial graphics + background + player sprite
			g2.setFont(font);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			if (paused) {
				g2.drawImage(pauseImg, 0,0,WIDTH, HEIGHT,this);
			} else {
				g2.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
				g2.drawImage(p.getImage(), p.getX(), p.getY(), p.getWidth(),
						p.getHeight(), this);
				if (p.recentlyDied()) {
					try {
						Thread.sleep(500);
					} catch (Exception d) {
					}
					p.resetPosition();
				}

				// draw bullets
				for (int i = 0; i < bullets.size(); i++) {
					if (bullets.get(i) != null) {
						Bullet b = bullets.get(i);
						g2.drawImage(b.getImage(), b.getX(), b.getY(),
								b.getWidth(), b.getHeight(), this);
						b.moveUp();
						if (b.getY() + b.getHeight() + top.height <= 0)
							bullets.remove(i);
					}
				}
				// draw Enemy bullets
				for (int i = 0; i < eBullets.size(); i++) {
					if (eBullets.get(i) != null) {
						EnemyBullet b = eBullets.get(i);
						g2.drawImage(b.getImage(), b.getX(), b.getY(),
								b.getWidth(), b.getHeight(), this);
						b.move();
						if (b.getY() >= d.height)
							eBullets.remove(i);
					}
				}
				// draw ufo
				if (ufo != null) {
					if (!ufo.destroyed())
						g2.drawImage(ufo.getImage(), ufo.getX(), ufo.getY(),
								ufo.getWidth(), ufo.getHeight(), this);
					else {
						g2.setColor(Color.WHITE);
						if (System.currentTimeMillis() - stringT < 1000)
							g2.drawString("+" + ufo.getPoints(), ufo.getX(),
									ufo.getY() + ufo.getHeight() / 2);
						else {
							ufo = null;
						}
					}
				}
				// draw enemies
				for (int i = 0; i < enemies.length; i++) {
					for (int j = 0; j < enemies[0].length; j++) {
						Sprite enemy = enemies[i][j];
						if (enemy != null) {
							g2.drawImage(enemy.getImage(), enemy.getX(),
									enemy.getY(), enemy.getWidth(),
									enemy.getHeight(), this);
						}
					}
				}

				// draw score values + gray bar
				g2.setColor(Color.GRAY);
				g2.fill(top);
				updateTime();
				String drawn = "Score: " + score + "      Time: " + cntr;
				g2.setColor(Color.BLACK);
				g2.drawString(drawn, 275, top.height / 2 + 5);
				String l = "Lives:";
				g2.drawString(l, 700, top.height / 2 + 4);
				int widthApart = 0;

				// draw lives
				if (!lives.isEmpty()) {
					for (int i = 0; i < p.getLives(); i++) {
						g2.drawImage(lives.get(i).getImage(), 800 + widthApart,
								top.height / 2 - 20, lives.get(i).getWidth(),
								lives.get(i).getHeight(), this);
						widthApart += lives.get(i).getWidth() + 10;
					}
				}
			}
		}
	}

	/*
	 * get logo for the game
	 */
	public Image getLogo() {
		return logo;
	}

	/*
	 * method for playing sounds in the .wav format
	 */
	private void playSound(String fileName) {
		try {
			File yourFile = new File(System.getProperty("user.dir")
					+ "\\resources\\" + fileName);
			AudioInputStream stream = AudioSystem.getAudioInputStream(yourFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/*
	 * updates time of game
	 */
	private void updateTime() {
		if (System.currentTimeMillis() - timer > 1000) {
			cntr++;
			timer = System.currentTimeMillis();
		}

	}

	/*
	 * hehe
	 */
	private int findMaxRow(Enemy[][] e, boolean big) {
		int max = 0;
		int min = e[0].length - 1;
		for (int i = 0; i < e.length; i++) {
			for (int j = 0; j < e[0].length; j++) {
				if (!e[i][j].destroyed() && j > max && big)
					max = j;
				if (!e[i][j].destroyed() && j < min && !big) {
					min = j;
				}
			}
		}
		if (big)
			return max;
		return min;
	}

	private void checkIfEmpty(Enemy[][] e) {
		boolean empty = true;
		for (int i = 0; i < e.length; i++) {
			for (int j = 0; j < e[0].length; j++)
				if (e[i][j] != null && !e[i][j].destroyed())
					empty = false;
		}
		if (empty) {
			populateEnemyArray();
			turns = 0;
		}
	}

	private Enemy findMaxEnemy() {
		Enemy max = new Enemy(0, 0, 0);
		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < enemies[0].length; j++) {
				if (enemies[i][j] != null && enemies[i][j].getY() > max.getY())
					max = enemies[i][j];
			}
		}
		return max;
	}

	/*
	 * reset game function - for debugging :^)
	 */

	public void addEnemyBullets() {
		if (!eBullets.isEmpty())
			return;
		Random r = new Random();
		int hmm = r.nextInt(1000);
		if (hmm < 980)
			return;
		int strength = r.nextInt(5);
		ArrayList<Enemy> e = getLowestEnemies();
		Enemy temp = e.get(r.nextInt(e.size()));
		if(temp.getPoints() == 0)
			return;
		if (strength < 4)
			eBullets.add(new EnemyBullet(EnemyBullet.SLOW, temp));
		else
			eBullets.add(new EnemyBullet(EnemyBullet.FAST, temp));
	}

	public ArrayList<Enemy> getLowestEnemies() {
		ArrayList<Enemy> lowest = new ArrayList<Enemy>();
		for (int i = 0; i < enemies[0].length; i++) {
			Enemy big = new Enemy(0, 0, 0);
			for (int j = 0; j < enemies.length; j++) {
				Enemy temp = enemies[j][i];
				if (temp != null && !temp.destroyed()
						&& temp.getY() > big.getY())
					big = temp;
			}
			lowest.add(big);
		}
		return lowest;
	}

	public boolean lost() {
		return lost;
	}

	public int totalScore() {
		return score;
	}
}
