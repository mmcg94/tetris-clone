import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Game {
	
	private Grid mainGrid, nextGrid;
	private int[] currentToken = { 0, 0 };
	private int currentTokenX = 4;
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
	//private int[] tokenColor = new int[7];
	private int numTokenCells = 4;

	public Game() {
		mainGrid = new Grid(10, 20, 5, 5, 44);
		nextGrid = new Grid(5, 5, 20, 20, 8);
		//key = new Keyboard();
		//addKeyListener(key);
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
		// TODO Auto-generated method stub

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
		currentTokenY++;
		eraseToken();
		reDrawTokens();
	}

	private int occupiedBelow(int[][] grid, int x, int y) {
		if (grid[x][y + 1] == 0) {
			return 1;
		} else
			return 0;
	}

	private int occupiedRight(int[][] grid, int x, int y) {
		if (grid[x + 1][y] == 0) {
			return 1;
		} else
			return 0;
	}

	private void drawRandomToken() {
		currentToken[0] = (int) (Math.random() * 7);
		currentToken[1] = (int) (Math.random() * 4);
		for (int i = 0; i < numTokenCells; i++) {
			mainGrid.occupyTokenCell(
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + 4,
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
			nextGrid.occupyTokenCell(
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i],
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
	}

	private void reDrawTokens() {
		for (int i = 0; i < numTokenCells; i++) {
			mainGrid.occupyTokenCell(
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX,
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY);
			nextGrid.occupyTokenCell(
					tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i],
					tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
	}

	private void eraseToken() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				mainGrid.getGrid()[x][y] = 0;
			}
		}
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				nextGrid.getGrid()[x][y] = 0;
			}
		}
	}

	private void eraseCell(int x, int y) {
		mainGrid.getGrid()[x][y] = 0;
	}
}
