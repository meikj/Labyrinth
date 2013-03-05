/**
 * Represents the leaderboard in the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.04032013
 *
 */
public class Leaderboard {
	
	private String[][] scores;
	private int maxEntries;
	
	/**
	 * Construct a standard empty leaderboard
	 */
	public Leaderboard() {
		// Set the default max entries to 10
		this.maxEntries = 10;
		
		for(int i = 0; i < maxEntries; i++) {
			scores[i][0] = "John Doe";
			scores[i][1] = "No Score";
		}
	}
	
	/**
	 * Construct a leader board from an existing leader board file
	 * 
	 * @param file The leaderboard file to load
	 */
	public Leaderboard(String file) {
		this();
	}
	
	/**
	 * Submit a score to the leaderboard
	 * 
	 * @param name The name of the player
	 * @param score The score to submit
	 * @return Returns whether or not the submission was successful
	 */
	public boolean submit(String name, int score) {
		// Check if any spaces spare, if score is eligible, etc.
		return false;
	}
	
	public String[][] getScores() { return scores; }
	public int getMaxEntries() { return maxEntries; }

}
