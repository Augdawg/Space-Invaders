import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
	private boolean[] keys;
	private boolean[] keysTyped;

	public InputManager() {
		keys = new boolean[300];
		keysTyped = new boolean[300];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
			keysTyped[i] = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == ' ')
			keysTyped[KeyEvent.VK_SPACE] = !keysTyped[KeyEvent.VK_SPACE];
		if(e.getKeyChar() == 'd')
			keysTyped[KeyEvent.VK_D] = !keysTyped[KeyEvent.VK_D];
		if(e.getKeyChar() == 'r')
			keysTyped[KeyEvent.VK_R] = !keysTyped[KeyEvent.VK_R];
		if(e.getKeyChar() == 'p')
			keysTyped[KeyEvent.VK_P] = !keysTyped[KeyEvent.VK_P];
	}

	public boolean isPressed(int i) {
		return keys[i];
	}

	public boolean isTyped(int i) {
		return keysTyped[i];
	}
}
