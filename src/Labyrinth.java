import java.util.Scanner;

/**
 * The main entry point to the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.07032013
 *
 */
public class Labyrinth {
	
	private TreasureChase tc;
	private Scanner input;
	private boolean execute;
	
	/**
	 * Construct a Labyrinth game interface
	 */
	public Labyrinth() {
		tc = new TreasureChase(new Player());
		input = new Scanner(System.in);
		execute = true;
	}
	
	public void run() {
		while(execute) {
			// Main game loop
			tc.update();
			 
			System.out.print("\n> ");
			parseInput(promptUser());
		}
		
		// Game loop closed, call clean up code
		input.close();
	}
	
	/**
	 * Parse user input and perform appropriate operation(s)
	 * 
	 * @param inputArgs The list of arguments the user passed
	 */
	public void parseInput(String[] inputArgs) throws NumberFormatException, IndexOutOfBoundsException {
		if(inputArgs[0].toLowerCase().equals("insert")) {
			if(inputArgs.length < 3) {
				System.out.println("Usage: insert <column> <row>");
				return;
			}
		
			// Insert spare tile command
			int column, row;
			
			try {
				column = Integer.parseInt(inputArgs[1]);
				row = Integer.parseInt(inputArgs[2]);
			}
			catch(NumberFormatException e) {
				throw new NumberFormatException("Invalid coordinates entered\n");
			}
			catch(IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("Not enough arguments\n");
			}
			
			tc.replaceTile(column, row);
		}
		else if(inputArgs[0].toLowerCase().equals("rotate")) {
			if(inputArgs.length == 2) {
				// Rotate spare tile
				try {
					tc.rotateTile(Integer.parseInt(inputArgs[1]));
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
					tc.rotateTile(column, row, Integer.parseInt(inputArgs[3]));
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid angle: must be 90, 180 or 270");
					return;
				}
			}
			else {
				System.out.println("Usage: rotate <degrees> (rotate spare tile) or rotate <column> <row> <degrees>");
			}
		}
		else if(inputArgs[0].toLowerCase().equals("exit")) {
			// Exit command called
			execute = false;
		}
		else if(inputArgs[0].toLowerCase().equals("help")) {
			System.out.println("insert \t Insert spare tile to specified location");
			System.out.println("rotate \t Rotate either spare tile or specified tile by a number of degrees");
			System.out.println("exit \t Exit the game");
			System.out.println("help \t Display available game options");
		}
		else {
			System.out.println("Invalid command: please enter 'help' for more information");
		}
	}
	
	/**
	 * Prompt user for input
	 * 
	 * @return The users input in a tokenized form
	 */
	public String[] promptUser() {
		String in = input.nextLine();
		String[] tokens = in.split(" ");
		
		return tokens;
	}
	
	public static void main(String[] args) {
		Labyrinth game = new Labyrinth();
		
		System.out.println("       _           _                _       _   _           \n" +
                           "      | |         | |              (_)     | | | |          \n" +
                           "      | |     __ _| |__  _   _ _ __ _ _ __ | |_| |__        \n" + 
                           "      | |    / _` | '_ \\| | | | '__| | '_ \\| __| '_ \\    \n" +
                           "      | |___| (_| | |_) | |_| | |  | | | | | |_| | | |      \n" +
                           "      \\_____/\\__,_|_.__/ \\__, |_|  |_|_| |_|\\__|_| |_|  \n" +
                           "                          __/ |                             \n" +
                           "                         |___/                              \n");
		game.run();
	}

}
