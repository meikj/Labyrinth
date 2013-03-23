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
				// Check if win condition has been satisfied before continuing
				onTreasureChaseWin();
				setRunning(false);
			} else {
				// Process player tile move
				while(true) {
					try {
						promptTileMove();
						break;
					} catch(IllegalArgumentException e ) {
						System.out.println(e.getMessage());
						enterPrompt();
						continue;
					}
				}
				
				// Update interface to reflect move
				update();
				
				// Process player token move
				while(true) {
					try {
						promptTokenMove();
						break;
					} catch(IllegalArgumentException e) {
						// Invalid command passed, reset loop
						System.out.println(e.getMessage());
						enterPrompt();
						continue;
					}
				}
				
				// Advance to next round by checking win, processing computer move, etc.
				game.nextRound();
			}
		}
		
		// Game loop closed, call clean up code
		input.close();
	}
	
	/**
	 * Prompt the user for their tile move input.
	 * 
	 * @return The users input in a tokenized form.
	 * @throws IllegalArgumentException Thrown when an invalid command is passed as a value.
	 */
	public void promptTileMove() throws IllegalArgumentException {
		System.out.print("\nTile Move > ");
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		// A tile move is either rotate or insert
		if(tokens[0].equals("rotate")) {
			// When the player does a rotation, it does not count as a move, so ask for another tile move
			parse(tokens);
			update();
			promptTileMove();
		} else if(tokens[0].equals("insert")) {
			parse(tokens);
		} else {
			throw new IllegalArgumentException("Invalid tile move command: only rotate and insert allowed");
		}
	}
	
	/**
	 * Prompt the user for their token move input.
	 * 
	 * @return The users input in a tokenized form.
	 * @throws IllegalArgumentException Thrown when an invalid command is passed as a value.
	 */
	public void promptTokenMove() throws IllegalArgumentException {
		System.out.print("\nToken Move > ");
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		// A token move is move
		if(tokens[0].equals("move")) {
			parse(tokens);
		} else {
			throw new IllegalArgumentException("Invalid token move command: only move allowed");
		}
	}
	
	/**
	 * Parse user input and perform appropriate operation(s)
	 * 
	 * @param inputArgs The list of arguments the user passed
	 * @throw IllegalArgumentException Thrown when an invalid argument is passed with a command.
	 */
	public void parse(String[] inputArgs) throws IllegalArgumentException {
		if(inputArgs[0].toLowerCase().equals("insert")) {
			// Insertion command called
			if(inputArgs.length < 4) {
				throw new IllegalArgumentException("Usage:\tinsert column <top/bottom> <no>\n\tinsert row <left/right> <no>");
			}
			
			// Check if insertion into row or column
			if(inputArgs[1].equals("column")) {
				int column_no = 0;
				
				try {
					column_no = Integer.parseInt(inputArgs[3]);
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("Invalid column number entered");
				}
				
				// Check whether to push in from top or bottom
				try {
					if(inputArgs[2].equals("top")) {
						game.insertColumn(column_no, Direction.TOP);
					} else if(inputArgs[2].equals("bottom")) {
						game.insertColumn(column_no, Direction.BOTTOM);
					}
				} catch(IllegalMoveException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			} else if(inputArgs[1].equals("row")) {
				int row_no = 0;
				
				try {
					row_no = Integer.parseInt(inputArgs[3]);
				} catch(NumberFormatException e) {
					throw new IllegalArgumentException("Invalid row number entered");
				}
				
				// Check whether to push in from left or right
				try {
					if(inputArgs[2].equals("left")) {
						game.insertRow(row_no, Direction.LEFT);
					} else if(inputArgs[2].equals("right")) {
						game.insertRow(row_no, Direction.RIGHT);
					}
				} catch(IllegalMoveException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			} else {
				// Invalid argument passed
				throw new IllegalArgumentException("Usage:\tinsert column <top/bottom> <no>\n\tinsert row <left/right> <no>");
			}
		} else if(inputArgs[0].toLowerCase().equals("rotate")) {
			// Rotation command called
			if(inputArgs.length == 2) {
				// Rotate spare tile
				try {
					game.rotateTile(Integer.parseInt(inputArgs[1]));
				} catch(NumberFormatException e) {
					System.out.println(e.getMessage());
					enterPrompt();
				}
			} else {
				throw new IllegalArgumentException("Usage: rotate <degrees>, where degrees is 90, 180 or 270");
			}
		} else if(inputArgs[0].toLowerCase().equals("move")) {
			// Move command called
			if(inputArgs.length < 2) {
				System.out.println("Usage: move <up/down/left/right>");
				enterPrompt();
			} else {
				try {
					if(inputArgs[1].equals("up")) {
						// Move up
						game.moveTokenUp();
					} else if(inputArgs[1].equals("down")) {
						// Move down
						game.moveTokenDown();
					} else if(inputArgs[1].equals("left")) {
						// Move left
						game.moveTokenLeft();
					} else if(inputArgs[1].equals("right")) {
						// Move token right
						game.moveTokenRight();
					} else {
						throw new IllegalArgumentException("Usage: move <up/down/left/right>");
					}
				} catch(IllegalMoveException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			}
		} else if(inputArgs[0].toLowerCase().equals("exit")) {
			// Exit command called
			setRunning(false);
		} else {
			throw new IllegalArgumentException("Invalid command passed: " + inputArgs[0]);
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
	 * Draw the game board.
	 */
	public void drawGameBoard() {
		// Get board details from game
		int width = game.getBoard().getWidth();
		int height = game.getBoard().getHeight();
		
		// Get the tiles from the board
		Tile[][] tiles = game.getBoard().getTiles();
		
		// Form horizontal border
		String numberTop = "         ";
		String border = "      =======";
		
		for(int i = 0; i < width; i++)
			if((i + 1) < 10)
				numberTop += Integer.toString(i + 1) + "      ";
			else
				numberTop += Integer.toString(i + 1) + "     ";
		
		for(int i = 1; i < width; i++)
			border += "=======";
		
		// Display top border
		System.out.println(border);
		
		// Display each tile
		for(int i = 0; i < width; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 7x5, so therefore consists of 5 row sections
			
			String number = "";
			
			if((width - i) < 10) {
				number = Integer.toString(width - i) + " ";
			}
			else {
				number = Integer.toString(width - i);
			}
			
			String rowTop    = "   || ";
			String rowTM     = "   || ";
			String rowMiddle = " " + number + "|| ";
			String rowMB     = "   || ";
			String rowBottom = "   || ";
			
			for(int j = 0; j < height; j++) {
				// Split the tile up into its relevant sections
				// tile[0] = top, tile[1] = top-middle
				// tile[2] = middle, tile[3] = middle-bottom
				// tile[4] = bottom
				
				String[] tile = tiles[i][j].getTileString();
				
				// Check which tile it is for correct border placement
				if(j == (height - 1)) {
					// Last tile on row requires a border
					rowTop    += tile[0] + " ||";
					rowTM     += tile[1] + " ||";
					rowMiddle += tile[2] + " ||";
					rowMB     += tile[3] + " ||";
					rowBottom += tile[4] + " ||";
				}
				else {
					// A tile will be placed to the right of this tile, so skip border
					rowTop    += tile[0];
					rowTM     += tile[1];
					rowMiddle += tile[2];
					rowMB     += tile[3];
					rowBottom += tile[4];
				}
			}
			
			// Output full row now
			System.out.println(rowTop);
			System.out.println(rowTM);
			System.out.println(rowMiddle);
			System.out.println(rowMB);
			System.out.println(rowBottom);
		}
		
		// Display bottom border
		System.out.println(border);
		System.out.println(numberTop);
	}
	
	/**
	 * Update the interface.
	 */
	public void update() {
		// Draw the board
		drawGameBoard();
		
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
