/**
 * Represents the board in the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
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
	}
	
	public void draw() {
		// TODO
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
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public String getTokenPos() { return tokenPos; }

}
