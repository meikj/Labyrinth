import java.util.Random;

/**
 * Represents a game board containing tiles.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.12032013
 *
 */
public class Board {
	
	private Tile[][] tiles;
	private int width;
	private int height;
	private int[] tokenPos;
	private Random r;
	
	/**
	 * Construct a board of a particular size.
	 * 
	 * @param width The width of the board.
	 * @param height The height of the board.
	 */
	public Board(int width, int height) {
		this.r = new Random();
		
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
		
		// Randomise tiles
		for(int i = 0; i < height; i++) {
			// Skip far left and far right columns
			for(int j = 0; j < width; j++) {
				Tile randomTile;
				int rNumber = r.nextInt(4); // CORNER, LINE, TSHAPE, CROSS
				int rotation = r.nextInt(4); // DEFAULT, NINETY, HUNDREDANDEIGHTY, TWOHUNDREDANDSEVENTY
				TileType[] tileTypes = TileType.values();
				RotationAngle[] rotationAngles = RotationAngle.values();
				
				randomTile = new Tile(tileTypes[rNumber], rotationAngles[rotation]);
				tiles[i][j] = randomTile;
			}
		}
		
		// Set immovable tiles (i,j) where i and j are odd
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				// Internally board starts at 0, but to the user it starts at 1
				// so we need to perform odd calculations on what the user sees
				if(((i + 1) % 2 != 0) && ((j + 1) % 2 != 0)) {
					// Found an odd combination
					tiles[i][j].setMovable(false);
					
					// Check if not on the edge (must be cross in this case)
					if(i != 0 && i != height - 1 && j != 0 && j != width - 1) {
						tiles[i][j].setType(TileType.CROSS);
					}
					else {
						// Tile is on the edge, determine which edge
						if(i == 0 && j == 0) {
							// Top left hand corner
							tiles[i][j] = new Tile(TileType.CORNER, RotationAngle.DEFAULT, false);
						}
						else if(i == 0 && j == width - 1) {
							// Top right hand corner
							tiles[i][j] = new Tile(TileType.CORNER, RotationAngle.NINETY, false);
						}
						else if(i == height - 1 && j == width - 1) {
							// Bottom right hand corner
							tiles[i][j] = new Tile(TileType.CORNER, RotationAngle.HUNDREDANDEIGHTY, false);
						}
						else if(i == height - 1 && j == 0) {
							// Bottom left hand corner
							tiles[i][j] = new Tile(TileType.CORNER, RotationAngle.TWOHUNDREDANDSEVENTY, false);
						}
						else if(i == 0) {
							// Top most row
							// Set to either horizontal line or horizontal T-shape
							int type = r.nextInt(2); // LINE, TSHAPE
							
							if(type == 0)
								tiles[i][j] = new Tile(TileType.LINE, RotationAngle.NINETY, false);
							else
								tiles[i][j] = new Tile(TileType.TSHAPE, RotationAngle.NINETY, false);
						}
						else if(i == height - 1) {
							// Bottom most row
							// Set to either horizontal line or horizontal T-shape
							int type = r.nextInt(2); // LINE, TSHAPE
							
							if(type == 0)
								tiles[i][j] = new Tile(TileType.LINE, RotationAngle.NINETY, false);
							else
								tiles[i][j] = new Tile(TileType.TSHAPE, RotationAngle.TWOHUNDREDANDSEVENTY, false);
						}
						else if(j == 0) {
							// Left most column
							// Set to either horizontal line or horizontal T-shape
							int type = r.nextInt(2); // LINE, TSHAPE
							
							if(type == 0)
								tiles[i][j] = new Tile(TileType.LINE, RotationAngle.DEFAULT, false);
							else
								tiles[i][j] = new Tile(TileType.TSHAPE, RotationAngle.DEFAULT, false);
						}
						else if(j == width - 1) {
							// Right most column
							// Set to either horizontal line or horizontal T-shape
							int type = r.nextInt(2); // LINE, TSHAPE
							
							if(type == 0)
								tiles[i][j] = new Tile(TileType.LINE, RotationAngle.DEFAULT, false);
							else
								tiles[i][j] = new Tile(TileType.TSHAPE, RotationAngle.HUNDREDANDEIGHTY, false);
						}
					}
				}
			}
		}
		
		// Set token position (bottom left corner)
		this.tokenPos = new int[2];
		this.tokenPos[0] = 1; // column
		this.tokenPos[1] = 1; // row
		
		tiles[getInternalRow(tokenPos[1])][getInternalColumn(tokenPos[1])].setToken(true);
	}
	
	/**
	 * Draw the game board.
	 */
	public void draw() {
		// Form horizontal border
		String numberTop = "         ";
		String border = "      =======";
		
		for(int i = 0; i < width; i++)
			if((i + 1) < 10)
				numberTop += Integer.toString(i + 1) + "      ";
			else
				numberTop += Integer.toString(i + 1) + "     ";
		
		for(int i = 1; i < width; i++)
			border += "=======";
		
		// Display top border
		System.out.println(border);
		
		// Display each tile
		for(int i = 0; i < width; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 7x5, so therefore consists of 5 row sections
			
			String number = "";
			
			if((width - i) < 10) {
				number = Integer.toString(width - i) + " ";
			}
			else {
				number = Integer.toString(width - i);
			}
			
			String rowTop    = "   || ";
			String rowTM     = "   || ";
			String rowMiddle = " " + number + "|| ";
			String rowMB     = "   || ";
			String rowBottom = "   || ";
			
			for(int j = 0; j < height; j++) {
				// Split the tile up into its relevant sections
				// tile[0] = top, tile[1] = top-middle
				// tile[2] = middle, tile[3] = middle-bottom
				// tile[4] = bottom
				
				String[] tile = tiles[i][j].getTileString();
				
				// Check which tile it is for correct border placement
				if(j == (height - 1)) {
					// Last tile on row requires a border
					rowTop    += tile[0] + " ||";
					rowTM     += tile[1] + " ||";
					rowMiddle += tile[2] + " ||";
					rowMB     += tile[3] + " ||";
					rowBottom += tile[4] + " ||";
				}
				else {
					// A tile will be placed to the right of this tile, so skip border
					rowTop    += tile[0];
					rowTM     += tile[1];
					rowMiddle += tile[2];
					rowMB     += tile[3];
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
	 * Get the tile at the specified location.
	 * 
	 * @param column The column coordinate of the tile.
	 * @param row The row coordinate of the tile.
	 * @return The particular tile at the specified location. Returns null on error.
	 */
	public Tile getTile(int column, int row) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return null;
		
		return tiles[getInternalRow(row)][getInternalColumn(column)];
	}
	
	/**
	 * Set the tile at the specified location to a new tile.
	 * 
	 * @param column The column coordinate of the tile.
	 * @param row The row coordinate of the tile.
	 * @param newTile The new tile for replacing the existing tile with.
	 */
	public void setTile(int column, int row, Tile newTile) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return;
		
		tiles[getInternalRow(row)][getInternalColumn(column)] = newTile;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	/**
	 * Get the current token position as a board coordinate.
	 * 
	 * @return The token position as an integer array. Index 0 is
	 * the column, index 1 is the row.
	 */
	public int[] getTokenPos() {
		return tokenPos;
	}
	
	/**
	 * Set the current token position as a board coordinate.
	 * 
	 * @param column The column number of the token.
	 * @param row The row number of the token.
	 */
	public void setTokenPos(int column, int row) {
		tokenPos[0] = column;
		tokenPos[1] = row;
	}
	
	/**
	 * Convert a row to its internal array equivalent.
	 * 
	 * @param row The row as labelled on the board.
	 * @return The internal array equivalent.
	 */
	public int getInternalRow(int row) {
		return (getHeight() - row);
	}
	
	/**
	 * Convert a column to its internal array equivalent.
	 * 
	 * @param column The column as labelled on the board.
	 * @return The internal array equivalent.
	 */
	public int getInternalColumn(int column) {
		return column - 1;
	}

}
