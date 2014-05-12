import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Game {
	
	private Grid mainGrid, nextGrid;
	private Boolean randomFlag = true;

	public Game() {
		mainGrid = new Grid(10, 20, 5, 5, 44);
		nextGrid = new Grid(5, 5, 20, 20, 8);
	}

	public void update(int updates) {
		
		updateMainGrid();
		updateNextToken();
		updateBankToken();
		if (updates == 58) {
			if (mainGrid.currentTokenReachedBottom()){
				turnOnRandom();
			} else{
				moveToken(0, 1);	
				System.out.println(mainGrid.currentTokenY);
			}
		}
	}


	private void updateBankToken() {
		// TODO Auto-generated method stub

	}

	private void updateNextToken() {
		// TODO Auto-generated method stub

	}

	private void updateMainGrid() {
		if(randomFlag){
			mainGrid.saveToken();
			mainGrid.drawRandomToken();
			turnOffRandom();
		}
		if (Keyboard.keyTyped(KeyEvent.VK_LEFT)) {
			moveToken(-1, 0);
		} else if (Keyboard.keyTyped(KeyEvent.VK_RIGHT)) {
			moveToken(1, 0);
		} else if (Keyboard.keyTyped(KeyEvent.VK_DOWN)) {
			
		}
		if (Keyboard.keyTyped(KeyEvent.VK_ENTER) || Keyboard.keyTyped(KeyEvent.VK_SPACE)) {
			
		}
	}

	private void moveToken(int x, int y) {
		if (mainGrid.checkValidTokenPostion(x, y)) {
			mainGrid.changeCurrentTokenCoords(x, y);
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
				if (grid.getGrid()[x][y] >= 1) {
					g.setColor(Color.BLACK);
					g.fillRect(
						    x * grid.getCellSize() + grid.getXOffset(),
							y * grid.getCellSize() + grid.getYOffset(),
							grid.getCellSize() + occupiedRight(mainGrid.getGrid(), x, y),
							grid.getCellSize() + occupiedBelow(mainGrid.getGrid(), x, y)
					);
					g.setColor(Color.decode(grid.getColor(grid.getGrid()[x][y])));
					g.fillRect(
							x * grid.getCellSize() + grid.getXOffset()+1,  
							y * grid.getCellSize() + grid.getYOffset()+1, 
							grid.getCellSize()-1, grid.getCellSize()-1
					);
				}
			} 
		}
	}


	private int occupiedBelow(int[][] grid, int x, int y) {
		if (y + 1 > mainGrid.getMaxY() - 1) {
			return 1;
		}
		if (grid[x][y + 1] == 0) {
			return 1;
		} else
			return 0;
	}

	private int occupiedRight(int[][] grid, int x, int y) {
		if (x+1 > mainGrid.getMaxX() - 1) {
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
				if (grid.getGrid()[x][y] == 1){
					eraseCell(grid, x, y);					
				}
			}
		}
	}

	private void eraseCell(Grid grid, int x, int y) {
		grid.getGrid()[x][y] = 0;
	}
	
	public void turnOffRandom() {
		this.randomFlag = false;
	}
	
	private void turnOnRandom() {
		this.randomFlag = true;
			
	}
}
