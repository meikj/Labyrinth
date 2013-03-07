import java.io.IOException;
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
	
	public Labyrinth() {
		tc = new TreasureChase(new Player());
		input = new Scanner(System.in);
		execute = true;
	}
	
	public void run() {
		while(execute) {
			tc.update();
			 
			System.out.print("\n> ");
			parseInput(promptUser());
		}
	}
	
	/**
	 * Parse user input and perform appropriate operation(s)
	 * 
	 * @param inputArgs The list of arguments the user passed
	 */
	public void parseInput(String[] inputArgs) throws NumberFormatException, IndexOutOfBoundsException {
		if(inputArgs[0].toLowerCase().equals("insert")) {
			if(inputArgs.length < 3) {
				System.out.println("Usage: insert <x> <y>");
				return;
			}
		
			// Insert spare tile command
			int x, y;
			
			try {
				x = Integer.parseInt(inputArgs[1]);
				y = Integer.parseInt(inputArgs[2]);
			}
			catch(NumberFormatException e) {
				throw new NumberFormatException("Invalid coordinates entered\n");
			}
			catch(IndexOutOfBoundsException e) {
				throw new IndexOutOfBoundsException("Not enough arguments\n");
			}
			
			tc.replaceTile(x, y);
		}
		else if(inputArgs[0].toLowerCase().equals("help")) {
			System.out.println("insert \t Insert spare tile to specified location");
			System.out.println("help \t Display available game options");
		}
		else {
			System.out.println("Please enter 'help' for more information");
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
		
		System.out.println("Welcome to the Labyrinth game!\n");
		game.run();
	}

}
