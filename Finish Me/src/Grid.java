public class Grid {

	private int maxX;
	private int maxY;
	private int cellSize;
	private int screenXOffset;
	private int screenYOffset;
	private int[][] coordinateGrid;
	private final int numTokenCells = 4;
	public int currentTokenX = 0;
	public int currentTokenY = 0;
	private int[] currentToken = { 0, 0 };

	public Grid(int maxX, int maxY, int screenXOffset, int screenYOffset, int cellSize) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.cellSize = cellSize;
		this.screenXOffset = screenXOffset;
		this.screenYOffset = screenYOffset;
		coordinateGrid = new int[maxX][maxY];
	}

	public void occupyTokenCell(int x, int y){
		if (x > 9 || x < 0 || y < 0 || y > 19) {
			System.out.print("Failed to occupy token cell.");
			return;
		}
		coordinateGrid[x][y] = 1;
	}
	
	public void occupyNonTokenCell(int x, int y){
		if (x > 9 || x < 0 || y < 0 || y > 19) {
			System.out.print("Failed to occupy non token cell.");
			return;
		}
		coordinateGrid[x][y] = 2;
	}
	
	public boolean checkValidTokenPostion(int xOffset, int yOffset) {
		for (int i = 0; i < numTokenCells; i++) { 
			int x = Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i]; 
			int y = Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]; 
			if (x + currentTokenX + xOffset > maxX - 1 || x + currentTokenX  + xOffset < 0 || 
					y + currentTokenY + yOffset > maxY - 1 || y + currentTokenY + yOffset < 0 ||
					coordinateGrid[x + currentTokenX + xOffset][y] == 2 || 
					coordinateGrid[x][y + currentTokenY + yOffset] == 2) 
			{
				return false;			
			}
		}
		return true;
	}
	
	public void reDrawTokens() {
		for (int i = 0; i < numTokenCells; i++) {
			occupyTokenCell(
					Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + currentTokenX,
					Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i] + currentTokenY);
		}
	}
	
	public void drawRandomToken() {
		currentToken[0] = (int) (Math.random() * 7);
		currentToken[1] = (int) (Math.random() * 4);
		for (int i = 0; i < numTokenCells; i++) {
			occupyTokenCell(
					Token.tokenRotationCellXPosition[currentToken[0]][currentToken[1]][i] + 4,
					Token.tokenRotationCellYposition[currentToken[0]][currentToken[1]][i]);
		}
	}
	
	public int getMaxX() { return maxX; }
	public int getMaxY() { return maxY; }
	public int getCellSize() { return cellSize; }
	public int getXOffset() { return screenXOffset; }
	public int getYOffset() { return screenYOffset; }
	public int[][] getGrid() { return coordinateGrid; }

	public void changeCurrentTokenCoords(int x, int y) {
		currentTokenX = currentTokenX + x;
		currentTokenY = currentTokenY + y;
	}


}
