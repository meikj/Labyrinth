import java.util.Random;

/**
 * Represents a game board containing tiles.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.24032013
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
		this.tiles = new Tile[height][width];
		
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
		
		tiles[getInternalRow(tokenPos[1])][getInternalColumn(tokenPos[0])].setToken(true);
	}
	
	/**
	 * Get the tile at the specified location.
	 * 
	 * @param column The column coordinate of the tile.
	 * @param row The row coordinate of the tile.
	 * @return The particular tile at the specified location. Returns null on error.
	 * @throws IllegalArgumentException If either the row or column is not within range.
	 */
	public Tile getTile(int column, int row) throws IllegalArgumentException {
		// Check if arguments are valid
		if(column < 1 || column > width) {
			throw new IllegalArgumentException("Specified column not within range");
		} else if(row < 1 || row > height) {
			throw new IllegalArgumentException("Specified row not within range");
		}
		
		// Calculate internal coordinates and return corresponding tile
		return tiles[getInternalRow(row)][getInternalColumn(column)];
	}
	
	/**
	 * Set the tile at the specified location to a new tile.
	 * 
	 * @param column The column coordinate of the tile.
	 * @param row The row coordinate of the tile.
	 * @param newTile The new tile for replacing the existing tile with.
	 * @throws IllegalArgumentException If either the row or column is not within range.
	 */
	public void setTile(int column, int row, Tile newTile) throws IllegalArgumentException{
		// Check if arguments are valid
		if(column < 1 || column > width) {
			throw new IllegalArgumentException("Specified column not within range");
		} else if(row < 1 || row > height) {
			throw new IllegalArgumentException("Specified row not within range");
		}
		
		// Calculate internal coordinates and return corresponding tile
		tiles[getInternalRow(row)][getInternalColumn(column)] = newTile;
	}
	
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
	
	/**
	 * Get the width of the board.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the board.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get all the tiles on the board.
	 * 
	 * @return All the tiles on the board in the form of an array.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

}
