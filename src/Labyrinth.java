/**
 * The main entry point to the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.11032013
 *
 */
public class Labyrinth {
	
	public static void main(String[] args) {
		TreasureChase game = new TreasureChase(new Player());
		UserInterface ui = new UserInterface(game);
		
		System.out.println("       _           _                _       _   _           \n" +
                           "      | |         | |              (_)     | | | |          \n" +
                           "      | |     __ _| |__  _   _ _ __ _ _ __ | |_| |__        \n" + 
                           "      | |    / _` | '_ \\| | | | '__| | '_ \\| __| '_ \\    \n" +
                           "      | |___| (_| | |_) | |_| | |  | | | | | |_| | | |      \n" +
                           "      \\_____/\\__,_|_.__/ \\__, |_|  |_|_| |_|\\__|_| |_|  \n" +
                           "                          __/ |                             \n" +
                           "                         |___/                              \n");
		ui.run();
	}

}
