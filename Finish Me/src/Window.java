import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = 1L;
	public int width = 450;
	public int height = 914;
	private String title = "Tetris Clone";
	private JFrame frame;
	private BufferStrategy bs;
	private Graphics graphics;

	public Window() {
		setUpWindow();
	}
	
	public void setUpWindow() {
		Dimension size = new Dimension(width, height);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		frame = new JFrame();
		frame.setTitle(title);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		requestFocus();
		
		createBufferStrategy(3);
		bs = getBufferStrategy();
		graphics = bs.getDrawGraphics();
	}

	public void setTitle(int frames, int updates) {
		frame.setTitle(title + "  |  Frames: " + frames + "  Updates: " + updates);
	}

	public void display() {
		bs.show();
		graphics.dispose();
	}
}
