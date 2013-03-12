import java.util.Scanner;

/**
 * Represents a text based user interface for use in the Labyrinth game. This user interface
 * works with a GameMode object.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version v0.1.12032013
 *
 */
public class UserInterface {
	
	private GameMode game;
	private boolean running;
	private Scanner input;
	
	/**
	 * Construct a new user interface to interface with a valid
	 * game mode.
	 * 
	 * @param game The game mode object to manipulate.
	 */
	public UserInterface(GameMode game) {
		this.game = game;
		this.input = new Scanner(System.in);
		this.running = true;
	}
	
	/**
	 * Run the user interface.
	 */
	public void run() {
		while(running) {
			// Main game loop
			game.update();
			 
			System.out.print("\n> ");
			parse(prompt());
		}
		
		// Game loop closed, call clean up code
		input.close();
	}
	
	/**
	 * Prompt the user for input.
	 * 
	 * @return The users input in a tokenized form.
	 */
	public String[] prompt() {
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		return tokens;
	}
	
	/**
	 * Parse user input and perform appropriate operation(s)
	 * 
	 * @param inputArgs The list of arguments the user passed
	 */
	public void parse(String[] inputArgs) throws NumberFormatException, IndexOutOfBoundsException {
		if(inputArgs[0].toLowerCase().equals("insert")) {
			// Insertion command called
			if(inputArgs.length < 3) {
				System.out.println("Usage:\tinsert column <column_no>\n\tinsert row <row_no>");
				return;
			}
			
			// Check if insertion into row or column
			if(inputArgs[1].equals("column")) {
				int column_no = 0;
				
				try {
					column_no = Integer.parseInt(inputArgs[2]);
				}
				catch(NumberFormatException e) {
					throw new NumberFormatException("Invalid column number entered");
				}
				
				game.insertColumn(column_no);
			}
			else if(inputArgs[1].equals("row")) {
				int row_no = 0;
				
				try {
					row_no = Integer.parseInt(inputArgs[2]);
				}
				catch(NumberFormatException e) {
					throw new NumberFormatException("Invalid row number entered");
				}
				
				game.insertRow(row_no);
			}
			else {
				// Invalid argument passed
				System.out.println("Usage:\tinsert column <column_no>\n\tinsert row <row_no>");
				return;
			}
		}
		else if(inputArgs[0].toLowerCase().equals("rotate")) {
			// Rotation command called
			if(inputArgs.length == 2) {
				// Rotate spare tile
				try {
					game.rotateTile(Integer.parseInt(inputArgs[1]));
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid angle: must be 90, 180 or 270");
					return;
				}
			}
			else if(inputArgs.length == 4) {
				// Rotate tile on board
				int column = Integer.parseInt(inputArgs[1]);
				int row = Integer.parseInt(inputArgs[2]);
				
				try {
					game.rotateTile(column, row, Integer.parseInt(inputArgs[3]));
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid angle: must be 90, 180 or 270");
					return;
				}
			}
			else {
				System.out.println("Usage:\trotate <degrees> (rotate spare tile)\n\trotate <column_no> <row_no> <degrees>");
			}
		}
		else if(inputArgs[0].toLowerCase().equals("exit")) {
			// Exit command called
			running = false;
		}
		else if(inputArgs[0].toLowerCase().equals("help")) {
			// Help command called
			System.out.println("insert \t Insert spare tile to specified location.");
			System.out.println("rotate \t Rotate either spare tile or specified tile by a number of degrees.");
			System.out.println("exit \t Exit the game.");
			System.out.println("help \t Display available game options. Type help <command_name> for more info.");
			
			// TODO: Implement specific command help dialogue
		}
		else {
			System.out.println("Invalid command: please enter 'help' for more information.");
		}
	}
	
	/**
	 * Check whether or not the user interface is running.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Set whether or not the user interface is running.
	 */
	public void setRunning(boolean state) {
		running = state;
	}

}
