import java.util.ArrayList;

/**
 * Represents the leaderboard in the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public class Leaderboard {
	
	private ArrayList<String> names;
	private ArrayList<Integer> scores;
	
	/**
	 * Construct a standard empty leaderboard.
	 */
	public Leaderboard() {
		names = new ArrayList<String>();
		scores = new ArrayList<Integer>();
	}
	
	/**
	 * Construct a leader board from an existing leader board file.
	 * 
	 * @param file The leaderboard file to load.
	 */
	public Leaderboard(String file) {
		this();
	}
	
	/**
	 * Submit a score to the leaderboard.
	 * 
	 * @param name The name of the player.
	 * @param score The score to submit.
	 */
	public void submit(String name, int score) {
		// TODO
	}
	
	
	/**
	 * Get the name entries. Same index is used for scores.
	 * @return
	 */
	public ArrayList<String> getNames() {
		return names;
	}
	
	
	/**
	 * Get the score entries. Same index is used for names.
	 * @return
	 */
	public ArrayList<Integer> getScores() {
		return scores;
	}

}
