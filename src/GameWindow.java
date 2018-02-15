import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private volatile Board board;
	private volatile GameOver go;

	public GameWindow() {
		board = new Board();
		StartScreen start = new StartScreen(board);
		add(start);
		setSize(board.getSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setTitle("Space Invaders");
		setIconImage(board.getLogo());
		start.requestFocusInWindow();
		start.start();
		while(!start.done()) {
			
		}
		setContentPane(board);
		board.requestFocusInWindow();
		board.start();
		while (true) {
			if (board.lost()) {
				go = new GameOver(board);
				setContentPane(go);
				go.requestFocusInWindow();
				board = new Board();
				go.start();
			}
			if(go.done()) {
				setContentPane(board);
				go = new GameOver(board);
				board.requestFocusInWindow();
				board.start();
			}
				
		}
	}

	public static void main(String[] args) {
		new GameWindow();
	}
}
