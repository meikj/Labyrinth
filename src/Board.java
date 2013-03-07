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
	private String tokenPos; // In the format of, for example, A4?
	
	/**
	 * Construct a board of a particular size
	 * 
	 * @param width The width of the board
	 * @param height The height of the board
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		
		Random r = new Random();
		
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
	}
	
	/**
	 * Draw the game board
	 */
	public void draw() {
		String numberTop = "      ";
		String borderTop = "     ===";
		
		for(int i = 0; i < width; i++)
			numberTop += Integer.toString(i + 1) + "  ";
		
		for(int i = 1; i < width; i++)
			borderTop += "===";
		
		// Display top border
		System.out.println(numberTop);
		System.out.println(borderTop);
		
		// Display each tile
		for(int i = 0; i < width; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 3x3, so therefore consists of top, middle and bottom
			
			String rowTop = "  || ";
			String rowMiddle = " " + Integer.toString(width - i) + "|| ";
			String rowBottom = "  || ";
			
			for(int j = 0; j < height; j++) {
				// Split the tile up into its top, middle and bottom
				// tileContents[0] = top
				// tileContents[1] = middle
				// tileContents[2] = bottom
				
				String tileString = tiles[i][j].getTileString();
				String[] tileContents = tileString.split("\n");
				
				// Check which tile it is for correct border placement
				if(j == (height - 1)) {
					// Last tile on row requires a border
					rowTop += tileContents[0] + " ||";
					rowMiddle += tileContents[1] + " ||";
					rowBottom += tileContents[2] + " ||";
				}
				else {
					// A tile will be placed to the right of this tile, so skip border
					rowTop += tileContents[0] + "";
					rowMiddle += tileContents[1] + "";
					rowBottom += tileContents[2] + "";
				}
			}
			
			// Output full row now
			System.out.println(rowTop);
			System.out.println(rowMiddle);
			System.out.println(rowBottom);
		}
		
		// Display bottom border
		System.out.println();
		System.out.println(borderTop);
	}
	
	/**
	 * Get the tile at the specified location
	 * 
	 * @param row The row coordinate of the tile
	 * @param column The column coordinate of the tile
	 * @return The particular tile at the specified location. Returns null on error.
	 */
	public Tile getTile(int row, int column) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return null;
		
		return tiles[row][column];
	}
	
	/**
	 * Set the tile at the specified location to a new tile
	 * 
	 * @param row The row coordinate of the tile
	 * @param column The column coordinate of the tile
	 * @param newTile The new tile for replacing the existing tile with
	 */
	public void setTile(int row, int column, Tile newTile) {
		// Check if tile is within valid bounds
		if((row < 0 || row > width) || (column < 0 || column > height))
			return;
		
		tiles[row][column] = newTile;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public String getTokenPos() { return tokenPos; }

}
