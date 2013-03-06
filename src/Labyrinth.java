import java.io.IOException;
import java.util.Scanner;

/**
 * The main entry point to the Labyrinth game
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.06032013
 *
 */
public class Labyrinth {
	
	public static void main(String[] args) {
		TreasureChase tc = new TreasureChase(new Player());
		
		System.out.println("Welcome to the Labyrinth game!\n");
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			 
			tc.update();
			 
			System.out.println("\nPlease enter your spare tile at coordinates <x> <y>:");
			 
			String input = sc.nextLine();
			String[] coordinates = input.split(" ");
			
			int x, y;
			
			try {
				x = Integer.parseInt(coordinates[0]);
				y = Integer.parseInt(coordinates[1]);
			}
			catch(NumberFormatException e) {
				System.out.println("Invalid coordinates entered\n");
				continue;
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("Not enough arguments\n");
				continue;
			}
			 
			tc.replaceTile(x, y);
		}
	}

}
