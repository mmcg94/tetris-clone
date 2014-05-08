import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game {
	
	private Grid mainGrid, nextGrid;

	public Game() {
		mainGrid = new Grid(10, 20, 5, 5, 44);
		nextGrid = new Grid(5, 5, 20, 20, 8);
	}

	public void update(int updates) {
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
		if (Keyboard.keyTyped(KeyEvent.VK_LEFT)) {
			moveTokenLeft();
		} else if (Keyboard.keyTyped(KeyEvent.VK_RIGHT)) {
			moveTokenRight();
		} else if (Keyboard.keyTyped(KeyEvent.VK_DOWN)) {
			
		}
		if (Keyboard.keyTyped(KeyEvent.VK_ENTER) || Keyboard.keyTyped(KeyEvent.VK_SPACE)) {
			
		}

	}
	
	private void moveTokenLeft() {
		if (mainGrid.checkValidTokenPostion(-1)) {
			mainGrid.currentTokenX--;
			eraseToken(mainGrid);
			mainGrid.reDrawTokens();
		}
	}

	private void moveTokenRight() {
		if (mainGrid.checkValidTokenPostion(1)) {
			mainGrid.currentTokenX++;
			eraseToken(mainGrid);
			mainGrid.reDrawTokens();
		}
			
	}

	public void render(Window window) {
		BufferStrategy bs = window.getBufferStrategy();
		Graphics g = bs .getDrawGraphics();
		try {
			BufferedImage image = ImageIO.read(Game.class
					.getResource("/Layout.png"));
			g.drawImage(image, 0, 0, window.getWidth(), window.getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		drawCell(g, mainGrid);
		drawCell(g, nextGrid);
			
		window.display();
	}

	private void drawCell(Graphics g, Grid grid) {
		for (int x = 0; x < grid.getMaxX(); x++) {
			for (int y = 0; y < grid.getMaxY(); y++) {
				if (grid.getGrid()[x][y] == 1) {
					g.setColor(Color.BLACK);
					g.fillRect(
						    x * grid.getCellSize() + grid.getXOffset(),
							y * grid.getCellSize() + grid.getYOffset(),
							grid.getCellSize() + occupiedRight(mainGrid.getGrid(), x, y),
							grid.getCellSize() + occupiedBelow(mainGrid.getGrid(), x, y)
					);
					g.setColor(Color.RED);
					g.fillRect(
							x * grid.getCellSize() + grid.getXOffset()+1,  
							y * grid.getCellSize() + grid.getYOffset()+1, 
							grid.getCellSize()-1, grid.getCellSize()-1
					);
				}
			} 
		}
	}

	private void moveTokenDown() {
		mainGrid.currentTokenY++;
		eraseToken(mainGrid);
		mainGrid.reDrawTokens();
	}

	private int occupiedBelow(int[][] grid, int x, int y) {
		if (grid[x][y + 1] == 0) {
			return 1;
		} else
			return 0;
	}

	private int occupiedRight(int[][] grid, int x, int y) {
		if (x+1 > 9) {
			return 1;
		}
		if (grid[x + 1][y] == 0) {
			return 1;
		} else
			return 0;
	}


	private void eraseToken(Grid grid) {
		for (int x = 0; x < grid.getMaxX(); x++) {
			for (int y = 0; y < grid.getMaxY(); y++) {
				eraseCell(grid, x, y);
			}
		}
	}

	private void eraseCell(Grid grid, int x, int y) {
		grid.getGrid()[x][y] = 0;
	}
}
