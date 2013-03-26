/**
 * The main entry point to the Labyrinth game.
 * 
 * @author Gareth Gill
 * @author John Meikle
 * @version 0.1.22032013
 *
 */
public class Labyrinth {
	
	public static final String SETTINGS_PATH = System.getProperty("user.dir") + "/settings/settings.txt";
	
	public static void main(String[] args) {
		SettingsManager settings = new SettingsManager(SETTINGS_PATH);
		
		// By default just start up a new Treasure Chase game
		GameMode game = new TreasureChase(settings);
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
