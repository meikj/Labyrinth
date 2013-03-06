/**
 * Represents the board in the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
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
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				Tile TEMP = new Tile(TileType.TSHAPE);
				TEMP.setRotation(RotationAngle.NINETY);
				tiles[i][j] = TEMP;
			}
		}
	}
	
	/**
	 * Draw the game board to the standard output
	 */
	public void draw() {
		String numberTop = "       ";
		String borderTop = "     =====";
		
		for(int i = 0; i < width; i++)
			numberTop += Integer.toString(i + 1) + "    ";
		
		for(int i = 1; i < width; i++)
			borderTop += "=====";
		
		// Display top border
		System.out.println(numberTop);
		System.out.println(borderTop);
		
		// Display each tile
		for(int i = 0; i < width; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 3x3, so therefore consists of top, middle and bottom
			
			String rowTop = "  ||  ";
			String rowMiddle = " " + Integer.toString(width - i) + "||  ";
			String rowBottom = "  ||  ";
			
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
					rowTop += tileContents[0] + "  ||";
					rowMiddle += tileContents[1] + "  ||";
					rowBottom += tileContents[2] + "  ||";
				}
				else {
					// A tile will be placed to the right of this tile, so skip border
					rowTop += tileContents[0] + "  ";
					rowMiddle += tileContents[1] + "  ";
					rowBottom += tileContents[2] + "  ";
				}
			}
			
			// Output full row now
			System.out.println(rowTop);
			System.out.println(rowMiddle);
			System.out.println(rowBottom);
		}
		
		// Display bottom border
		System.out.println(borderTop);
	}
	
	/**
	 * Get the tile at the specified location
	 * 
	 * @param x The x-axis location number
	 * @param y The y-axis location number
	 * @return The particular tile at the specified location. Returns null on error.
	 */
	public Tile getTile(int x, int y) {
		// Check if tile is within valid bounds
		if((x < 0 || x > width) || (y < 0 || y > height))
			return null;
		
		return tiles[x][y];
	}
	
	/**
	 * Set the tile at the specified location to a new tile
	 * 
	 * @param x The x-axis location number
	 * @param y The y-axis location number
	 * @param newTile The new tile for replacing the existing tile with
	 */
	public void setTile(int x, int y, Tile newTile) {
		// Check if tile is within valid bounds
		if((x < 0 || x > width) || (y < 0 || y > height))
			return;
		
		tiles[x][y] = newTile;
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public String getTokenPos() { return tokenPos; }

}
