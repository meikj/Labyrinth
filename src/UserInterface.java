import java.util.Scanner;

/**
 * Represents a text based user interface for use in the Labyrinth game. This user interface
 * works with a GameMode object.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version v0.1.22032013
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
			update();
			
			if(game.hasWon()) {
				onTreasureChaseWin();
				setRunning(false);
			} else {
				System.out.print("\n> ");
				parse(prompt());
			}
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
			if(inputArgs.length < 4) {
				System.out.println("Usage:\tinsert column <column_no>\n\tinsert row <row_no>");
				return;
			}
			
			// Check if insertion into row or column
			if(inputArgs[1].equals("column")) {
				int column_no = 0;
				
				try {
					column_no = Integer.parseInt(inputArgs[3]);
				}
				catch(NumberFormatException e) {
					throw new NumberFormatException("Invalid column number entered");
				}
				
				// Check whether to push in from top or bottom
				try {
					if(inputArgs[2].equals("top"))
							game.insertColumn(column_no, Direction.TOP);
					else if(inputArgs[2].equals("bottom"))
						game.insertColumn(column_no, Direction.BOTTOM);
				}
				catch(IllegalMoveException e) {
					System.out.println(e.getMessage());
					enterPrompt();
				}
			}
			else if(inputArgs[1].equals("row")) {
				int row_no = 0;
				
				try {
					row_no = Integer.parseInt(inputArgs[3]);
				}
				catch(NumberFormatException e) {
					throw new NumberFormatException("Invalid row number entered");
				}
				
				// Check whether to push in from left or right
				try {
					if(inputArgs[2].equals("left"))
						game.insertRow(row_no, Direction.LEFT);
					else if(inputArgs[2].equals("right"))
						game.insertRow(row_no, Direction.RIGHT);
				}
				catch(IllegalMoveException e) {
					System.out.println(e.getMessage());
					enterPrompt();
				}
			}
			else {
				// Invalid argument passed
				System.out.println("Usage: insert column <top/bottom> <column_no>");
				System.out.println("Usage: insert row <left/right> <row_no>");
				enterPrompt();
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
					System.out.println(e.getMessage());
					enterPrompt();
				}
			}
			else {
				System.out.println("Usage: rotate <degrees>, where degrees is 90, 180 or 270");
				enterPrompt();
			}
		}
		else if(inputArgs[0].toLowerCase().equals("move")) {
			// Move command called
			if(inputArgs.length < 2) {
				System.out.println("Usage: move <up/down/left/right>");
				enterPrompt();
			}
			else {
				try {
					if(inputArgs[1].equals("up")) {
						// Move up
						game.moveTokenUp();
					}
					else if(inputArgs[1].equals("down")) {
						// Move down
						game.moveTokenDown();
					}
					else if(inputArgs[1].equals("left")) {
						// Move left
						game.moveTokenLeft();
					}
					else if(inputArgs[1].equals("right")) {
						// Move token right
						game.moveTokenRight();
					}
					else {
						System.out.println("Usage: move <up/down/left/right>");
						enterPrompt();
					}
				}
				catch(IllegalMoveException e) {
					System.out.println(e.getMessage());
					enterPrompt();
				}
			}
		}
		else if(inputArgs[0].toLowerCase().equals("exit")) {
			// Exit command called
			running = false;
		}
		else if(inputArgs[0].toLowerCase().equals("help")) {
			// Help command called
			if(inputArgs.length < 2) {
				System.out.println("insert \t Insert spare tile into a specified row or column from some direction.");
				System.out.println("rotate \t Rotate spare tile by a number of degrees.");
				System.out.println("move \t Move token either up, down, left or right.");
				System.out.println("exit \t Exit the game.");
				System.out.println("help \t Display available game options, or specific help through 'help <command>'.");
			}
			else {
				if(inputArgs[1].equals("insert")) {
					System.out.println("Usage: insert column <top/bottom> <column_no>");
					System.out.println("Usage: insert row <left/right> <row_no>");
				}
				else if(inputArgs[1].equals("rotate")) {
					System.out.println("Usage: rotate <degrees>, where degrees is 90, 180 or 270");
				}
				else if(inputArgs[1].equals("move")) {
					System.out.println("Usage: move <up/down/left/right>");
				}
			}
			enterPrompt();
		}
		else {
			System.out.println("Invalid command: please enter 'help' for more information.");
			enterPrompt();
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
	
	/**
	 * Display and run the main menu. This implicitly calls run().
	 */
	public void runMenu() {
		System.out.println("1. Play game");
		System.out.println("2. Load game");
		System.out.println("3. Options");
		System.out.println("4. Quit");
		System.out.println();
		
		System.out.print("Choice: ");
		
		String choiceInput;
		int choice;
		
		while(true) {
			choiceInput = input.nextLine();
			
			try {
				choice = Integer.parseInt(choiceInput);
				
				if(choice < 0 || choice > 4)
					throw new NumberFormatException();
				
				break;
			}
			catch(NumberFormatException e) {
				System.out.println("Please enter a valid menu option!");
				enterPrompt();
			}
		}
		
		// TODO
	}
	
	/**
	 * Display and wait for enter. Useful to pause states until user is ready.
	 */
	public void enterPrompt() {
		System.out.print("\nPlease press [ENTER] to continue... ");
		input.nextLine();
	}
	
	/**
	 * Update the interface.
	 */
	public void update() {
		// Draw the board
		game.getBoard().draw();
		
		// Draw the "HUB"
		Tile spareTile = game.getPlayer().getSpareTile();
		String[] tileRows = spareTile.getTileString();
		
		String roundString = Integer.toString(game.getRound());
		
		// Determine rounding padding
		if(game.getRound() < 10)
			// Prepend 4 zeros
			roundString = "0000" + roundString;
		else if(game.getRound() < 100)
			// Prepend 3 zeros
			roundString = "000" + roundString;
		else if(game.getRound() < 1000)
			// Prepend 2 zeros
			roundString = "00" + roundString;
		
		// Score in Treasure Chase is rounds
		
		System.out.println();
		System.out.println("      ------------------- --------------- ---------------");
		System.out.println("     |    SPARE  TILE    |     ROUND     |     SCORE     |");
		System.out.println("     |===================|===============|===============|");
		System.out.println("     |      " + tileRows[0] + "      |               |               |");
		System.out.println("     |      " + tileRows[1] + "      |               |               |");
		System.out.println("     |      " + tileRows[2] + "      |     " + roundString + "     |     " + roundString + "     |");
		System.out.println("     |      " + tileRows[3] + "      |               |               |");
		System.out.println("     |      " + tileRows[4] + "      |               |               |");
		System.out.println("      ------------------- --------------- ---------------");
		System.out.println("     |    LAST COMPUTER MOVE                             |");
		System.out.println("     |===================================================|");
		System.out.println("       " + game.getComputerPlayer().getLastMove());
		System.out.println("     |___________________________________________________|");
		System.out.println();
		
		
		System.out.println("DEBUG INFO:");
		System.out.println("\tToken Position: (" + game.getBoard().getTokenPos()[0] + "," + game.getBoard().getTokenPos()[1] + ")");
	}
	
	/**
	 * Called in the event the player has won (i.e. completed the game mode objective(s)).
	 */
	public void onTreasureChaseWin() {
		System.out.println("__   __             __                      _   _   _            _                                              \n" +
                           "\\ \\ / /            / _|                    | | | | | |          | |                                           \n" +
                           " \\ V /___  _   _  | |_ ___  _   _ _ __   __| | | |_| |__   ___  | |_ _ __ ___  __ _ ___ _   _ _ __ ___         \n" +
                           "  \\ // _ \\| | | | |  _/ _ \\| | | | '_ \\ / _` | | __| '_ \\ / _ \\ | __| '__/ _ \\/ _` / __| | | | '__/ _ \\ \n" +
                           "  | | (_) | |_| | | || (_) | |_| | | | | (_| | | |_| | | |  __/ | |_| | |  __/ (_| \\__ \\ |_| | | |  __/_ _ _  \n" +
                           "  \\_/\\___/ \\__,_| |_| \\___/ \\__,_|_| |_|\\__,_|  \\__|_| |_|\\___|  \\__|_|  \\___|\\__,_|___/\\__,_|_|  \\___(_|_|_) \n");
                                                                                                             
                                                                                                             
		
		// Wait 2 seconds before final congratulation message
		try { Thread.sleep(2000); } catch(InterruptedException e) {}
		
		System.out.println("     _____ _____ _   _ _____ ______  ___ _____ _   _ _       ___ _____ _____ _____ _   _  _____ _ _           \n" +
                           "    /  __ \\  _  | \\ | |  __ \\| ___ \\/ _ \\_   _| | | | |     / _ \\_   _|_   _|  _  | \\ | |/  ___| | |   \n" +
                           "    | /  \\/ | | |  \\| | |  \\/| |_/ / /_\\ \\| | | | | | |    / /_\\ \\| |   | | | | | |  \\| |\\ `--.| | | \n" +
                           "    | |   | | | | . ` | | __ |    /|  _  || | | | | | |    |  _  || |   | | | | | | . ` | `--. \\ | |         \n" +
                           "    | \\__/\\ \\_/ / |\\  | |_\\ \\| |\\ \\| | | || | | |_| | |____| | | || |  _| |_\\ \\_/ / |\\  |/\\__/ /_|_| \n" +
                           "     \\____/\\___/\\_| \\_/\\____/\\_| \\_\\_| |_/\\_/  \\___/\\_____/\\_| |_/\\_/  \\___/ \\___/\\_| \\_/\\____/(_|_)  \n");
                                                                                                
                                                                                                
		
		//
		// TODO: Display leaderboard and do high score checking...
		//
	}

}
