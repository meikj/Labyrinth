package com.labyrinth.ui;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.labyrinth.Direction;
import com.labyrinth.GameManager;
import com.labyrinth.GameMode;
import com.labyrinth.IllegalMoveException;
import com.labyrinth.Leaderboard;
import com.labyrinth.SettingsManager;
import com.labyrinth.Tile;

/**
 * Represents a text based user interface for use as a front-end for the Labyrinth game.
 * This user interface works with a valid conforming GameMode object (e.g. TreasureChase).
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.01042013
 *
 */
public class UserInterface {
	
	private GameMode game;
	private boolean running;
	private Scanner input;
	private GameManager manager;
	
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
		this.manager = new GameManager();
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
				return;
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
	 * @throws IllegalArgumentException Thrown when an invalid command is passed as a value.
	 */
	public void promptTileMove() throws IllegalArgumentException {
		System.out.println("Tile Move Commands:");
		System.out.println("\trotate <degrees>");
		System.out.println("\tinsert row <left/right> <no>");
		System.out.println("\tinsert column <top/bottom> <no>");
		System.out.print("\nTile Move > ");
		
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		// A tile move is either rotate, insert, save or exit
		if(tokens[0].equals("rotate")) {
			// When the player does a rotation, it does not count as a move, so ask for another tile move
			parse(tokens);
			update();
			promptTileMove();
		} else if(tokens[0].equals("insert") || tokens[0].equals("save") || tokens[0].equals("exit")) {
			parse(tokens);
		} else {
			throw new IllegalArgumentException("Invalid tile move command: only rotate and insert allowed");
		}
	}
	
	/**
	 * Prompt the user for their token move input.
	 * 
	 * @throws IllegalArgumentException Thrown when an invalid command is passed as a value.
	 */
	public void promptTokenMove() throws IllegalArgumentException {
		System.out.println("Token Move Commands:");
		System.out.println("\tmove <up/down/left/right>");
		System.out.print("\nToken Move > ");
		
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		// A token move is move, save or exit
		if(tokens[0].equals("move") || tokens[0].equals("save") || tokens[0].equals("exit")) {
			parse(tokens);
		} else {
			throw new IllegalArgumentException("Invalid token move command: only move allowed");
		}
	}
	
	/**
	 * Parse user input and perform appropriate operation(s).
	 * 
	 * @param inputArgs The list of arguments the user passed.
	 * @throws IllegalArgumentException Thrown when an invalid argument is passed with a command.
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
						game.updatePlayer(game.insertColumn(column_no, Direction.TOP, game.getPlayer()));
					} else if(inputArgs[2].equals("bottom")) {
						game.updatePlayer(game.insertColumn(column_no, Direction.BOTTOM, game.getPlayer()));
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
						game.updatePlayer(game.insertRow(row_no, Direction.LEFT, game.getPlayer()));
					} else if(inputArgs[2].equals("right")) {
						game.updatePlayer(game.insertRow(row_no, Direction.RIGHT, game.getPlayer()));
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
		} else if(inputArgs[0].toLowerCase().equals("save")) {
			String gameName = inputArgs[1];
			
			try {
				manager.save(System.getProperty("user.dir") + "/saves/" + gameName + ".txt", game);
				manager.addGameEntry(gameName);
			} catch(IOException e) {
				System.out.println("Couldn't save file: " + e.getMessage());
				enterPrompt();
			}
		} else if(inputArgs[0].toLowerCase().equals("exit")) {
			// Exit command called
			setRunning(false);
			input.close();
			System.exit(0);
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
	 * Display the help file.
	 */
	public void displayHelp() {
		LinkedList<String> helpFile = processFile(System.getProperty("user.dir") + "/media/help.txt");
		
		for(String line : helpFile)
			System.out.println(line);
		
		enterPrompt();
	}
	
	/**
	 * Parse a menu option.
	 * 
	 * 1 - Play game
	 * 2 - Load game
	 * 3 - Options
	 * 4 - Help
	 * 5 - Exit
	 * 
	 * @param choice The menu option.
	 * @throws IllegalArgumentException When an invalid menu option is passed.
	 */
	public void parseMenu(int choice) throws IllegalArgumentException {
		switch(choice) {
		case 1:
			// Play game
			System.out.println("\nStarting new Treasure Chase game...\n");
			run();
			break;
		case 2:
			// Load game
			System.out.println();
			promptLoad();
			break;
		case 3:
			// Options
			// TODO
			break;
		case 4:
			// Help
			System.out.println();
			displayHelp();
			break;
		case 5:
			// Exit
			setRunning(false);
			System.exit(0);
		default:
			throw new IllegalArgumentException("Please enter an option between 1-5 (inclusive).");
		}
	}
	
	/**
	 * Display the load screen and prompt the user for their option.
	 */
	public void promptLoad() {
		String option;
		
		while(running) {
			displayLoad();
			System.out.print("\n    Load Game: ");
			
			try {
				option = input.nextLine();
			} catch(Exception e) {
				System.out.println("Please enter a valid game name.");
				enterPrompt();
				continue;
			}
		
			// Process option
			GameMode newGame = null;
			
			try {
				newGame = manager.load(System.getProperty("user.dir") + "/saves/" + option + ".txt");
			} catch(FileNotFoundException e) {
				System.out.println("Couldn't find: " + System.getProperty("user.dir") + "/saves/" + option + ".txt");
				enterPrompt();
				continue;
			} catch(IOException e) {
				System.out.println("Error parsing: " + System.getProperty("user.dir") + "/saves/" + option + ".txt");
				System.out.println(e.getMessage());
				e.printStackTrace();
				enterPrompt();
				continue;
			}
			
			// Assign the saved game to the active game
			if(newGame != null) {
				this.game = newGame;
				run();
			} else {
				System.out.println("Something went horribly wrong processing the save. Try again?");
				enterPrompt();
			}
		}
	}
	
	/**
	 * Display the load screen.
	 */
	public void displayLoad() {
		LinkedList<String> loadFile = processFile(System.getProperty("user.dir") + "/media/load.txt");
		LinkedList<String> gameList = processFile(System.getProperty("user.dir") + "/saves/list.txt");
		
		for(String line : loadFile) {
			// Check for %START% - this signifies where to begin outputting the available games
			if(line.contains("%")) {
				// Begin outputting games here
				String[] split = line.split("%");
				
				for(String game : gameList) {
					// Process each game
					if(game.isEmpty())
						continue;
					
					// Offset is number of spaces to skip to retain border placement
					int offset = game.length() - 1;
					game = split[0] + game + split[1].substring(offset, split[1].length());
					System.out.println(game);
				}
			} else {
				System.out.println(line);
			}
		}
	}
	
	/**
	 * Display the menu.
	 */
	public void displayMenu() {
		LinkedList<String> menuFile = processFile(System.getProperty("user.dir") + "/media/menu.txt");
		
		for(String line : menuFile)
			System.out.println(line);
	}
	
	/**
	 * Display and run the main menu.
	 */
	public void runMenu() {
		while(running) {
			displayMenu();
			
			System.out.print("\n    Option: ");
			int option = 0;
			
			try {
				option = Integer.parseInt(input.nextLine());
			} catch(NumberFormatException e) {
				System.out.println("Please enter a valid menu option.");
				continue;
			}
			
			try {
				parseMenu(option);
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
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
	public void displayGameBoard() {
		// Get board details from game
		int width = game.getBoard().getWidth();
		int height = game.getBoard().getHeight();
		
		// Get the tiles from the board
		Tile[][] tiles = game.getBoard().getTiles();
		
		// Form horizontal top border
		String borderTop = "   " + Character.toString(SettingsManager.charBorderCornerTopLeft) +
				new String(new char[9]).replace('\0', SettingsManager.charBorderHorizontal);
		
		for(int i = 1; i < width; i++)
			borderTop += new String(new char[7]).replace('\0', SettingsManager.charBorderHorizontal);
		
		borderTop += Character.toString(SettingsManager.charBorderCornerTopRight);
		
		// Display top border
		System.out.println(borderTop);
		
		// Display each tile
		for(int i = 0; i < height; i++) {
			// Form the row by concatenating each tile on the row together
			// A tile is 7x5, so therefore consists of 5 row sections
			
			String number = "";
			
			if((height - i) < 10) {
				number = Integer.toString(height - i) + " ";
			}
			else {
				number = Integer.toString(height - i);
			}
			
			String rowTop    = "   " + Character.toString(SettingsManager.charBorderVertical) + " ";
			String rowTM     = "   " + Character.toString(SettingsManager.charBorderVertical) + " ";
			String rowMiddle = " " + number + Character.toString(SettingsManager.charBorderVertical) + " ";
			String rowMB     = "   " + Character.toString(SettingsManager.charBorderVertical) + " ";
			String rowBottom = "   " + Character.toString(SettingsManager.charBorderVertical) + " ";
			
			for(int j = 0; j < width; j++) {
				// Split the tile up into its relevant sections
				// tile[0] = top, tile[1] = top-middle
				// tile[2] = middle, tile[3] = middle-bottom
				// tile[4] = bottom
				
				String[] tile = tiles[i][j].getTileString();
				
				// Check which tile it is for correct border placement
				if(j == (width - 1)) {
					// Last tile on row requires a border
					rowTop    += tile[0] + " " + Character.toString(SettingsManager.charBorderVertical);
					rowTM     += tile[1] + " " + Character.toString(SettingsManager.charBorderVertical);
					rowMiddle += tile[2] + " " + Character.toString(SettingsManager.charBorderVertical);
					rowMB     += tile[3] + " " + Character.toString(SettingsManager.charBorderVertical);
					rowBottom += tile[4] + " " + Character.toString(SettingsManager.charBorderVertical);
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
		String numberBottom = "        ";
		
		for(int i = 0; i < width; i++)
			if((i + 1) < 10)
				numberBottom += Integer.toString(i + 1) + "      ";
			else
				numberBottom += Integer.toString(i + 1) + "     ";
		
		String borderBottom = borderTop.replace(SettingsManager.charBorderCornerTopLeft, SettingsManager.charBorderCornerBottomLeft);
		borderBottom = borderBottom.replace(SettingsManager.charBorderCornerTopRight, SettingsManager.charBorderCornerBottomRight);
		
		System.out.println(borderBottom);
		System.out.println(numberBottom);
	}
	
	/**
	 * Display the game HUB.
	 */
	public void displayHUB() {
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
		System.out.println("    ------------------- --------------- ---------------");
		System.out.println("   |    SPARE  TILE    |     ROUND     |     SCORE     |");
		System.out.println("   |===================|===============|===============|");
		System.out.println("   |      " + tileRows[0] + "      |               |               |");
		System.out.println("   |      " + tileRows[1] + "      |               |               |");
		System.out.println("   |      " + tileRows[2] + "      |     " + roundString + "     |     " + roundString + "     |");
		System.out.println("   |      " + tileRows[3] + "      |               |               |");
		System.out.println("   |      " + tileRows[4] + "      |               |               |");
		System.out.println("    ------------------- --------------- ---------------");
		System.out.println("   |    LAST COMPUTER MOVE                             |");
		System.out.println("   |===================================================|");
		System.out.println("     " + game.getComputerPlayer().getLastMove());
		System.out.println("   |___________________________________________________|");
		System.out.println();
		
		
		System.out.println("DEBUG INFO:");
		System.out.println("\tToken Position: (" + game.getBoard().getTokenPos()[0] + "," + game.getBoard().getTokenPos()[1] + ")");
	}
	
	/**
	 * Update the interface.
	 */
	public void update() {
		// Display the game board
		displayGameBoard();
		
		// Display the HUB
		displayHUB();
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
                                                                                                
                                                                                                
		
		System.out.println();
		System.out.println("Your final score: " + game.getPlayer().getScore());
		System.out.println();
		
		// Leaderboard code
		promptLeaderboard();
		enterPrompt();
	}
	
	/**
	 * Prompt the user for leaderboard entry.
	 */
	public void promptLeaderboard() {
		String name;
		String choice;
		
		System.out.print("Do you want to submit score (Y/N)? ");
		choice = input.nextLine().toLowerCase();
		
		if(choice.equals("y")) {
			System.out.print("Enter name: ");
			name = input.nextLine();
			
			while(name.length() > 14) {
				System.out.println("Name must not exceed 14 characters!");
				System.out.print("Enter name: ");
				name = input.nextLine();
			}
			
			game.getLeaderboard().submit(name, game.getPlayer().getScore());
			System.out.println("Score submitted! Here is the current top 10 now: \n");
			
			displayLeaderboard(10);
			
			try {
				game.getLeaderboard().save();
			} catch(Exception e) {
				System.out.println("Couldn't save score to leaderboard file. Score will NOT be saved.");
			}
		}
	}
	
	/**
	 * Display the leaderboard.
	 */
	public void displayLeaderboard(int entries) {
		Leaderboard l = game.getLeaderboard();
		ArrayList<String> names = l.getNames();
		ArrayList<Integer> scores = l.getScores();
		
		System.out.println(" -------------------- ------------- ");
		System.out.println("|        NAME        |    SCORE    |");
		System.out.println("|====================|=============|");
		
		if(names.size() < entries)
			entries = names.size();
		
		for(int i = 0; i < entries; i++) {
			// Form name entry
			String entry = "| " + (i + 1) + ". " + names.get(i);
			
			while(entry.length() < 21) {
				// Pad the name out to 21 characters to fit in field
				entry += " ";
			}
			
			entry += "|";
			
			// Form score entry
			entry += " " + scores.get(i);
			
			while(entry.length() < (21 + 14)) {
				// Pad the score out to 21 further characters to fit in field
				entry += " ";
			}
			
			entry += "|";
			
			System.out.println(entry);
		}
		
		// Form bottom
		System.out.println(" -------------------- ------------- ");
	}
	
	/**
	 * Retrieve the contents of a file line by line.
	 * 
	 * @param path The path to the file.
	 * @returns The lines of the processed file.
	 */
	private LinkedList<String> processFile(String path) {
		LinkedList<String> lines = new LinkedList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Scanner scanner = null;
			
			String line = reader.readLine();
			
			while(line != null) {
				scanner = new Scanner(line);
				String l = "";
				
				try {
					l = scanner.nextLine();
				} catch(NoSuchElementException e) {
					// Ignore
				}
				
				// [W] = wall block
				l = l.replace("[W]", Character.toString(SettingsManager.charBlock));
				lines.add(l);
				
				// Proceed to next line
				line = reader.readLine();
			}
			
			scanner.close();
			reader.close();
		} catch(FileNotFoundException e) {
			System.out.println("File not found: " + path);
		} catch(Exception e) {
			System.out.println("Couldn't process file: " + path);
		}
		
		return lines;
	}

}