/**
 * Represents the TreasureChase game mode
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
 *
 */
public class TreasureChase {
	
	private Player player;
	private Board board;
	private Leaderboard leaderboard;
	private int round;
	
	/**
	 * Construct a TreasureChase game with a specified player
	 * 
	 * @param player The player to play the game
	 */
	public TreasureChase(Player player) {
		this.player = player;
		this.leaderboard = new Leaderboard();
		
		// Generate the board with random treasure placements, etc.
		this.board = new Board(7, 7);
	}
	
	/**
	 * Construct a TreasureChase game with a specified player and leaderboard
	 * 
	 * @param player The player to play the game
	 * @param leaderboard The leaderboard to use
	 */
	public TreasureChase(Player player, Leaderboard leaderboard) {
		this(player);
		this.leaderboard = leaderboard;
	}
	
	public void moveToken(int x, int y) { return; }
	public void moveTile(int currX, int currY, int newX, int newY) { return; }
	
	/**
	 * Rotate the tile specified to a particular rotation
	 * 
	 * @param x The x coordinate of the existing tile
	 * @param y The y coordinate of the existing tile
	 * @param newAngle The new angle of the tile
	 */
	public void rotateTile(int x, int y, RotationAngle newAngle) {
		Tile tile = board.getTile(x, y);
		tile.setRotation(newAngle);
		board.setTile(x, y, tile);
		player.setMoves(player.getMoves() + 1);
	}
	
	/**
	 * Replace an existing tile on the board with a new one
	 * 
	 * @param x The x coordinate of the existing tile
	 * @param y The y coordinate of the existing tile
	 * @param newTile The new tile
	 */
	public void replaceTile(int x, int y, Tile newTile) {
		board.setTile(x, y, newTile);
		player.setMoves(player.getMoves() + 1);
	}
	
	/**
	 * Update the interface
	 */
	public void update() {
		board.draw();
	}
	
	public void save() { return; }
	public void end() { return; }
	
	public int getRound() { return round; }
	
}
