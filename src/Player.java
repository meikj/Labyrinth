import java.util.Random;

/**
 * Represents the player in the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public class Player {
	
	private int score;
	private int moves;
	private Tile spareTile;
	
	/**
	 * Construct a player object.
	 */
	public Player() {
		this.score = 0;
		this.moves = 0;
		
		// Generate a random spare tile
		Random r = new Random();
		int randomSelection = r.nextInt(4); // CORNER, LINE, TSHAPE, CROSS
		
		TileType[] tileTypes = TileType.values();
		
		this.spareTile = new Tile(tileTypes[randomSelection]);
	}
	
	public void setScore(int newScore) { score = newScore; }
	public void setMoves(int newMoves) { moves = newMoves; }
	public void setSpareTile(Tile newTile) { spareTile = newTile; }
	
	public int getScore() { return score; }
	public int getMoves() { return moves; }
	public Tile getSpareTile() { return spareTile; }

}
