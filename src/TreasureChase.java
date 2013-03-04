/**
 * Represents the TreasureChase game mode
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
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
		
		// Generate the board with random treasure placements, etc.
		this.board = null;
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
	
	public boolean moveToken(int x, int y) { return false; }
	public boolean moveTile(int currX, int currY, int newX, int newY) { return false; }
	public boolean rotateTile(int x, int y, RotationAngle newAngle) { return false; }
	public boolean replaceTile(int x, int y, Tile newTile) { return false; }
	
	public void save() { return; }
	public void end() { return; }
	
	public int getRound() { return round; }
	
}
