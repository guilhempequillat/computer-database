import persistance.Save;
import ui.CommandLineInterface;

public class Main {

	public static void main(String[] args) {
		Save save = new Save();
		save.saveCompany();
		CommandLineInterface cli = new CommandLineInterface();
		cli.menuPresentation();
	}

}
