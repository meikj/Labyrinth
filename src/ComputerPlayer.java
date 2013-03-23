import java.util.ArrayList;
import java.util.Random;

/**
 * Computer opponent for the player to play against in the Labyrinth
 * game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class ComputerPlayer extends Player {
	
	private ArrayList<Integer> columns;
	private ArrayList<Integer> rows;
	private Random rand;
	
	/**
	 * Generate a computer player opponent using the board
	 * settings as information on moving tiles.
	 * 
	 * @param board The board to gather information from.
	 */
	public ComputerPlayer(Board board) {
		this.columns = new ArrayList<Integer>();
		this.rows = new ArrayList<Integer>();
		this.rand = new Random();
		
		// Add available rows and columns (odd tiles are immovable, so get even)
		for(int i = 1; i <= board.getHeight(); i++) {
			if(i % 2 == 0) {
				rows.add(i);
			}
		}
		for(int i = 1; i <= board.getWidth(); i++) {
			if(i % 2 == 0) {
				columns.add(i);
			}
		}
	}
	
	/**
	 * Get a random column coordinate from the available rows.
	 */
	public Integer getRandomColumn() {
		int r = rand.nextInt(columns.size());
		return columns.get(r);
	}
	
	/**
	 * Get a random row coordinate from the available rows.
	 */
	public Integer getRandomRow() {
		int r = rand.nextInt(rows.size());
		return rows.get(r);
	}

}
