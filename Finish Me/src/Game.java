import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public int width = 450;
	public int height = 914;
	private Dimension size = new Dimension(width, height);
	private static String title = "Tetcopy";
	int moverect;

	private Thread thread;
	private boolean isRunning = false;
	private JFrame frame;
	private int[][] mainGrid = new int[10][20];
	private int[][] nextGrid = new int[5][5];
	private int[][] bankGrid = new int[5][5];
	private int[] currentToken = { 0, 0 };
	private int currentTokenY = 0;

	private int[][][] tokenRotationCellXPosition = {
			{ { 0, 1, 2, 0 }, { 0, 1, 1, 1 }, { 0, 1, 2, 2 }, { 1, 1, 1, 2 } },
			{ { 0, 1, 2, 2 }, { 0, 1, 1, 1 }, { 0, 0, 1, 2 }, { 1, 1, 1, 2 } },
			{ { 0, 1, 1, 2 }, { 0, 0, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 1 } },
			{ { 0, 1, 1, 2 }, { 0, 0, 1, 1 }, { 0, 1, 1, 2 }, { 0, 0, 1, 1 } },
			{ { 0, 1, 1, 2 }, { 0, 1, 1, 1 }, { 0, 1, 1, 2 }, { 1, 1, 1, 2 } },
			{ { 0, 1, 2, 3 }, { 2, 2, 2, 2 }, { 0, 1, 2, 3 }, { 2, 2, 2, 2 } },
			{ { 1, 1, 2, 2 }, { 1, 1, 2, 2 }, { 1, 1, 2, 2 }, { 1, 1, 2, 2 } } };
	private int[][][] tokenRotationCellYposition = {
			{ { 1, 1, 1, 2 }, { 0, 0, 1, 2 }, { 1, 1, 1, 0 }, { 0, 1, 2, 2 } },
			{ { 1, 1, 1, 2 }, { 2, 0, 1, 2 }, { 0, 1, 1, 1 }, { 0, 1, 2, 0 } },
			{ { 2, 2, 1, 1 }, { 0, 1, 1, 2 }, { 2, 2, 1, 1 }, { 0, 1, 1, 2 } },
			{ { 1, 1, 2, 2 }, { 1, 2, 0, 1 }, { 1, 1, 2, 2 }, { 1, 2, 0, 1 } },
			{ { 1, 1, 2, 1 }, { 1, 0, 1, 2 }, { 1, 0, 1, 1 }, { 0, 1, 2, 1 } },
			{ { 2, 2, 2, 2 }, { 0, 1, 2, 3 }, { 2, 2, 2, 2 }, { 0, 1, 2, 3 } },
			{ { 1, 2, 1, 2 }, { 1, 2, 1, 2 }, { 1, 2, 1, 2 }, { 1, 2, 1, 2 } } };
	private int[] tokenColor = new int[7];
	private int numTokenCells = 4;
	private int mainGridXBounds = 10;
	private int mainGridYBounds = 20;
	private int subGridXBounds = 4;
	private int subGridYBounds = 4;

	public Game() {
		frame = new JFrame();
		setPreferredSize(size);
		// key = new Keyboard();
		// addKeyListener(key);

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setUpWindow();
		game.start();

	}

	public synchronized void setUpWindow() {
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this, "Tetcopy Display");
		thread.start();

	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		boolean newRandomToken = true;
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update(updates);
				updates++;
				delta--;
			}

			if (newRandomToken) {
				drawRandomToken();
				newRandomToken = false;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups, "+ frames +" fps");
				frame.setTitle(title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;

			}

		}
		stop();

	}

	private void update(int updates) {

		updateMainGrid();
		updateNextToken();
		updateBankToken();

		if (updates == 58) {
			moveTokenDown();
		}

	}

	private void updateBankToken() {
		// TODO Auto-generated method stub

	}

	private void updateNextToken() {
		// TODO Auto-generated method stub

	}

	private void updateMainGrid() {
		// TODO Auto-generated method stub

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		try {
			BufferedImage image = ImageIO.read(Game.class
					.getResource("/Layout.png"));
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < mainGridXBounds; x++) {
			for (int y = 0; y < mainGridYBounds; y++) {
				if (mainGrid[x][y] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(x * 44 + 5, y * 44 + 5,
							44 + occupiedRight(mainGrid, x, y),
							44 + occupiedBelow(mainGrid, x, y));
					g.setColor(Color.RED);
					g.fillRect(x * 44 + 1 + 5, y * 44 + 1 + 5, 43, 43);
				}
				if (x < subGridXBounds && y < subGridYBounds
						&& nextGrid[x][y] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(x * 8 + 50, y * 8 + 870,
							8 + occupiedRight(nextGrid, x, y),
							8 + occupiedBelow(nextGrid, x, y));
					g.setColor(Color.RED);
					g.fillRect(x * 8 + 1 + 50, y * 8 + 1 + 870, 7, 7);
				}
			}
		}

		g.dispose();
		bs.show();

	}

	private void moveTokenDown() {
		currentTokenY++;
		eraseToken();
		reDrawTokens();

	}

	private int occupiedBelow(int[][] occupied, int x, int y) {
		if (occupied[x][y + 1] == 0) {
			return 1;
		} else
			return 0;
	}

	private int occupiedRight(int[][] occupied, int x, int y) {
		if (occupied[x + 1][y] == 0) {
			return 1;
		} else
			return 0;
	}

	private void drawRandomToken() {
		currentToken[0] = (int) (Math.random() * 7);
		currentToken[1] = (int) (Math.random() * 4);
		for (int i = 0; i < numTokenCells; i++) {
			occupyCell(
					true,
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + 4,
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
		for (int i = 0; i < numTokenCells; i++) {
			occupyCell(
					false,
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i],
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
	}

	private void reDrawTokens() {
		for (int i = 0; i < numTokenCells; i++) {
			occupyCell(
					true,
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + 4,
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]
							+ currentTokenY);
		}
		for (int i = 0; i < numTokenCells; i++) {
			occupyCell(
					false,
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i],
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
	}

	private void occupyCell(boolean mGrid, int x, int y) {
		if (mGrid) {
			mainGrid[x][y] = 1;
		} else
			nextGrid[x][y] = 1;
		;
	}

	private void eraseToken() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				mainGrid[x][y] = 0;
			}
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				nextGrid[x][y] = 0;
			}
		}
	}

	private void eraseCell(int x, int y) {
		mainGrid[x][y] = 0;

	}

}
