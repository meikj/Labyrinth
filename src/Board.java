import java.util.Random;

/**
 * Represents the board in the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.07032013
 *
 */
public class Board {
	
	private Tile[][] tiles;
	private int width;
	private int height;
	private int[] tokenPos;
	
	/**
	 * Construct a board of a particular size
	 * 
	 * @param width The width of the board
	 * @param height The height of the board
	 */
	public Board(int width, int height) {
		Random r = new Random();
		
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		
		// Set outside tiles to "walled" tiles
		
		// Top and bottom row tiles
		for(int i = 0; i < width; i++) {
			// Top row
			tiles[0][i] = new Tile(TileType.TSHAPE, RotationAngle.NINETY);;
			
			// Bottom row
			tiles[height - 1][i] = new Tile(TileType.TSHAPE, RotationAngle.TWOHUNDREDANDSEVENTY);
		}
		
		// Side tiles (skip top and bottom row)
		for(int i = 1; i < (height - 1); i++) {
			// Left side
			tiles[i][0] = new Tile(TileType.TSHAPE);
			
			// Right side
			tiles[i][width - 1] = new Tile(TileType.TSHAPE, RotationAngle.HUNDREDANDEIGHTY);
		}
		
		// Corners
		tiles[0][0] = new Tile(TileType.CORNER);
		tiles[0][width - 1] = new Tile(TileType.CORNER, RotationAngle.NINETY);
		tiles[height - 1][width - 1] = new Tile(TileType.CORNER, RotationAngle.HUNDREDANDEIGHTY);
		tiles[height - 1][0] = new Tile(TileType.CORNER, RotationAngle.TWOHUNDREDANDSEVENTY);
		
		// Set inside tiles to random
		// Skip top row and bottom row
		for(int i = 1; i < (height - 1); i++) {
			// Skip far left and far right columns
			for(int j = 1; j < (width - 1); j++) {
				Tile randomTile;
				int rNumber = r.nextInt(4); // CORNER, LINE, TSHAPE, CROSS
				TileType[] tileTypes = TileType.values();
				
				randomTile = new Tile(tileTypes[rNumber]);
				tiles[i][j] = randomTile;
			}
		}
		
		// Set token position (bottom left corner)
		this.tokenPos = new int[2];
		this.tokenPos[0] = height - 1; // row
		this.tokenPos[1] = 0; // column
		
		tiles[tokenPos[0]][tokenPos[1]].setToken(true);
	}
	
	/**
	 * Draw the game board
	 */
	public void draw() {
		String numberTop = "       ";
		String border = "     =====";
		
		for(int i = 0; i < width; i++)
			numberTop += Integer.toString(i + 1) + "    ";
		
		for(int i = 1; i < width; i++)
			border += "=====";
		
		// Display top border
		
		System.out.println(border);
		
		// Display each tile
		for(int i = 0; i < width; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 5x5, so therefore consists of 5 sections
			
			String rowTop = "  || ";
			String rowTM = "  || ";
			String rowMiddle = " " + Integer.toString(width - i) + "|| ";
			String rowMB = "  || ";
			String rowBottom = "  || ";
			
			for(int j = 0; j < height; j++) {
				// Split the tile up into its relevant sections
				// tile[0] = top, tile[1] = top-middle
				// tile[2] = middle, tile[3] = middle-bottom
				// tile[4] = bottom
				
				String[] tile = tiles[i][j].getTileString();
				
				// Check which tile it is for correct border placement
				if(j == (height - 1)) {
					// Last tile on row requires a border
					rowTop += tile[0] + " ||";
					rowTM += tile[1] + " ||";
					rowMiddle += tile[2] + " ||";
					rowMB += tile[3] + " ||";
					rowBottom += tile[4] + " ||";
				}
				else {
					// A tile will be placed to the right of this tile, so skip border
					rowTop += tile[0];
					rowTM += tile[1];
					rowMiddle += tile[2];
					rowMB += tile[3];
					rowBottom += tile[4];
				}
			}
			
			// Output full row now
			System.out.println(rowTop);
			System.out.println(rowTM);
			System.out.println(rowMiddle);
			System.out.println(rowMB);
			System.out.println(rowBottom);
		}
		
		// Display bottom border
		
		System.out.println(border);
		System.out.println(numberTop);
	}
	
	/**
	 * Get the tile at the specified location
	 * 
	 * @param column The column coordinate of the tile
	 * @param row The row coordinate of the tile
	 * @return The particular tile at the specified location. Returns null on error.
	 */
	public Tile getTile(int column, int row) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return null;
		
		return tiles[internalRow(row)][internalColumn(column)];
	}
	
	/**
	 * Set the tile at the specified location to a new tile
	 * 
	 * @param column The column coordinate of the tile
	 * @param row The row coordinate of the tile
	 * @param newTile The new tile for replacing the existing tile with
	 */
	public void setTile(int column, int row, Tile newTile) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return;
		
		tiles[internalRow(row)][internalColumn(column)] = newTile;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int[] getTokenPos() { return tokenPos; }
	
	/**
	 * Convert a row to its internal array equivalent
	 * 
	 * @param row The row as labelled on the board
	 * @return The internal array equivalent
	 */
	private int internalRow(int row) {
		return (getHeight() - row);
	}
	
	/**
	 * Convert a column to its internal array equivalent
	 * 
	 * @param column The column as labelled on the board
	 * @return The internal array equivalent
	 */
	private int internalColumn(int column) {
		return column - 1;
	}

}
