/**
 * Represents the player in the Labyrinth game
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
	 * Construct a player object
	 */
	public Player() {
		this.score = 0;
		this.moves = 0;
		
		// Generate a random spare tile, perhaps?
		this.spareTile = null;
	}
	
	public void setScore(int newScore) { score = newScore; }
	public void setMoves(int newMoves) { moves = newMoves; }
	
	public int getScore() { return score; }
	public int getMoves() { return moves; }
	public Tile getSpareTile() { return spareTile; }

}
